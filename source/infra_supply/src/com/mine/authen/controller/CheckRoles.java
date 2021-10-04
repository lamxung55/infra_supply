/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.authen.controller;

import com.mine.util.Constants;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@ManagedBean(name = "smscCheckRoles")
@ViewScoped
public class CheckRoles implements Serializable {

    public boolean hasRightPage(String pagesRole, String pageRole, String username) {
        pageRole = pageRole.toUpperCase().substring(1);
        pagesRole = pagesRole.toUpperCase();

        if (!username.equalsIgnoreCase("guest") && pageRole.endsWith("OAM")) {
            return true;
        }

        if (pagesRole.contains(pageRole) || pageRole.contains("CHANGE-PASSWORD")) {
            return true;
        } else if (pagesRole.contains(";ALL") || pagesRole.contains(pageRole.split("/")[0] + "/ALL")) {
            return true;
        }
        return false;

    }

    public boolean hasRightPage(String pageRole) {
        String username = (String) getCurrentSession().getAttribute(Constants.HTTP_SESSION_ATTRIBUTE_USERNAME);
        String pagesRole = (String) getCurrentSession().getAttribute(Constants.HTTP_SESSION_ATTRIBUTE_PAGE_ROLE);
        if (pagesRole == null) {
            return false;
        }
        pageRole = pageRole.toUpperCase().substring(1);
        pagesRole = pagesRole.toUpperCase();

        if (!username.equalsIgnoreCase("guest") && pageRole.endsWith("OAM")) {
            return true;
        }

        if (pagesRole.contains(pageRole) || pageRole.contains("HOME") || pageRole.contains("CHANGE-PASSWORD")) {
            return true;
        } else if (pagesRole.contains(";ALL") || pagesRole.contains(pageRole.split("/")[0] + "/ALL")) {
            return true;
        }
        return false;

    }

    public boolean hasRightAction(String action) {

        String currentPage = (String) getCurrentSession().getAttribute(Constants.HTTP_SESSION_ATTRIBUTE_LAST_REQUEST_URI);
        String actions = (String) getCurrentSession().getAttribute(Constants.HTTP_SESSION_ATTRIBUTE_ACTION_ROLE);
        String[] tokens = currentPage.split("/");
        String tempCurrentPage = null;
        if (tokens.length == 2) {
            currentPage = tokens[1];
            tempCurrentPage = currentPage;
        } else {
            tempCurrentPage = tokens[tokens.length - 2];
            currentPage = tokens[tokens.length - 1];
        }

        actions = actions.toUpperCase();
        action = action.toUpperCase();

        if (actions.contains(currentPage + "-ALL") || actions.contains(";ALL") || actions.contains(tempCurrentPage + "-ALL")) {
            return true;
        }

        if (actions.contains(currentPage + "-" + action)) {
            return true;
        }
        return false;
    }

    public void redirectPage(String path) {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest req = (HttpServletRequest) context.getRequest();
        try {
            context.redirect(req.getContextPath().concat(path));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void redirectPage2(String path) {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect(path);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static HttpSession getCurrentSession() {
        HttpServletRequest request = (HttpServletRequest) FacesContext
                .getCurrentInstance().getExternalContext().getRequest();
        return request.getSession();
    }
}
