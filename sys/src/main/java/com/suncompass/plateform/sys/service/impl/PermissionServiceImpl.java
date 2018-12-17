package com.suncompass.plateform.sys.service.impl;

import com.suncompass.plateform.sys.dao.PermissionRepository;
import com.suncompass.plateform.sys.entity.Permission;
import com.suncompass.plateform.sys.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/1/24.
 *
 * @author by Administrator.
 * @version 1.0.0.
 */
@Service
public class PermissionServiceImpl implements PermissionService{
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Permission get(long id) {
        return permissionRepository.getOne(id);
    }
}
