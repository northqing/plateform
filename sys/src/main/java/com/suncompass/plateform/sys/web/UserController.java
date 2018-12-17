package com.suncompass.plateform.sys.web;

import com.suncompass.plateform.kernel.web.BaseController;
import com.suncompass.plateform.sys.entity.Permission;
import com.suncompass.plateform.sys.entity.Role;
import com.suncompass.plateform.sys.entity.User;
import com.suncompass.plateform.sys.security.Principal;
import com.suncompass.plateform.sys.service.RoleService;
import com.suncompass.plateform.sys.service.SecurityService;
import com.suncompass.plateform.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by alex on 2017/11/27.
 *
 * @author by alex.
 * @version 1.0.0.
 */
@Controller
@RequestMapping("${app.adminPath}/user")
public class UserController extends BaseController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    SecurityService securityService;

    @RequestMapping("index")
    public String index() {
        return "/user/index";
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Page<User> list(int page, int size, String name) {

        Page<User> userPage = userService.findUserList(name, page, size);

        return userPage;
    }

    @RequestMapping(value = {"add"}, method = RequestMethod.GET)
    public String add(Model model) {
        User user = new User();
        List<Role> allRoles = roleService.findAll();

        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles);
        return "user/add";
    }

    @RequestMapping(value = {"detail", "edit"}, method = RequestMethod.GET)
    public String edit(Long id, Model model) {
        User user = userService.get(id);
        List<Role> allRoles = roleService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles);
        return "user/edit";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public RequestResult save(@Validated User user, BindingResult bindingResult) {
        RequestResult result = new RequestResult();

        try {
            userService.save(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @RequestMapping("getMenu")
    @ResponseBody
    public Permission getMenu() {
        Principal principal = securityService.getPrincipal();

        return userService.getMenu(principal.getId(), 1L);
    }
}
