/*
 * Created on Jun 7, 2013
 *
 * Copyright (C) 2013 by mine Network Company. All rights reserved
 */
package com.mine.converter;

import com.mine.util.LanguageBean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.util.Locale;

/**
 * Dung cho truong hop required ma nguoi dung danh toan ky tu space
 * 		Khong can khai bao.
 * 
 * @author
 * @since Jun 7, 2013
 * @version 1.0.0
*/
@FacesConverter("localeConverter")
public class LocaleConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && value.trim().length() > 0) {
            try {
            	//LanguageBean service = (LanguageBean) context.getExternalContext().getApplicationMap().get("language");
                return LanguageBean.getLocales().get(value);
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        else {
            return null;
        }
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value instanceof Locale) {
			Locale new_name = (Locale) value;
			return new_name.getLanguage();
			
		}
		return null;
	}

}
