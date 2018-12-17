package com.suncompass.plateform.sys.service.impl;

import com.suncompass.plateform.kernel.security.Digest;
import com.suncompass.plateform.kernel.util.Encodes;
import com.suncompass.plateform.kernel.util.StringUtils;
import com.suncompass.plateform.kernel.util.WebUtils;
import com.suncompass.plateform.sys.dao.UserRepository;
import com.suncompass.plateform.sys.entity.Permission;
import com.suncompass.plateform.sys.entity.Role;
import com.suncompass.plateform.sys.entity.User;
import com.suncompass.plateform.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author alex
 *         Created by alex on 2017/11/25.
 */
@Service
public class UserServiceImpl implements UserService {
    public static final int DEFAULT_SALT_SIZE = 8;

    @Autowired
    UserRepository userRepository;

    @Override
    @Cacheable(value = "user", key = "#username")
    public User findByUsername(String username) {
        Assert.notNull(username, "参数[username]不能为空");
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findByNameContains(String name) {
        return userRepository.findByNameContains(name);
    }

    @Override
    public List<User> findByCompanyId(Long id) {
        return userRepository.findByCompanyId(id);
    }

    @Override
    public int updateLoginInfo(User user) {
        Long id = user.getId();
        String ip = WebUtils.getRemoteAddr(WebUtils.getRequest());
        if (StringUtils.isEmpty(ip)) {
            ip = "未知";
        }

        return userRepository.updateLoginInfo(id, ip, new Date());
    }

    @Override
    public Page<User> findUserList(String name, int page, int size) {
        PageRequest request = new PageRequest(page, size);
        if (StringUtils.isEmpty(name)) {
            return ((PagingAndSortingRepository) userRepository).findAll(request);
        } else {
            return userRepository.findByNameContains(name, request);
        }
    }

    @Override
    public User save(User user) {
        preHandleSaveUser(user);
        return userRepository.save(user);
    }

    @Override
    public User get(long id) {
        return userRepository.getOne(id);
    }

    /**
     * 加密用户密码
     *
     * @param password
     * @return
     */
    private String EncryptPassword(String password, byte[] salt) {
        byte[] hashPassword = Digest.md5(password.getBytes(), salt);
        return Encodes.encodeHex(hashPassword);
    }

    @Override
    @Transactional
    public Permission getMenu(long userId, long sysId) {
        Set<Permission> permissionSet = new HashSet<>();

        User user = get(userId);
        for (Role role : user.getRoleList()) {
            List<Permission> rolePermissions = role.getPermissionList();
            if (rolePermissions != null && rolePermissions.size() > 0) {
                permissionSet.addAll(rolePermissions);
            }
        }

        Permission rootPermission = null;
        for (Permission p : permissionSet) {
            if (p.isMenu() && p.getId() == sysId) {
                rootPermission = p;
                break;
            }
        }

        return filterPermissionByRole(rootPermission, permissionSet);
    }

    /**
     * 筛选许可，只保留用户角色包含的许可
     *
     * @param permission
     * @param permissionSet
     * @return
     */
    private Permission filterPermissionByRole(Permission permission, final Set<Permission> permissionSet) {
        if (permission != null) {
            List<Permission> children = permissionSet.stream().filter(p -> p.isMenu() && permission.equals(p.getParent())).sorted(Comparator.comparing(Permission::getSort)).collect(Collectors.toList());
            permission.setChildren(children);

            for (Permission child : children) {
                filterPermissionByRole(child, permissionSet);
            }
        }
        return permission;
    }

    /**
     * 加密用户密码，并设置加密盐
     *
     * @param user
     */
    private void EncrptyPassword(User user) {
        Assert.hasLength(user.getPassword(), "用户密码不能为空");

        byte[] salt = Digest.generateSalt(DEFAULT_SALT_SIZE);
        user.setSalt(Encodes.encodeHex(salt));
        String newPassword = EncryptPassword(user.getPassword(), salt);
        user.setPassword(newPassword);
    }

    /**
     * 判断是否新增用户
     *
     * @param user
     * @return
     */
    private boolean isNewUser(User user) {
        Long id = user.getId();
        return id == null || id == 0;
    }

    /**
     * 预处理用户信息
     *
     * @param user
     * @return
     */
    private User preHandleSaveUser(User user) {
        if (isNewUser(user)) {
            EncrptyPassword(user);

            //设置修改时间
            user.setCreated(new Date());
            user.setModified(new Date());
        } else {
            User dbUser = userRepository.findOne(user.getId());
            //不能修改用户账号
            user.setUsername(dbUser.getUsername());

            //密码和密码盐只能是用户自己修改
            user.setPassword(dbUser.getPassword());
            user.setSalt(dbUser.getSalt());

            //设置修改时间
            user.setCreated(dbUser.getCreated());
            user.setModified(new Date());
        }

        return user;
    }

    @Override
    public void modifyPassword(Long id, String newPassword) {
        User user = userRepository.findOne(id);
        user.setPassword(newPassword);
        EncrptyPassword(user);
        save(user);
    }
}
