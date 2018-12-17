package com.suncompass.plateform.sys.service.impl;

import com.suncompass.plateform.sys.entity.Permission;
import com.suncompass.plateform.sys.entity.Role;
import com.suncompass.plateform.sys.entity.User;
import com.suncompass.plateform.sys.service.LogService;
import com.suncompass.plateform.sys.service.PermissionService;
import com.suncompass.plateform.sys.service.SystemService;
import com.suncompass.plateform.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2018/1/24.
 *
 * @author by Administrator.
 * @version 1.0.0.
 */
@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public UserService getUserService() {
        return userService;
    }

    @Override
    public LogService getLogService() {
        return logService;
    }
}