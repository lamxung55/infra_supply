package com.mine.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@SuppressWarnings("serial")
@ManagedBean(name="language")
@SessionScoped
public class LanguageBean implements Serializable {

	protected static Map<String, Object> locales ;

	public static Map<String, Object> getLocales() {
		return locales;
	}

	public static void setLocales(Map<String, Object> locales) {
		LanguageBean.locales = locales;
	}


	static {
		locales = new HashMap<String, Object>();
		Locale vn = new Locale("vi", "VN");
		locales.put(vn.getLanguage(), vn);
		Locale us = new Locale("en", "US");
		locales.put(us.getLanguage(), us);
	}
	// ngon ngu mac dinh tieng anh_start
	private String localeCode = new Locale("en", "US").getLanguage();
	//ngon ngu mac dinh tieng anh_end
	public List<String> getCountries(){
		List<String> countries = new ArrayList<>();
		for (Iterator<String> iterator = locales.keySet().iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			countries.add(string);
		}
		return countries;
	}
	public List<Locale> getCountrie2s(){
		List<Locale> countries = new ArrayList<>();
		for (Iterator<String> iterator = locales.keySet().iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			countries.add((Locale) locales.get(string));
		}
		return countries;
	}

	public void countryLocaleCodeChanged (ValueChangeEvent e){

		String newLocaleCode = e.getNewValue().toString();
		for (Iterator<String> iterator = locales.keySet().iterator(); iterator.hasNext();) {
			String localeCode = (String) iterator.next();
			if(localeCode.equals(newLocaleCode)){
				FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) locales.get(localeCode));
				// hienhv4_20160910_fix loi message_start
				MessageUtil.setResourceBundle();
				// hienhv4_20160910_fix loi message_end
			}
		}
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}
	public String getLocaleName(String localeCode){
		switch (localeCode) {
			case "vi":
				return "Tiếng Việt";
			case "en":
				return "English";
			default :
				break;
		}
		return "";
	}
}
