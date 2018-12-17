package com.suncompass.plateform.sys.service;

import com.suncompass.plateform.sys.security.Principal;

/**
 * Created by Administrator on 2017/12/6.
 *
 * @author by Administrator.
 * @version 1.0.0.
 */

/**
 * 权限服务
 */
public interface SecurityService {
    /**
     * 获取登陆用户标识
     * @return
     */
    Principal getPrincipal();

    /**
     * 注销退出
     */
    void logout();

}
