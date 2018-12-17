package com.suncompass.plateform.sys.security.impl;

import com.suncompass.plateform.sys.entity.User;
import com.suncompass.plateform.sys.security.Principal;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/28.
 *
 * @author by Administrator.
 * @version 1.0.0.
 */
public class PrincipalImpl implements Principal, Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String name;
    private boolean mobileLogin;

    public PrincipalImpl(User user, boolean mobileLogin) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.mobileLogin = mobileLogin;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isMobileLogin() {
        return mobileLogin;
    }
}
