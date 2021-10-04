package com.mine.authen.util;

import com.mine.authen.bean.OamUserRole;
import com.mine.authen.service.RoleManageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("role")
public class RoleConverter implements Converter {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        try {
            return new RoleManageServiceImpl().findById(Integer.parseInt(value));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component,
            Object value) {
        try {
            if (value instanceof OamUserRole) {
                return ((OamUserRole) value).getRoleId().toString();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;

    }
}
