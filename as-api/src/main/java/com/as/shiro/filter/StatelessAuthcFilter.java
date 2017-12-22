package com.as.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.as.util.HmacSHA256Utils;
import com.as.util.SysCode;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class StatelessAuthcFilter extends AccessControlFilter {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		String token =null;
		try {
			HttpServletRequest req = (HttpServletRequest)request;
			//取出请求头里面的token
			token = req.getHeader("Authorization");
			//构建签名工具
			Algorithm algorithm=Algorithm.HMAC256(HmacSHA256Utils.KEY);
			DecodedJWT de = JWT.decode(token.split(" ")[1].trim());
			 Integer id = de.getClaim("id").asInt();
			 //验证
			algorithm.verify(de);
			log.info("请求id="+id);
			log.info(token);
			return true;
		} catch (Exception e) {
			log.error("登录失败 token="+token);
			onLoginFail(response); // 6、登录失败
			return false;
		}
	}

	// 登录失败时默认返回401状态码
	private void onLoginFail(ServletResponse response) throws IOException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		httpResponse.getWriter().write(SysCode.SYS_E_118);
	}

	
}
