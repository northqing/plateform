package com.suncompass.plateform.sys.dao;

import com.suncompass.plateform.sys.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Created by Administrator on 2018/2/1.
 *
 * @author by Administrator.
 * @version 1.0.0.
 */
public interface RoleRepository extends BaseRepository<Role> {
    /**
     * 分页查询包含{name}的所有角色
     * @param name 角色名称
     * @param pageable 分页
     * @return
     */
    Page<Role> findByNameContains(String name, Pageable pageable);
}
