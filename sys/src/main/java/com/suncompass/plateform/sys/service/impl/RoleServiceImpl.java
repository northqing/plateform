package com.suncompass.plateform.sys.service.impl;

import com.suncompass.plateform.sys.dao.RoleRepository;
import com.suncompass.plateform.sys.entity.Role;
import com.suncompass.plateform.sys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/2/1.
 *
 * @author by Administrator.
 * @version 1.0.0.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<Role> findRoleList(int size, int page, String name) {
        return roleRepository.findByNameContains(name, new PageRequest(page, size));
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
