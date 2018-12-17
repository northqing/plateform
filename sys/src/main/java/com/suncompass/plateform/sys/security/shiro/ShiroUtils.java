package com.suncompass.plateform.sys.security.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Created by Administrator on 2017/11/29.
 *
 * @author by Administrator.
 * @version 1.0.0.
 */
public class ShiroUtils {
    public static Session getSession(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null){
                session = subject.getSession();
            }
            if (session != null){
                return session;
            }
            subject.logout();
        }catch (InvalidSessionException e){

        }
        return null;
    }

    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }
}
