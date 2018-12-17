package com.suncompass.plateform.sys.service;

import com.suncompass.plateform.sys.entity.Role;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2018/2/1.
 *
 * @author by Administrator.
 * @version 1.0.0.
 */
public interface RoleService {
    /**
     * 按名称查找角色，实现分页返回结果
     * @param size 每页大小
     * @param page 当前页码
     * @param name 角色名称
     * @return
     */
    Page<Role> findRoleList(int size, int page, String name);

    List<Role> findAll();
}
