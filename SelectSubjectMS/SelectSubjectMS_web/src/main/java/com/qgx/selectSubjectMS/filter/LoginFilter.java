package com.qgx.selectSubjectMS.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * �ж�SessionʧЧ������
 * @author goxcheer
 *
 */
public class LoginFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
				
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		    
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		
		Object object = httpServletRequest.getSession().getAttribute("currentUser");
		
		if (object == null) {
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login.jsp");
		} else {
			chain.doFilter(httpServletRequest, httpServletResponse);
		}
		
		return;
		
	}

	@Override
	public void destroy() {
		
	}

}
