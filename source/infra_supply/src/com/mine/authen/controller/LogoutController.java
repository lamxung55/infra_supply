/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.authen.controller;

import com.mine.authen.util.SessionUtil;
import com.mine.util.Constants;
import org.apache.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Date;

@ManagedBean
@ViewScoped
public class LogoutController implements Serializable {
	private static final long serialVersionUID = 1945054393262412859L;
	private static Logger logger = Logger.getLogger("LogoutController.class");

	public void logout() {
		SessionUtil.getCurrentSession().invalidate();
		String user = (String) SessionUtil.getCurrentSession().getAttribute(
				Constants.HTTP_SESSION_ATTRIBUTE_USERNAME);
		SessionUtil.getCurrentSession().setAttribute(
				Constants.HTTP_SESSION_LOGGED, null);
		SessionUtil.getCurrentSession().setAttribute(
				Constants.HTTP_SESSION_USER_LOGGED, Boolean.FALSE);
		SessionUtil.getCurrentSession().setAttribute(
				Constants.HTTP_SESSION_ATTRIBUTE_USERNAME, null);
		SessionUtil.getCurrentSession().setAttribute(
				Constants.HTTP_SESSION_ATTRIBUTE_LAST_REQUEST_URI, null);
		logger.info(new Date() + ": Username: " + user + " đã thoát!");
		new CheckRoles().redirectPage(Constants.HOME_PAGE);
	}
	
	public void login() {
		SessionUtil.getCurrentSession().invalidate();
		String user = (String) SessionUtil.getCurrentSession().getAttribute(
				Constants.HTTP_SESSION_ATTRIBUTE_USERNAME);
		SessionUtil.getCurrentSession().setAttribute(
				Constants.HTTP_SESSION_LOGGED, null);
		SessionUtil.getCurrentSession().setAttribute(
				Constants.HTTP_SESSION_USER_LOGGED, Boolean.FALSE);
		SessionUtil.getCurrentSession().setAttribute(
				Constants.HTTP_SESSION_ATTRIBUTE_USERNAME, null);
		SessionUtil.getCurrentSession().setAttribute(
				Constants.HTTP_SESSION_ATTRIBUTE_LAST_REQUEST_URI, null);
		logger.info(new Date() + ": Username: " + user + " đã thoát!");
		new CheckRoles().redirectPage(Constants.LOGIN_PAGE);
	}
}
