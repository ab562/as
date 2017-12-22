package com.as.shiro.realm;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StatelessRealm extends AuthorizingRealm {
	private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持StatelessToken类型的Token
        return token instanceof StatelessToken;
    }
    @SuppressWarnings("unused")
	@Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //根据用户名查找角色，请根据需求实现
    	//目前没有上
        return null;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    	//次方法的调用时机为 com.as.shiro.filter.StatelessAuthcFilter.onAccessDenied(ServletRequest, ServletResponse)
    	//内调用 getSubject(request, response).login(token);
    	//目前没用上
    	return null;
    }

    
}
