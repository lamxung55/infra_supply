/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.converter;

import com.mine.model.DepentPropCatBO;

import javax.faces.convert.FacesConverter;

/**
 *
 * @author vinhvh
 */
@FacesConverter("depentPropCatConverter")
public class DepentPropCatConverter extends BaseConverter<DepentPropCatBO> {
    @Override
    public String getFieldId() {
        return "depentPropCatId";
    }
    
}
