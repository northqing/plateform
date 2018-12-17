package com.suncompass.plateform.sys.web;

import com.suncompass.plateform.sys.entity.Role;
import com.suncompass.plateform.sys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/2/1.
 *
 * @author by Administrator.
 * @version 1.0.0.
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequestMapping("index")
    public String index(){
        return "role/index";
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Page<Role> list(int size, int page, String name){
        return roleService.findRoleList(size, page, name);
    }
}
