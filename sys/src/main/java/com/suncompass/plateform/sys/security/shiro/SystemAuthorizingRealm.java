package com.suncompass.plateform.sys.security.shiro;

import com.suncompass.plateform.kernel.util.Encodes;
import com.suncompass.plateform.kernel.util.WebUtils;
import com.suncompass.plateform.sys.config.AppConfig;
import com.suncompass.plateform.sys.entity.Role;
import com.suncompass.plateform.sys.entity.User;
import com.suncompass.plateform.sys.entity.UserStateEnum;
import com.suncompass.plateform.sys.security.Principal;
import com.suncompass.plateform.sys.security.impl.PrincipalImpl;
import com.suncompass.plateform.sys.service.LogService;
import com.suncompass.plateform.sys.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

/**
 * Created by alex on 2017/11/27.
 *
 * @author by alex.
 * @version 1.0.0.
 */
public class SystemAuthorizingRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    private CacheSessionManager sessionDao = new CacheSessionManager();

    /**
     * 认证回调函数, 登录时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        // 校验用户名密码
        String username = userToken.getUsername();
        User user = userService.findByUsername(username);
        if (user != null) {
            if (UserStateEnum.LOCKED.ordinal()== user.getState()){
                throw new AuthenticationException("msg:该已帐号禁止登录.");
            }
            if (UserStateEnum.NOT_ACTIVE.ordinal()== user.getState()){
                throw new AuthenticationException("msg:该已帐号未激活.");
            }
            byte[] salt = Encodes.decodeHex(user.getSalt());
            return new SimpleAuthenticationInfo(new PrincipalImpl(user, userToken.isMobileLogin()),
                    user.getPassword(), ByteSource.Util.bytes(salt), getName());
        } else {
            return null;
        }
    }

    /**
     * 授权回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Principal principal = (Principal) getAvailablePrincipal(principals);
        // 获取当前已登录的用户
        if (!appConfig.getAllowMultiLogin()){
            Collection<Session> sessions = getSessionCache().getActiveSessions(true, principal, ShiroUtils.getSession());
            if (sessions.size() > 0){
                // 如果是登录进来的，则踢出已在线用户
                if (ShiroUtils.getSubject().isAuthenticated()){
                    for (Session session : sessions){
                        getSessionCache().delete(session);
                    }
                }
                // 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
                else{
                    ShiroUtils.getSubject().logout();
                    throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
                }
            }
        }
        User user = userService.findByUsername(principal.getUsername());
        if (user != null) {
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

            // 添加用户权限
            authorizationInfo.addStringPermission("user");
            // 添加用户角色信息
            for (Role role : user.getRoleList()){
                authorizationInfo.addRole(role.getName());

                for(com.suncompass.plateform.sys.entity.Permission permission : role.getPermissionList()){
                    authorizationInfo.addStringPermission(permission.getUrl());
                }
            }
            // 更新登录IP和时间
            userService.updateLoginInfo(user);
            // 记录登录日志
            logService.logUserLoginedInfo(WebUtils.getRequest(), user);
            return authorizationInfo;
        } else {
            return null;
        }
    }

    @Override
    protected void checkPermission(Permission permission, AuthorizationInfo info) {
        authorizationValidate(permission);
        super.checkPermission(permission, info);
    }

    @Override
    protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                authorizationValidate(permission);
            }
        }
        return super.isPermitted(permissions, info);
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        authorizationValidate(permission);
        return super.isPermitted(principals, permission);
    }

    @Override
    protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                authorizationValidate(permission);
            }
        }
        return super.isPermittedAll(permissions, info);
    }

    /**
     * 授权验证方法
     * @param permission
     */
    private void authorizationValidate(Permission permission){
        // 模块授权预留接口
    }

    /**
     * 获取sessionDao
     */
    public CacheSessionManager getSessionCache(){
        return  sessionDao;
    }
}
