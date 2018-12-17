package com.suncompass.plateform.sys.service;

import com.suncompass.plateform.sys.entity.Permission;

import java.util.List;

/**
 * Created by alex on 2017/11/28.
 *
 * @author by alex.
 * @version 1.0.0.
 */
public interface SystemService {
    /**
     * 获取用户服务
     * @return
     */
    UserService getUserService();

    /**
     * 获取日志服务
     * @return
     */
    LogService getLogService();
}
