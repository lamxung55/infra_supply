package com.mine.util;///*
// * Created on Jun 7, 2013
// *
// * Copyright (C) 2013 by mine Network Company. All rights reserved
// */
//package com.mine.util;
//
//import javax.faces.controller.ManagedBean;
//import javax.faces.controller.RequestScoped;
//
//
///**
//* Dinh nghia cac ham thao tac voi session cua ca he thong.
//*
//* @author
//* @since Jun 7, 2013
//* @version 1.0.0
//*/
//@ManagedBean
//@RequestScoped
//public class SessionUtil extends SessionWrapper {
//
//	private static final long serialVersionUID = -7313741895804416337L;
//
//	/**
//	 * Lay gia tri menu default.
//	 * 
//	 */
//	public static String getMenuDefault() {
//		if (! Config._DEFAULT_URL.equals(""))
//			return Config._DEFAULT_URL;
//		
//		if (getUrlByKey("/company")){
//			return "/company";
//		} else if (getUrlByKey("/province")){
//			return "/province";
//		} else if (getUrlByKey("/user")){
//			return "/user";
//		} 
//		// Nguoi dung khong co url nao trong he thong
//		// Tra ve trang bao loi.
//		return Config._ERROR_PAGE;
//	}
//	
//}// End class
