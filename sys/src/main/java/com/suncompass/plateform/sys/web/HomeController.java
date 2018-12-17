package com.suncompass.plateform.sys.web;

import com.suncompass.plateform.kernel.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by Administrator on 2017/12/6.
 *
 * @author by Administrator.
 * @version 1.0.0.
 */
@Controller
@RequestMapping("${app.adminPath}")
@RequiresAuthentication
public class HomeController extends BaseController {
    @Value("${app.adminPath}")
    private String adminPath;

    @RequestMapping("")
    @RequiresRoles("admin")
    public String index() {
        return adminPath + "/home/index";
    }
}
