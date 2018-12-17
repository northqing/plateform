package com.suncompass.plateform.sys.dao;

import com.suncompass.plateform.sys.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * 系统用户数据访问对象
 *
 * @author alex
 * @version v1.0.0
 *          Created by alex on 2017/11/25.
 */
public interface UserRepository extends BaseRepository<User> {
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
     * 按名称分页查询
     *
     * @param name
     * @param pageable
     * @return
     */
    Page<User> findByNameContains(String name, Pageable pageable);

    /**
     * 更新用户最近登陆信息
     *
     * @param id
     * @param ip
     * @param date
     * @return
     */
    @Transactional
    @Modifying
    @Query("update User u set u.lastLoginIp= :ip,u.lastLoginDate= :date where u.id= :id")
    int updateLoginInfo(@Param("id") Long id, @Param("ip") String ip, @Param("date") Date date);
}
