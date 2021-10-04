/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.authen.util;

import com.mine.authen.bean.OamUserRole;
import com.mine.authen.controller.CheckRoles;
import com.mine.authen.service.RoleManageServiceImpl;
import com.mine.util.Constants;
import com.ocpsoft.pretty.PrettyContext;
import com.mine.exception.AppException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class CheckLoginFilter implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {
     
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req=null;
        HttpServletResponse res=null;
        if(request instanceof HttpServletRequest)
            req=(HttpServletRequest) request;
        if(response instanceof HttpServletResponse)
            res=(HttpServletResponse) response;
        
        if(req!=null){
            HttpSession session=req.getSession(true);
            String contextPath=req.getContextPath();
            Boolean logged=(Boolean) session.getAttribute(Constants.HTTP_SESSION_LOGGED);
            if(logged==null || logged==Boolean.FALSE){
                logged= Boolean.FALSE;
                session.setAttribute(Constants.HTTP_SESSION_ATTRIBUTE_USERNAME, Constants.HTTP_SESSION_ATTRIBUTE_GUEST_USERNAME);

                try {
                    RoleManageServiceImpl roleManageService=new RoleManageServiceImpl();
                    List<OamUserRole> roles= roleManageService.findByRoleName("guest");
                    OamUserRole role= new OamUserRole();
                    if(roles.size()<1){
                        role.setRoleId(1);
                        role.setRoleName("guest");
                        role.setPageUrl("/home");
                        role.setDescription("This role can't be deleted!");
                        roleManageService.insert(role);
                    }else{
                        role=roles.get(0);
                    }
                    session.setAttribute(Constants.HTTP_SESSION_ATTRIBUTE_PAGE_ROLE, role.getPageUrl());
                    
                } catch (AppException a){
                    a.printStackTrace();
                }
            }
            
            String path= PrettyContext.getCurrentInstance(req).getRequestURL().toURL();
            
            if(logged==Boolean.FALSE && session.getAttribute(Constants.HTTP_SESSION_ATTRIBUTE_LAST_REQUEST_URI)==null){
                session.setAttribute(Constants.HTTP_SESSION_ATTRIBUTE_LAST_REQUEST_URI,Constants.HOME_PAGE );
            }else{
                session.setAttribute(Constants.HTTP_SESSION_ATTRIBUTE_LAST_REQUEST_URI, path);
            }
            
            if(path.contains("login") && logged==Boolean.FALSE || path.contains("javax.faces.resource")){
                chain.doFilter(request, response);
            }else if(!path.contains("login") && logged==Boolean.TRUE || session.getAttribute(Constants.HTTP_SESSION_ATTRIBUTE_USERNAME).equals(Constants.HTTP_SESSION_ATTRIBUTE_GUEST_USERNAME)){
                String pagesRole=(String) session.getAttribute(Constants.HTTP_SESSION_ATTRIBUTE_PAGE_ROLE);
                String username=(String) session.getAttribute(Constants.HTTP_SESSION_ATTRIBUTE_USERNAME);
                if(new CheckRoles().hasRightPage(pagesRole,path, username))
                    chain.doFilter(request, response);
                else{
                    if (path.contains(Constants.ERROR_PAGE)) {                            
                            chain.doFilter(request, response);
                    }else {
                            res.sendRedirect(contextPath.concat(Constants.ERROR_PAGE));
                    }
                }
                    
            }else{
                session.setAttribute(Constants.HTTP_SESSION_LOGGED,Boolean.FALSE);
                session.setAttribute(Constants.HTTP_SESSION_USER_LOGGED,Boolean.FALSE);
                session.setAttribute(Constants.HTTP_SESSION_ATTRIBUTE_USERNAME, Constants.HTTP_SESSION_ATTRIBUTE_GUEST_USERNAME);
                res.sendRedirect(contextPath.concat(Constants.HOME_PAGE));
                
            }
        }  
    }

    @Override
    public void destroy() {
       
    }
    
}
