/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.converter;

import com.mine.controller.DeviceInfraController;
import com.mine.datamodel.DeviceEntity;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.util.List;

/**
 *
 * @author TuanAnh Created on Jul 23, 2017, 11:09:29 PM
 */
@FacesConverter("deviceConverter")
public class DeviceConverter implements Converter {

    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                FacesContext facesContext = FacesContext.getCurrentInstance();
//                AdsetController neededBean
//                        = (AdsetController) facesContext.getApplication()
//                        .getVariableResolver().resolveVariable(facesContext, "adsetController");

                DeviceInfraController neededBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{deviceInfraController}", DeviceInfraController.class);
                if(neededBean != null){
                    List<DeviceEntity> lstKeyword = neededBean.getLstdevice();
                    for(DeviceEntity key:lstKeyword){
                        if(key.getId().equals(Integer.parseInt(value))){
                            return key;
                        }
                    }
                }
                return null;
                
//                ThemeService service = (ThemeService) fc.getExternalContext().getApplicationMap().get("themeService");
//                return service.getThemes().get(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        } else {
            return null;
        }
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
         if (object instanceof DeviceEntity) {
            return String.valueOf((((DeviceEntity) object).getId()));
        } else {
            return null;
        }
    }
}
