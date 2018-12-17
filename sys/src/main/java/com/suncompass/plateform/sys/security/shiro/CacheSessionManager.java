package com.suncompass.plateform.sys.security.shiro;

import com.google.common.collect.Sets;
import com.suncompass.plateform.kernel.util.DateUtils;
import com.suncompass.plateform.kernel.util.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * shiro session管理实现类
 * @author alex
 * @version 2017-11-29
 */
public class CacheSessionManager extends EnterpriseCacheSessionDAO implements SessionManager {
	private Logger logger = LoggerFactory.getLogger(getClass());

    public CacheSessionManager() {
        super();
    }

    @Override
    protected void doDelete(Session session) {
    	if (session == null || session.getId() == null) {  
            return;
        }
    	
    	super.doDelete(session);
    	logger.debug("delete {} ", session.getId());
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
		return super.doReadSession(sessionId);
    }
    /**
	 * 获取活动会话
	 * @param includeLeave 是否包括离线（最后访问时间大于3分钟为离线会话）
	 * @return
	 */
	@Override
	public Collection<Session> getActiveSessions(boolean includeLeave) {
		return getActiveSessions(includeLeave, null, null);
	}
    
    /**
	 * 获取活动会话
	 * @param includeLeave 是否包括离线（最后访问时间大于3分钟为离线会话）
	 * @param principal 根据登录者对象获取活动会话
	 * @param filterSession 不为空，则过滤掉（不包含）这个会话。
	 * @return
	 */
	@Override
	public Collection<Session> getActiveSessions(boolean includeLeave, Object principal, Session filterSession) {
		// 如果包括离线，并无登录者条件。
		if (includeLeave && principal == null){
			return getActiveSessions();
		}
		Set<Session> sessions = Sets.newHashSet();
		for (Session session : getActiveSessions()){
			boolean isActiveSession = false;
			// 不包括离线并符合最后访问时间小于等于3分钟条件。
			if (includeLeave || DateUtils.pastMinutes(session.getLastAccessTime()) <= 3){
				isActiveSession = true;
			}
			// 符合登陆者条件。
			if (principal != null){
				PrincipalCollection pc = (PrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
				if (principal.toString().equals(pc != null ? pc.getPrimaryPrincipal().toString() : StringUtils.EMPTY)){
					isActiveSession = true;
				}
			}
			// 过滤掉的SESSION
			if (filterSession != null && filterSession.getId().equals(session.getId())){
				isActiveSession = false;
			}
			if (isActiveSession){
				sessions.add(session);
			}
		}
		return sessions;
	}

	private HttpServletRequest getRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 判断sesion是否失效
	 * @param session
	 * @return
	 */
	private boolean isDeadedSession(Session session){
		return DateUtils.pastMinutes(session.getLastAccessTime()) <= 3;
	}
}
