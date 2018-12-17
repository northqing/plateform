package com.suncompass.plateform.sys.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 2017/11/29.
 *
 * @author by alex.
 * @version 1.0.0.
 */
@ConfigurationProperties(prefix = "app")
@Component
public class AppConfig {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getAllowMultiLogin() {
        return allowMultiLogin;
    }

    public void setAllowMultiLogin(boolean allowMultiLogin) {
        this.allowMultiLogin = allowMultiLogin;
    }

    public String getAdminPath() {
        return adminPath;
    }

    public void setAdminPath(String adminPath) {
        this.adminPath = adminPath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String name = "基础框架平台";
    private boolean allowMultiLogin = false;
    private String adminPath = "";
    private long id;
}
