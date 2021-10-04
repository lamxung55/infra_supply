/*
 * Created on Jun 7, 2013
 *
 * Copyright (C) 2013 by mine Network Company. All rights reserved
 */
package com.mine.authen.controller;

import com.mine.authen.util.SessionUtil;
import com.mine.exception.SysException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;

/**
 * Chức năng chính để forward các url tới phần phân quyền.
 * 
 * @author
 * @since Jun 7, 2013
 * @version 1.0.0
 */
@RequestScoped
@ManagedBean(name = "forwardService")
public class ForwardController implements Serializable {
	private static final long serialVersionUID = 4870520554535423726L;
	// Trang home.
	private static final String _HOME_PAGE = "/home";
        
        protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/**
	 * Dieu huong den trang home page.
	 * 
	 */
	private void homeForward() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		try {
			fc.getExternalContext().redirect(req.getContextPath() + _HOME_PAGE);
		} catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Redirect toi trang home.
	 * 
	 * @throws IOException
	 */
	public void doForward(final ComponentSystemEvent event) throws IOException {
		homeForward();
	}

	/**
	 * Dieu huong den trang mac dinh cua user.
	 * 
	 * @throws IOException
	 */
	public void doRedirect(final ComponentSystemEvent event) throws IOException {
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();

			// Lay gia tri menu default cua user dang nhap.
			String defaultUrl = SessionUtil.getMenuDefault();
			if (defaultUrl == "")
				homeForward();
			else
				fc.getExternalContext().redirect(req.getContextPath() + defaultUrl);
		} catch (SysException e) {
                        LOGGER.error(e.getMessage(), e);
		}
	}
}