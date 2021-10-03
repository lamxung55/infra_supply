/*
 * Created on Jun 7, 2013
 *
 * Copyright (C) 2013 by Viettel Network Company. All rights reserved
 */
package com.mine.util;

import com.mine.filter.JSF2Filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
//import com.viettel.vsa.token.ObjectToken;
//import com.viettel.vsa.token.RoleToken;
//import com.viettel.vsa.token.UserToken;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import viettel.passport.client.ObjectToken;
import viettel.passport.client.RoleToken;
import viettel.passport.client.UserToken;

/**
 * Dinh nghia cac ham thao tac voi session cua ca he thong.
 * 
* @author
 * @since Jun 7, 2013
 * @version 1.0.0
 */
@ManagedBean
@RequestScoped
public class SessionUtil extends SessionWrapper {

    private static final long serialVersionUID = -7313741895804416337L;
    protected static final Logger logger = LoggerFactory.getLogger(SessionUtil.class);

    /**
     * Lay gia tri menu default.
     *
     */
    public static String getMenuDefault() {
        try {

            String default_url = rb.getString("defaultUrl");
            if ( !"".equals(default_url)) {
                return default_url;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        
        if (getUrlByKey("/action")) {
            return "/resource/serverNew";
        }
        // Nguoi dung khong co url nao trong he thong
        // Tra ve trang bao loi.
        return Config._ERROR_PAGE;
    }

    public SessionUtil() {
//		mapRoleCode.clear();
        HttpSession session = getCurrentSession();
        UserToken userToken = (UserToken) session.getAttribute("vsaUserToken");
        if (userToken != null && userToken.getRolesList() != null) {
            for (RoleToken roleToken : userToken.getRolesList()) {
//				mapRoleCode.put(roleToken.getRoleCode(), roleToken);
            }
        }
//		mapComponentCode.clear();        
        if (userToken != null && userToken.getComponentList() != null) {
            for (ObjectToken component : userToken.getComponentList()) {
//                mapComponentCode.put(component.getObjectCode(), component);
            }
        }

    }

    private boolean createDB;
    private boolean createServer;

    public boolean isCreateDB() {
        return true;
    }

    public boolean isCreateServer() {
        return true;
    }

//vietnv14 add start
    private static String serviceURL;
    private static String domainCode;
    private static String logoutUrl;
    private static ResourceBundle rb;

    static {
        try {
            rb = ResourceBundle.getBundle("cas_en_US");
            serviceURL = rb.getString("service");
            domainCode = rb.getString("domainCode");
            logoutUrl = rb.getString("logoutUrl");
        } catch (MissingResourceException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public String logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        if (getCurrentSession() != null) {
            getCurrentSession().setAttribute(JSF2Filter._VSA_USER_TOKEN, null);
            getCurrentSession().invalidate();
        }
        String logoutUrl = "logout";
        try {
            logoutUrl = serviceURL;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect(logoutUrl);
        return logoutUrl;
    }

    public void doLogout() throws IOException {
        HttpSession localHttpSession = getCurrentSession();
        UserToken localUserToken = (UserToken) localHttpSession.getAttribute("vsaUserToken");
        String logoutUrlLocal=logoutUrl;
        if (localUserToken != null) {
            logoutUrlLocal = logoutUrlLocal + "?service=" + URLEncoder.encode(serviceURL, "UTF-8");
            logoutUrlLocal = logoutUrlLocal + "&userName=" + localUserToken.getUserName();
            logoutUrlLocal = logoutUrlLocal + "&appCode=" + domainCode;
        }
            localHttpSession.setAttribute("vsaUserToken", null);
            localHttpSession.invalidate();
        
        FacesContext.getCurrentInstance().getExternalContext().redirect(logoutUrlLocal);
    }

//vietnv14 add end
}// End class
