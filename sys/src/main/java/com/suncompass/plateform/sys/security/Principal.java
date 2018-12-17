package com.suncompass.plateform.sys.security;

/**
 * Created by Administrator on 2017/12/5.
 *
 * @author by Administrator.
 * @version 1.0.0.
 */
public interface Principal {
    /**
     * 获取用户Id
     * @return
     */
    Long getId();

    /**
     * 获取用户账号
     * @return
     */
    String getUsername();

    /**
     * 获取用户名称
     * @return
     */
    String getName();

    /**
     * 获取是否移动客户端登陆
     * @return
     */
    boolean isMobileLogin();
}
