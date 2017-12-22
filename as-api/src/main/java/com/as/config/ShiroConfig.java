package com.as.config;

import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.as.shiro.Constants;
import com.as.shiro.filter.StatelessAuthcFilter;
import com.as.shiro.mgt.StatelessDefaultSubjectFactory;
import com.as.shiro.realm.StatelessRealm;
import com.as.shiro.realm.StatelessToken;

@Configuration
public class ShiroConfig {
	@Bean(name="statelessRealm")
	public StatelessRealm statelessRealm(){
		StatelessRealm statelessRealm = new StatelessRealm();
		statelessRealm.setCachingEnabled(false);
		return statelessRealm;
	}
	@Bean(name="subjectFactory")
	public StatelessDefaultSubjectFactory subjectFactory(){
		return new StatelessDefaultSubjectFactory();
	}
	@Bean(name="sessionManager")
	public DefaultSessionManager sessionManager(){
		DefaultSessionManager dsm= new DefaultSessionManager();
		dsm.setSessionValidationSchedulerEnabled(false);
		return dsm;
	}
	@Bean(name="securityManager")
	public DefaultWebSecurityManager securityManager(StatelessDefaultSubjectFactory subjectFactory
			,DefaultSessionManager sessionManager
			,StatelessRealm statelessRealm){
		DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
		securityManager.setSubjectFactory(subjectFactory);
		securityManager.setSessionManager(sessionManager);
		DefaultSessionStorageEvaluator df = (DefaultSessionStorageEvaluator) ((DefaultSubjectDAO)securityManager.getSubjectDAO()).getSessionStorageEvaluator();
		  /*
         * 禁用使用Sessions 作为存储策略的实现，但它没有完全地禁用Sessions
         * 所以需要配合context.setSessionCreationEnabled(false);
         */
		df.setSessionStorageEnabled(false);
		securityManager.setRealm(statelessRealm);
		return securityManager;
	}
	@Bean(name="authenticationToken")
	public  AuthenticationToken authenticationToken(){
		StatelessToken st = new StatelessToken();
		st.setClientDigest(Constants.PARAM_DIGEST);
		st.setUsername(Constants.PARAM_USERNAME);
		return st;
		
	}
	@Bean(name="methodInvokingFactoryBean")
	public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultWebSecurityManager  securityManager){
		MethodInvokingFactoryBean mif=new MethodInvokingFactoryBean();
		mif.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		mif.setArguments(securityManager);
		return mif;
	}
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager  securityManager){
		ShiroFilterFactoryBean sf =new ShiroFilterFactoryBean();
		sf.setSecurityManager(securityManager);
		Map<String, Filter> filters = sf.getFilters();
		Map<String, String> fcmap = sf.getFilterChainDefinitionMap();
		filters.put("statelessAuthc", statelessAuthcFilter());
		fcmap.put("/anon/*", "anon");
		fcmap.put("/**", "statelessAuthc");
		return sf;
		
	}
	
	public StatelessAuthcFilter statelessAuthcFilter(){
		return new StatelessAuthcFilter();
	}
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
		return new LifecycleBeanPostProcessor();
	}
}
