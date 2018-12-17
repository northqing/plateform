package com.suncompass.plateform.sys.config;

import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.LogoutFilter;
import io.buji.pac4j.filter.SecurityFilter;
import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jSubjectFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.AbstractShiroWebFilterConfiguration;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.pac4j.core.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/31.
 *
 * @author by Administrator.
 * @version 1.0.0.
 */
@Configuration
public class ShiroConfig extends AbstractShiroWebFilterConfiguration {
    @Autowired
    private Config config;

    public Realm realm() {
        Pac4jRealm realm = new Pac4jRealm();
        return realm;
    }

    protected SubjectFactory subjectFactory() {
        return new Pac4jSubjectFactory();
    }

    public Filter securityFilter(){
        SecurityFilter filter = new SecurityFilter();
        filter.setClients("form, rest,jwt");
        filter.setConfig(config);
        filter.setMatchers("matcher");
        return filter;
    }

    @Bean
    protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition filterChainDefinition = new DefaultShiroFilterChainDefinition();
        filterChainDefinition.addPathDefinition("/lib/**", "anon");
        filterChainDefinition.addPathDefinition("/bower_components/**", "anon");
        filterChainDefinition.addPathDefinition("/plugins/**", "anon");
        filterChainDefinition.addPathDefinition("/dist/**", "anon");
        filterChainDefinition.addPathDefinition("/favicon*", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinition.addPathDefinition("/logout", "logout");
        filterChainDefinition.addPathDefinition("/callback", "callbackFilter");
        filterChainDefinition.addPathDefinition("/**", "securityFilter");
        return filterChainDefinition;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        securityManager.setSubjectFactory(subjectFactory());
        return securityManager;
    }

    @Bean
    protected ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = super.shiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        filterFactoryBean.setLoginUrl("/login");
        filterFactoryBean.setSuccessUrl("/a/");
        filterFactoryBean.setFilters(filters());

        return filterFactoryBean;
    }

    protected Map<String, Filter> filters() {
        Map<String, Filter> filterMap = new HashMap<>(3);
        filterMap.put("securityFilter", securityFilter());
        CallbackFilter callbackFilter = new CallbackFilter();
        callbackFilter.setConfig(config);
        filterMap.put("callbackFilter", callbackFilter);
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setConfig(config);
        filterMap.put("logoutFilter", logoutFilter);
        return filterMap;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
