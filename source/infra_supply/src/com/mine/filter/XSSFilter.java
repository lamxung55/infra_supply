/*
 * Created on Mar 24, 2014
 *
 * Copyright (C) 2014 by mine Network Company. All rights reserved
 */
package com.mine.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * XSSFilter.java
 * 
 * @author
 * @since Mar 24, 2014
 * @version 1.0.0
 */
@WebFilter(dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD,
		DispatcherType.INCLUDE, DispatcherType.ERROR }, urlPatterns = { "/faces/*" })
public class XSSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

		chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request),
				response);
	}
}
