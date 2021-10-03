/*
 * Created on Jun 7, 2013
 *
 * Copyright (C) 2013 by Viettel Network Company. All rights reserved
 */
package com.mine.util;

import com.mine.common.util.Constant;
import com.mine.common.util.DataUtil;
import com.mine.common.util.ResourceBundleUtils;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import viettel.passport.client.ObjectToken;
import viettel.passport.client.UserToken;

//import com.viettel.vsa.token.ObjectToken;
//import com.viettel.vsa.token.UserToken;

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
	
	/**
	 * Lay gia tri session attribute.
	 * 
	 * @param attributeName
	 * @return
	 */
	public String getSessionAttribute(String attributeName){
		return (String) getCurrentSession().getAttribute(attributeName);
	}

	/**
	 * Lay thong tin cua user hien tai dang login.
	 */
	public static String getCurrentUsername(){
		return (String) getCurrentSession().getAttribute(_VSA_USER_ID);
	}
	
	/**
	 * Kiem tra xem URL nay co duoc truy cap khong.
	 * 
	 * @return
	 */
	public boolean getUrlDisplay(String urlCode){
		HttpSession session = getCurrentSession();
		boolean result = false;
		
		String objToken ;
		UserToken userToken = (UserToken) session.getAttribute(_VSA_USER_TOKEN);
		if (userToken != null) {
			for (ObjectToken ot : userToken.getObjectTokens()) {
				objToken = ot.getObjectUrl();
				if (objToken.equalsIgnoreCase(urlCode)){
					result = true;
					break;
				}
			}
		}
		if(DataUtil.safeEqual(ResourceBundleUtils.getResource("IS_TEST"),"1")) result=true;
		return result;
	}	
	
	protected static Boolean getUrlByKey(String key){
		HttpSession session = getCurrentSession();
		UserToken userToken = (UserToken) session.getAttribute(_VSA_USER_TOKEN);
		if (userToken != null) {
			for (ObjectToken ot : userToken.getObjectTokens()) {
				if (key.equalsIgnoreCase(ot.getObjectUrl()))
					return true;
				
			}
		} else {
			return false;
		}
		
		return false;
	}
       
}// End class
