package com.suncompass.plateform.sys.service;

import com.suncompass.plateform.sys.entity.Permission;
import com.suncompass.plateform.sys.entity.User;
import org.springframework.data.domain.Page;
import java.util.List;

/**
 * Created by Administrator on 2017/11/25.
 */
public interface UserService {
    /**
     * 通过username查找用户
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 查找姓名包含name的用户
     *
     * @param name
     * @return
     */
    List<User> findByNameContains(String name);

    /**
     * 查找公司用户
     *
     * @param id
     * @return
     */
    List<User> findByCompanyId(Long id);

    /**
     * 更新用户登录信息
     *
     * @param user
     * @return
     */
    int updateLoginInfo(User user);

    /**
     * 按名称查找用户，实现分页返回结果
     *
     * @param name 名称，name为空查询所有
     * @param page 页码
     * @param size 大小
     * @return
     */
    Page<User> findUserList(String name, int page, int size);

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    User save(User user);

    /**
     * 根据id获取用户
     *
     * @param id
     * @return
     */
    User get(long id);

    /**
     * 获取用户系统菜单资源，必须指定用户id和sysId
     *
     * @param userId， 用户id
     * @param sysId   系统资源许可Id，返回包含该id及后代资源许可
     * @return
     */
    Permission getMenu(long userId, long sysId);

    /**
     * 修改密码
     * @param id
     * @param newPassword
     */
    void modifyPassword(Long id, String newPassword);
}