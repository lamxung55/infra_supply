/*
 * Created on Jun 7, 2013
 *
 * Copyright (C) 2013 by Viettel Network Company. All rights reserved
 */
package com.mine.resource;

import com.mine.util.LanguageBean;
import com.mine.util.MessageUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

/**
 *
 * Messages Utils for i18n
 *
 * @author
 * @since Jun 7, 2013
 * @version 1.0.0
 *
*/
public class AppMessages extends ResourceBundle {
	protected static final String BUNDLE_NAME = "com.mine.resource.messages";
	protected static final String BUNDLE_EXTENSION = "properties";
	protected static final String CHARSET = "UTF-8";
	protected static final Control UTF8_CONTROL = new UTF8Control();

	public AppMessages() {
		ResourceBundle.clearCache();
//		MessageUtil.local = (Locale) LanguageBean.getLocales().get(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()) ;
		MessageUtil.setLocal( (Locale) LanguageBean.getLocales().get(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage())) ;
//		setParent(ResourceBundle.getBundle(BUNDLE_NAME,MessageUtil.local, UTF8_CONTROL));
		setParent(ResourceBundle.getBundle(BUNDLE_NAME,MessageUtil.getLocal(), UTF8_CONTROL));
            
//		setParent(ResourceBundle.getBundle(BUNDLE_NAME, FacesContext
//				.getCurrentInstance().getViewRoot().getLocale(), UTF8_CONTROL));
	}

	@Override
	protected Object handleGetObject(String key) {
		return parent.getObject(key);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Enumeration getKeys() {
		return parent.getKeys();
	}

	public static class UTF8Control extends Control {
		public ResourceBundle newBundle(String baseName, Locale locale,
				String format, ClassLoader loader, boolean reload)
				throws IllegalAccessException, InstantiationException,
				IOException {
			String bundleName = toBundleName(baseName, locale);
			String resourceName = toResourceName(bundleName, BUNDLE_EXTENSION);

			ResourceBundle bundle = null;
			InputStream stream = null;
			if (reload) {
				URL url = loader.getResource(resourceName);
				if (url != null) {
					URLConnection connection = url.openConnection();
					if (connection != null) {
						connection.setUseCaches(false);
						stream = connection.getInputStream();
					}
				}
			} else {
				stream = loader.getResourceAsStream(resourceName);
			}
			if (stream != null) {
				try {
					bundle = new PropertyResourceBundle(new InputStreamReader(
							stream, CHARSET));
				} finally {
					stream.close();
				}
			}
			return bundle;
		}
	}
}