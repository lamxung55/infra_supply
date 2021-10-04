package com.mine.filter;//package com.mine.filter;
//
//import java.io.IOException;
//import java.util.regex.Pattern;
//
//import javax.faces.application.ResourceHandler;
//import javax.servlet.DispatcherType;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import com.mine.vsa.token.ObjectToken;
//import com.mine.vsa.token.UserToken;
//
///**
// * Filter chung cua ca he thong doi voi duong dan tuyet doi. Tat ca cac module
// * deu phai khai bao phan quyen o day.
// * 
// * @author hanh45
// *
// */
////@WebFilter(dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR}, urlPatterns = {"/faces/*"})
//public class JSF2Filter implements Filter {
//	private static String _REQUEST_PATH = "";
//	private static final String _HOME_PATH = "/home";
//	private static final String _ERROR_PATH = "/error";
//	private static final String _XHTML = ".xhtml";
//	private static final String _FACES = "/faces/";
//	private static final String _VSA_USER_TOKEN = "vsaUserToken";
//
//	public JSF2Filter() {
//	}
//
//	public void init(FilterConfig fConfig) throws ServletException {
//	}
//
//	public void destroy() {
//	}
//
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpSession session = req.getSession();
//		HttpServletResponse res = (HttpServletResponse) response;
//		boolean checkAuth = false;
//
//		// Skip JSF resources (CSS/JS/Images/etc)
//		if (!req.getRequestURI().startsWith(req.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) {
//			res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP
//																					// 1.1.
//			res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
//			res.setDateHeader("Expires", 0); // Proxies.
//		}
//
//		_REQUEST_PATH = req.getServletPath();
//		int pos = _REQUEST_PATH.indexOf("/", _FACES.length());
//		String SUB_REQUEST_PATH = _REQUEST_PATH.substring(0, pos);
//		_REQUEST_PATH = _REQUEST_PATH.substring(0, _REQUEST_PATH.indexOf(_XHTML) + _XHTML.length());
//
//		// Kiem tra session timeout.
//		UserToken userToken = (UserToken) session.getAttribute(_VSA_USER_TOKEN);
//		if (userToken != null) {
//			// Current session on.
//			switch (SUB_REQUEST_PATH) {
//				case _FACES + "home" :
//					checkAuth = true;
//					break;
//				case _FACES + "action" :
//					checkAuth = getPermission(session, _REQUEST_PATH);
//					break;
//				default :
//					checkAuth = getPermission(session, _REQUEST_PATH);
//					break;
//			}
//		} else {
//			// Session timeout.
//			// Xu ly cho ajax khi session timeout.
//			handleSessionTimeout(req, res);
//		}
//
//		if (checkAuth)
//			chain.doFilter(request, response);
//		else
//		// Dieu huong den trang bao loi.
//		if (!res.isCommitted())
//			res.sendRedirect(req.getContextPath() + _ERROR_PATH);
//	}
//
//	/**
//	 * Xu ly session timeout.
//	 * 
//	 * @throws IOException
//	 */
//	private void handleSessionTimeout(HttpServletRequest req, HttpServletResponse res) throws IOException {
//		if ("partial/ajax".equals(req.getHeader("Faces-Request"))) {
//			res.setContentType("text/xml");
//			res.getWriter().append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
//					.printf("<partial-response><redirect url=\"%s\"></redirect></partial-response>", req.getContextPath() + _HOME_PATH);
//		} else {
//			if (!res.isCommitted())
//				res.sendRedirect(req.getContextPath() + _HOME_PATH);
//		}
//	}
//
//	/**
//	 * Ham kiem tra quyen cua user tren session
//	 * 
//	 * @param urlCode
//	 * @return
//	 */
//	private boolean getUrlPermission(HttpSession session, String urlCode) {
//		boolean result = false;
//
//		String objToken = "";
//		UserToken userToken = (UserToken) session.getAttribute(_VSA_USER_TOKEN);
//		if (userToken != null) {
//			for (ObjectToken ot : userToken.getObjectTokens()) {
//				objToken = ot.getObjectUrl();
//				if (objToken.equalsIgnoreCase(urlCode)) {
//					result = true;
//					break;
//				}
//			}
//		}
//
//		return result;
//	}
//	private boolean getPermission(HttpSession session, String requestPath) {
//		// //System.out.println("vao getManagerPermisstion !!!!!!!!!!!!!!!!!!!!!!!!! ");
//		// Logger.getLogger("huynx6_3").info(requestPath);
//		boolean checkAuth = false;
//		try {
//			if (Pattern.compile("^" + _FACES + ".+?" + "/index.xhtml$").matcher(requestPath).find())
//				checkAuth = getUrlPermission(session, requestPath.substring(_FACES.length() - 1, requestPath.indexOf("/index.xhtml")));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return checkAuth;
//	}
//}
