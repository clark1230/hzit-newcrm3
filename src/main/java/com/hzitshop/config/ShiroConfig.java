package com.hzitshop.config;

import com.google.common.collect.Maps;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
public class ShiroConfig {
	
	/**
	 * FilterRegistrationBean
	 * @return
	 */
/*	@Bean
	public FilterRegistrationBean filterRegistrationBean() {


		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("*//*");
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
    
        return filterRegistration;
	}*/

	@Bean
	public  DelegatingFilterProxyRegistrationBean delegatingFilterProxyRegistrationBean(){
		DelegatingFilterProxyRegistrationBean
				dfr = new DelegatingFilterProxyRegistrationBean("shiroFilter");
		Set<String> url = new HashSet<>();
		url.add("/*");
		dfr.setUrlPatterns(url);
		dfr.setEnabled(true);
		dfr.setDispatcherTypes(DispatcherType.REQUEST);
		return  dfr;
	}

	
	/**
	 * @see ShiroFilterFactoryBean
	 * 过滤器   哪些资源需要放行的
	 * @return
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(){
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager());
		bean.setLoginUrl("/account/login");
		bean.setUnauthorizedUrl("/exception/exception");
		
		Map<String, Filter>filters = Maps.newHashMap();
		//filters.put("perms", urlPermissionsFilter());
		filters.put("anon", new AnonymousFilter());
		bean.setFilters(filters);
		
		Map<String, String> chains = Maps.newHashMap();
		chains.put("/account/captcha","anon");//验证码
		chains.put("/account/checkValidate","anon");
		chains.put("/account/login", "anon");
		chains.put("/account/unauthor", "anon");
		chains.put("/account/logout", "logout");
		chains.put("/assets*/**", "anon");
		chains.put("/static*/**", "anon");
		chains.put("/**", "authc,perms");
		bean.setFilterChainDefinitionMap(chains);
		return bean;
	}
	
	/**
	 * @see org.apache.shiro.mgt.SecurityManager
	 * @return
	 */
	@Bean(name="securityManager")
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(userRealm());
		manager.setCacheManager(cacheManager());
		manager.setSessionManager(defaultWebSessionManager());
		return manager;
	}


	/**
	 * @see DefaultWebSessionManager
	 * @return
	 */
	@Bean(name="sessionManager")
	public DefaultWebSessionManager defaultWebSessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setCacheManager(cacheManager());
		sessionManager.setGlobalSessionTimeout(4*60*60*1000);//4小时
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		sessionManager.setDeleteInvalidSessions(true);
		//sessionManager.setAttribute();
		/*SessionKey sessionKey = null;
		sessionManager.getSessionId(sessionKey);*/
		return sessionManager;
	}
	
	/**
	 * @see UserRealm--->AuthorizingRealm
	 * @return
	 */
	@Bean
	@DependsOn(value="lifecycleBeanPostProcessor")
	public UserRealm userRealm() {
		UserRealm userRealm = new UserRealm();
		userRealm.setCacheManager(cacheManager());//缓存管理
		return userRealm;
	}

	//  开启 Controller中@RequiresPermissions  start
	/**
	 * <bean class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator"
	 depends-on="lifecycleBeanPostProcessor" />
	 * @return
	 */
	@Bean
	@DependsOn(value="lifecycleBeanPostProcessor")
	public   DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
		System.out.println("proxyCreator");
		return new DefaultAdvisorAutoProxyCreator();
	}

	/**
	 * <bean class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
	 <property name="securityManager" ref="securityManager" />
	 </bean>
	 */
	@Bean
	@DependsOn(value="securityManager")
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
		System.out.println("sourceAdvisor");
		   return new AuthorizationAttributeSourceAdvisor();
	}
	//  开启 Controller中@RequiresPermissions  end
	/**
	 * 异常处理
	 * @return
	 */
/*
	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
		SimpleMappingExceptionResolver se = new SimpleMappingExceptionResolver();
		se.setDefaultErrorView("/exception/exception");
		return se;
	}
*/

	/**
	 * url权限过滤
	 * @return
	 */
	@Bean
	public URLPermissionsFilter urlPermissionsFilter() {
		return new URLPermissionsFilter();
	}

	/**
	 * 缓存管理
	 * @return
	 */
	@Bean
	public EhCacheManager cacheManager() {
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
		return cacheManager;
	}
	
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
}