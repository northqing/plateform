package com.suncompass.plateform.sys.service;

import com.suncompass.plateform.sys.entity.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by alex on 2017/11/29.
 *
 * @author by alex.
 * @version 1.0.0.
 */
public interface LogService {
    /**
     * 记录用户登陆信息
     * @param request
     * @param user
     */
    void logUserLoginedInfo(HttpServletRequest request, User user);
}
