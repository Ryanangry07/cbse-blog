package com.loloao.config;

import java.util.LinkedHashMap;
import java.util.Map;

import com.loloao.common.RedisManager;
import com.loloao.oauth.OAuthRealm;
import com.loloao.oauth.OAuthSessionDAO;
import com.loloao.oauth.OAuthSessionManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();  
        filterChainDefinitionMap.put("/", "anon");

        /*
        filterChainDefinitionMap.put("/static/**", "anon"); 
        filterChainDefinitionMap.put("/blogFile/**", "anon");
        filterChainDefinitionMap.put("/login", "anon"); 
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/users/currentUser", "anon");
        filterChainDefinitionMap.put("/tags", "anon");
        filterChainDefinitionMap.put("/categorys", "anon");
        filterChainDefinitionMap.put("/star", "anon");
        filterChainDefinitionMap.put("/star/loadStar", "anon");
        filterChainDefinitionMap.put("/comments/article/**", "anon");
        filterChainDefinitionMap.put("/articles", "anon");
        filterChainDefinitionMap.put("/articles/*", "anon");
        filterChainDefinitionMap.put("/articles/view/**", "anon");
        filterChainDefinitionMap.put("/articles/listArchives", "anon");
        filterChainDefinitionMap.put("/**", "authc");*/

        //filterChainDefinitionMap.put("/**/create", "authc");
        //filterChainDefinitionMap.put("/**/update", "authc");
        //filterChainDefinitionMap.put("/**/delete", "authc");
        //filterChainDefinitionMap.put("/upload", "authc");

        filterChainDefinitionMap.put("/**", "anon");

        //返回json数据，由前端跳转
        shiroFilterFactoryBean.setLoginUrl("/handleLogin");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    @Bean
    public OAuthRealm oAuthRealm() {
        OAuthRealm myShiroRealm = new OAuthRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }


    @Bean
    public SecurityManager securityManager(OAuthRealm oAuthRealm, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(oAuthRealm);
        securityManager.setSessionManager(sessionManager);
        // 自定义缓存实现 使用redis  
        //securityManager.setCacheManager(cacheManager());  
        return securityManager;
    }

    @Bean
    public SessionManager sessionManager(OAuthSessionDAO authSessionDAO) {
        OAuthSessionManager oAuthSessionManager = new OAuthSessionManager();
        oAuthSessionManager.setSessionDAO(authSessionDAO);
        return oAuthSessionManager;
    }


    @Bean
    public OAuthSessionDAO authSessionDAO(RedisManager redisManager) {
        OAuthSessionDAO authSessionDAO = new OAuthSessionDAO();
        authSessionDAO.setRedisManager(redisManager);
        return authSessionDAO;
    }


    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
