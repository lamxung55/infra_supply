
/*
 * CharsetFilter.java	1.0  01/01/2010
 *
 * Copyright 2006 mine Telecom. All rights reserved.
 * mine PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mine.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

/**
 * @author dungvv8
 */
public class ActionLogFilter implements Filter {

    private static final Logger logger = Logger.getLogger("LogActionFilter");
    private static final String SYS_NAME = "IIM";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String ip = "";
        Date startTime = null;
        Date endTime = null;
        String userName = "";
        String path = "";
        String uri = "";
        String className = "";
        String param = "";
        String clientKpiId = "";
        String description = "";
        Long duration = 0L;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        String requestId = "";
        HttpSession session = null;
        try {
            //set characterEncoding UTF-8
            request.setCharacterEncoding("UTF-8");
            if (request instanceof HttpServletRequest) {
                HttpServletRequest hRequest = (HttpServletRequest) request;
                
                clientKpiId = hRequest.getHeader("VTS-KPIID");
                ip = hRequest.getHeader("X-ClientIP");
                if (ip == null || ip.isEmpty()) {
                    ip = hRequest.getRemoteAddr();
                }
                path = hRequest.getServerName() + ":" + hRequest.getServerPort();
                uri = hRequest.getRequestURI();
                param = getAllParameter(hRequest);
                String[] methods = uri.split("!");
                String[] tmp = uri.split("/");
                if (tmp.length > 2) {
                    uri = "/" + tmp[2];
                }
                if (methods.length > 1) {
                    String[] method = methods[1].split("\\.");
                    if (method.length > 0) {
                        className = className + "." + method[0];
                    }
                }
            }
            startTime = new Date();
            requestId = String.valueOf(startTime.getTime());
            String startTimeStr = dateFormat.format(startTime);
            String logStart = MessageFormat.format("{0}|{1}|{2}|{3}|{4}|{5}|{6}|{7}|{8}|{9}|{10}|{11}|{12}",
                    "start_action", SYS_NAME, startTimeStr, userName, ip,
                    path, uri, param, className, "", description, clientKpiId, requestId);
            logger.info(logStart);

            chain.doFilter(request, response);
            endTime = new Date();
            duration = endTime.getTime() - startTime.getTime();

        } finally {
            String checkDuration = "";
            if (duration > 10000) {
                checkDuration = "CHAM";
            }
            String endTimeStr = null;
            if (endTime != null) {
                endTimeStr = dateFormat.format(endTime);
            }
            description = checkDuration;
            String logEnd = MessageFormat.format("{0}|{1}|{2}|{3}|{4}|{5}|{6}|{7}|{8}|{9}|{10}|{11}|{12}",
                    "end_action", SYS_NAME, endTimeStr, userName, ip,
                    path, uri, param, className, duration.toString(), description, clientKpiId, requestId);
            logger.info(logEnd);
        }
    }

    private static String getAllParameter(HttpServletRequest req) {
        StringBuilder params = new StringBuilder();
        try {
            Enumeration parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = (String) parameterNames.nextElement();
                if ("javax.faces.ViewState".equals(paramName)) {
                    continue;
                }
                params.append(paramName).append(":");

                String[] paramValues = req.getParameterValues(paramName);
                int length = paramValues.length;
                for (int i = 0; i < length; i++) {
                    String paramValue = paramValues[i];
                    if (paramValue != null) {
                        paramValue = paramValue.replaceAll("\n", " ");
                    }
                    params.append(paramValue).append(";");
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        if (params.length() > 1) {
            params.deleteCharAt(params.length() - 1);
        }
        return params.toString();
    }

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void destroy() {
    }

//    private UICommand findInvokedCommandComponent(FacesContext context) {
//        try {
//
//            Map<String, String> params = context.getExternalContext().getRequestParameterMap();
//            Set<String> clientIds = new HashSet<>();
//
//            if (context.getPartialViewContext().isAjaxRequest()) {
//                clientIds.add(params.get("javax.faces.source")); // This covers <f:ajax> inside UICommand.
//            } else {
//                for (Entry<String, String> entry : params.entrySet()) {
//                    if (entry.getKey().equals(entry.getValue())) { // This covers UIForm and UICommand components.
//                        clientIds.add(entry.getKey());
//                    }
//                }
//            }
//
//            EnumSet<VisitHint> hints = EnumSet.of(VisitHint.SKIP_UNRENDERED);
//            final UICommand[] found = new UICommand[1];
//            context.getViewRoot().visitTree(VisitContext.createVisitContext(context, clientIds, hints), new VisitCallback() {
//                @Override
//                public VisitResult visit(VisitContext context, UIComponent target) {
//                    if (target instanceof UICommand) {
//                        found[0] = (UICommand) target;
//                        return VisitResult.COMPLETE;
//                    } else {
//                        return VisitResult.ACCEPT;
//                    }
//                }
//            });
//
//            return found[0];
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    protected FacesContext getFacesContext(HttpServletRequest request, HttpServletResponse response) {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        if (facesContext == null) {
//
//            FacesContextFactory contextFactory = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
//            LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
//            javax.faces.lifecycle.Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
//
//            facesContext = contextFactory.getFacesContext(request.getSession().getServletContext(), request, response, lifecycle);
//
//            InnerFacesContext.setFacesContextAsCurrentInstance(facesContext);
//
//            UIViewRoot view = facesContext.getApplication().getViewHandler().createView(facesContext, "");
//            facesContext.setViewRoot(view);
//        }
//        return facesContext;
//    }

    // You need an inner class to be able to call FacesContext.setCurrentInstance
// since it's a protected method
//    private abstract static class InnerFacesContext extends FacesContext {
//
//        protected static void setFacesContextAsCurrentInstance(FacesContext facesContext) {
//            FacesContext.setCurrentInstance(facesContext);
//        }
//    }

}
