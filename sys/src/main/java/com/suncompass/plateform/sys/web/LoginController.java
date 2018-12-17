package com.suncompass.plateform.sys.web;

import com.suncompass.plateform.kernel.web.BaseController;
import com.suncompass.plateform.sys.security.Principal;
import com.suncompass.plateform.sys.security.shiro.FormAuthenticationFilter;
import com.suncompass.plateform.sys.service.SecurityService;
import com.suncompass.plateform.sys.service.UserService;
import org.pac4j.core.client.Client;
import org.pac4j.core.config.Config;
import org.pac4j.http.client.indirect.FormClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by alex on 2017/11/29.
 *
 * @author by alex.
 * @version 1.0.0.
 */
@Controller
public class LoginController extends BaseController {
    private String adminPath ="";

    @Autowired
    SecurityService securityService;

    @Autowired
    private Config config;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, Map<String, Object> map) throws Exception {
        Principal principal = securityService.getPrincipal();
        if (principal != null) {
            return "redirect:/" + adminPath;
        }
        FormClient client = (FormClient)config.getClients().findClient("form");
        map.put("callback", client.getCallbackUrl());
        // 此方法不处理登录成功,由shiro进行处理
        return adminPath +"/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String LoginFailure(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        String msg = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);

        map.put("msg", msg);
        return adminPath +"/login";
    }

    @RequestMapping("/logout")
    public String logout(){
        securityService.logout();

        return "redirect:/login";
    }

    @RequestMapping("/403")
    public String unauthorizedRole() {
        return "/403";
    }
}
