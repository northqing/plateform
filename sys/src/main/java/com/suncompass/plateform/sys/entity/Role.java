package com.suncompass.plateform.sys.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.suncompass.plateform.kernel.entity.AbstractDataEntity;

import javax.persistence.*;
import java.util.List;

/**
 * 角色实体
 *
 * @author alex
 * @version 1.0
 * Created by alex on 2017/11/23.
 */
@Entity
@Table(name = "sys_role")
public class Role extends AbstractDataEntity<Role> {
    private static final long serialVersionUID = 1L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    public List<User> getUserList() {
        return userList;
    }

    @JsonBackReference
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    /**
     * 角色
     */
    private String role;

    /**
     * 名称
     */
    @Column(unique = true)
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 是否可用,如果不可用将不会添加给用户
     */
    private Boolean available = Boolean.FALSE;

    //角色 -- 权限关系：多对多关系;
    @ManyToMany
    @JoinTable(name = "SysRolePermission", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "permissionId")})
    private List<Permission> permissionList;

    // 用户 - 角色关系定义;
    @ManyToMany
    @JoinTable(name = "SysUserRole", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "userId")})
    private List<User> userList;
}
