/*
 * Created on Jun 7, 2013
 *
 * Copyright (C) 2013 by mine Network Company. All rights reserved
 */
package com.mine.authen.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
* Các hàm thao tác với session cơ bản của cả hệ thống. 
*
* @author
* @since Jun 7, 2013
* @version 1.0.0
*/

public class SessionWrapper implements Serializable{
	private static final long serialVersionUID 	= -8318262775763386620L;
	private static final String _VSA_USER_TOKEN = "vsaUserToken";
	private static final String _VSA_USER_ID 	= "netID";
	
	/**
	 * Get current session cua he thong.
	 * 
	 * @return current session
	 */
	public static HttpSession getCurrentSession() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		return request.getSession();
	}
	

	
	protected static Boolean getUrlByKey(String key){
//		HttpSession session = getCurrentSession();
//		String url;
//		UserToken userToken = (UserToken) session.getAttribute(_VSA_USER_TOKEN);
//		if (userToken != null) {
//			for (ObjectToken ot : userToken.getObjectTokens()) {
//				url = ot.getObjectUrl();
//				if (key.equalsIgnoreCase(url)){
//                                    return true;
//                                }else {
//
//                                }
//
//			}
//		} else {
//			return false;
//		}
		
		return false;
	}
}// End class
