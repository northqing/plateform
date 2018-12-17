package com.suncompass.plateform.sys.service.impl;

import com.suncompass.plateform.sys.security.Principal;
import com.suncompass.plateform.sys.service.SecurityService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;


/**
 * Created by Administrator on 2017/12/6.
 *
 * @author by Administrator.
 * @version 1.0.0.
 */
@Service
public class ShiroSecurityServiceImpl implements SecurityService {
    @Override
    public Principal getPrincipal() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal) subject.getPrincipal();
            return principal;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        SecurityManager securityManager = SecurityUtils.getSecurityManager();
        if (securityManager != null) {
            securityManager.logout(subject);
        }
    }
}
