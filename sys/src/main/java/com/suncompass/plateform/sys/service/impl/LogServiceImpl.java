package com.suncompass.plateform.sys.service.impl;

import com.suncompass.plateform.kernel.util.StringUtils;
import com.suncompass.plateform.sys.dao.LogRepository;
import com.suncompass.plateform.sys.entity.Log;
import com.suncompass.plateform.sys.entity.User;
import com.suncompass.plateform.sys.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by alex on 2017/11/29.
 *
 * @author by alex.
 * @version 1.0.0.
 */
@Service
public class LogServiceImpl implements LogService{
    @Autowired
    private LogRepository logRepository;

    @Override
    public void logUserLoginedInfo(HttpServletRequest request, User user) {
        Log log = new Log();

        log.setUserId(user.getId());
        log.setTitle("系统登录");
        log.setType(Log.TYPE_ACCESS);
        log.setRemoteAddr(StringUtils.getRemoteAddr(request));
        log.setUserAgent(request.getHeader("user-agent"));
        log.setRequestUri(request.getRequestURI());
        log.setMethod(request.getMethod());

        logRepository.save(log);
    }
}
