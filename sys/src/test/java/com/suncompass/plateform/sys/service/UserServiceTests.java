package com.suncompass.plateform.sys.service;

import com.suncompass.plateform.sys.entity.Organization;
import com.suncompass.plateform.sys.entity.Permission;
import com.suncompass.plateform.sys.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;

/**
 * Created by Administrator on 2017/11/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @Test
    public void findByUsernameReturnNullWhenParameterIsNull() {
        //缓存key不能为空
        //Assert.assertNull(userService.findByUsername(null));
    }

    @Test
    public void findByUsernameReturnNullWhenNotExist() {
        Assert.assertNull(userService.findByUsername("abcXCV"));
    }

    @Test
    public void findByUsername() {
        User user = userService.findByUsername("admin");
        Assert.assertNotNull(user);
        Assert.assertEquals("username = admin", "admin", user.getUsername());
    }

    @Test
    public void findByNameContains() {
        List<User> userList = userService.findByNameContains("adminX000");
        Assert.assertEquals(0, userList.size());

        userList = userService.findByNameContains("系统管理");
        Assert.assertEquals(1, userList.size());
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void findByNameContainsThrowExceptionWhenParameterIsNull(){
        userService.findByNameContains(null);
    }

    @Test
    public void findByCompanyId() {
        List<User> userList = userService.findByCompanyId(1L);
    }

    @Test
    public void findUserPage() {
        Page<User> userPage = userService.findUserList(null, 0, 10);
        Assert.assertNotNull(userPage);
        Assert.assertThat(userPage.getContent().size(),greaterThan(0) );
    }

    @Test
    public  void updateLoginInfo(){
        User user = userService.findByUsername("admin");
        Assert.assertNotNull(user);
        userService.updateLoginInfo(user);
    }

    @Test
    public void getUserCompany(){
        User user = userService.findByUsername("admin");
        Assert.assertNotNull(user);
        Organization org = user.getCompany();
    }
}
