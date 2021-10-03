/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.controller;

import com.mine.common.util.Constant;
import com.mine.common.util.StringUtils;
import com.mine.exception.AppException;
import com.mine.exception.SysException;
import com.mine.lazy.LazyDataModelBaseNew;
import com.mine.model.DepentPropCatBO;
import com.mine.model.MdDependentBO;
import com.mine.persistence.DepentPropCatServiceImpl;
import com.mine.persistence.MdDependentServiceImpl;
import com.mine.util.MessageUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import static org.apache.logging.log4j.MarkerManager.clear;

import org.primefaces.context.RequestContext;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.Visibility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vinhvh
 */
@ViewScoped
@ManagedBean
public class DepentPropCatController {
    protected static final Logger logger = LoggerFactory.getLogger(DepentPropCatController.class);
    @ManagedProperty(value = "#{depentPropCatService}")
    private DepentPropCatServiceImpl depentPropCatService;
    private LazyDataModel<DepentPropCatBO> lazyModel;
    private List<Boolean> togglerColumn = new ArrayList<>();
    private DepentPropCatBO newObj;
    private Boolean isEdit = false;


    @PostConstruct
    public void onStart() {
        Map<String, Object> filters = new HashMap<>();
        LinkedHashMap<String, String> orders = new LinkedHashMap<>();
        orders.put("name", Constant.ORDERS.ASC);
        lazyModel = new LazyDataModelBaseNew<>(depentPropCatService, filters, orders);
        togglerColumn = Arrays.asList(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
    }

    public void preInsert() {
        newObj = new DepentPropCatBO();
        isEdit = false;
    }

    public void preUpdate(DepentPropCatBO node) {
        try {
            newObj = node;
            isEdit = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            MessageUtil.setErrorMessage("Xảy ra lỗi!!!");
        }
    }

    public void onSaveOrUpdate() {
        if (!validateDPC()) {
            return;
        }
        try {
            depentPropCatService.saveOrUpdate(newObj);
            RequestContext.getCurrentInstance().execute("PF('addDepentPropCatDlg').hide();");
            MessageUtil.setInfoMessageFromRes("common.message.success");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            MessageUtil.setErrorMessageFromRes("common.message.fail");
        }
    }

    public void delete(DepentPropCatBO delObj) {
        if (delObj != null) {
            try {
                depentPropCatService.delete(delObj);
                MessageUtil.setInfoMessageFromRes("server.message.delete.success");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                MessageUtil.setErrorMessageFromRes("server.message.delete.error");
            }
        }
    }

    public boolean validateDPC() {
        if (StringUtils.isNullOrEmpty(newObj.getCode()) || "".equals(newObj.getCode())) {
            MessageUtil.setErrorMessage(MessageFormat.format(MessageUtil.getResourceBundleMessage("common.required"),
                    MessageUtil.getResourceBundleMessage("depentPC.code")));
            return false;
        }
        if (StringUtils.isNullOrEmpty(newObj.getName()) || "".equals(newObj.getName())) {
            MessageUtil.setErrorMessage(MessageFormat.format(MessageUtil.getResourceBundleMessage("common.required"),
                    MessageUtil.getResourceBundleMessage("depentPC.name")));
            return false;
        }
        if (StringUtils.isNullOrEmpty(newObj.getValue()) || "".equals(newObj.getValue())) {
            MessageUtil.setErrorMessage(MessageFormat.format(MessageUtil.getResourceBundleMessage("common.required"),
                    MessageUtil.getResourceBundleMessage("depentPC.value")));
            return false;
        }
        return true;
    }


    public LazyDataModel<DepentPropCatBO> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<DepentPropCatBO> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public List<Boolean> getTogglerColumn() {
        return togglerColumn;
    }

    public void setTogglerColumn(List<Boolean> togglerColumn) {
        this.togglerColumn = togglerColumn;
    }

    public void onToggler(ToggleEvent e) {
        togglerColumn.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }

    public DepentPropCatBO getNewObj() {
        return newObj;
    }

    public void setNewObj(DepentPropCatBO newObj) {
        this.newObj = newObj;
    }

    public Boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Boolean isEdit) {
        this.isEdit = isEdit;
    }

    public DepentPropCatServiceImpl getDepentPropCatService() {
        return depentPropCatService;
    }

    public void setDepentPropCatService(DepentPropCatServiceImpl depentPropCatService) {
        this.depentPropCatService = depentPropCatService;
    }
}
