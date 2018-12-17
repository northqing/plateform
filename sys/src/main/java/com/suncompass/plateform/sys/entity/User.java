package com.suncompass.plateform.sys.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suncompass.plateform.kernel.entity.AbstractDataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 用户实体
 *
 * @author alex
 * @version v1.0
 *          Created by alex on 2017/11/23.
 */
@Entity
@Table(name = "sys_user")
public class User extends AbstractDataEntity<User> {
    private static final long serialVersionUID = 1L;
    /**
     * 账号
     */
    @Column(unique = true, nullable = false)
    @Length(min=6, max = 12, message = "账号必须是6到12为数字，字母或_")
    @Pattern(regexp = "^[a-zA-Z]/S$")
    private String username;
    /**
     * 密码
     */
    @JsonIgnore
    @Column(nullable = false)
    private String password;
    /**
     * 加密密码的盐
     */
    @JsonIgnore
    private String salt;
    /**
     * 姓名
     */
    private String name;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 手机
     */
    @Pattern(regexp = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", message = "手机号填写错误")
    private String mobile;
    /**
     * 用户类型
     */
    private String userType;
    /**
     * 所属公司
     */
    @ManyToOne
    @JoinColumn(name = "companyId")
    private Organization company;
    /**
     * 所属部门
     */
    @ManyToOne
    @JoinColumn(name = "departmentId")
    private Organization department;
    /**
     * 员工编号
     */
    private String employeeNumber;
    /**
     * 最后登陆IP
     */
    private String lastLoginIp;
    /**
     * 最后登陆日期
     */
    private Date lastLoginDate;
    /**
     * 用户状态0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.
     */
    private byte state = 0;
    /**
     * 头像
     */
    private String photo;

    /**
     * 用户角色列表
     */
    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(name = "SysUserRole", joinColumns = {@JoinColumn(name = "userId")}, inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<Role> roleList;

    public Organization getCompany() {
        return company;
    }

    public void setCompany(Organization company) {
        this.company = company;
    }

    public Organization getDepartment() {
        return department;
    }

    public void setDepartment(Organization department) {
        this.department = department;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    /**
     * 密码盐.
     *
     * @return
     */
    public String getCredentialsSalt() {
        return this.password + this.salt;
    }

    /**
     * 是否超级用户
     *
     * @param user
     * @return
     */
    public Boolean isAdministratorUser(User user) {
        return user == null ? false : user.id == 1;
    }
}
