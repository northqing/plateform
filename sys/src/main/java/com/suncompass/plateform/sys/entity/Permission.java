package com.suncompass.plateform.sys.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suncompass.plateform.kernel.entity.AbstractTreeDataEntity;

import java.util.List;
import javax.persistence.*;

/**
 * 资源许可实体
 *
 * @author alex
 * @version 1.0
 *          Created by alex on 2017/11/23.
 */
@Entity
@Table(name = "sys_permission")
public class Permission extends AbstractTreeDataEntity<Permission> {
    private static final long serialVersionUID = 1L;

    public final static String RESROUCE_TYEP_MENU = "menu";
    public final static String RESROUCE_TYEP_BUTTON = "button";

    /**
     * 名称
     */
    private String name;

    /**
     * 资源类型，[menu|button]
     */
    @Column(columnDefinition = "enum('menu','button')")
    private String resourceType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    @JsonBackReference
    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    /**
     * 资源路径.
     */

    private String url;
    /**
     * 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     */
    private String permission;

    /**
     * 父编号列表
     */
    private String parentIds;

    private Integer sort;

    private Boolean available = Boolean.FALSE;

    @ManyToMany
    @JoinTable(name = "SysRolePermission", joinColumns = {@JoinColumn(name = "permissionId")}, inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<Role> roleList;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private String icon;

    /**
     * 是否菜单资源
     * @return
     */
    public boolean isMenu(){
        return RESROUCE_TYEP_MENU.equals(resourceType);
    }
}
