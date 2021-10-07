/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.controller;

import com.mine.common.util.Constant;
import com.mine.datamodel.HaEntity;
import com.mine.datamodel.HaEntity;
import com.mine.lazy.LazyDataModelBaseNew;
import com.mine.persistence.HaServiceImpl;
import com.mine.util.MessageUtil;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.Visibility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.*;

/**
 * @author anhdt
 */
@ViewScoped
@ManagedBean
public class HaController {
    protected static final Logger logger = LoggerFactory.getLogger(HaController.class);
    @ManagedProperty(value = "#{haService}")
    private HaServiceImpl haService;
    private LazyDataModel<HaEntity> lazyModel;
    private List<Boolean> togglerColumn = new ArrayList<>();
    private HaEntity newObj;
    private Boolean isEdit = false;
    private List<HaEntity> selectedObj;

    @PostConstruct
    public void onStart() {
        Map<String, Object> filters = new HashMap<>();
        LinkedHashMap<String, String> orders = new LinkedHashMap<>();
        orders.put("name", Constant.ORDERS.ASC);
        lazyModel = new LazyDataModelBaseNew<>(haService, filters, orders);
        togglerColumn = Arrays.asList(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
    }

    public void preInsert() {
        newObj = new HaEntity();
        isEdit = false;
    }

    public void preUpdate(HaEntity node) {
        try {
            newObj = node;
            isEdit = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            MessageUtil.setErrorMessage("Xảy ra lỗi!!!");
        }
    }

    public void onSaveOrUpdate() {
        if (!validate()) {
            return;
        }
        try {
            haService.saveOrUpdate(newObj);
            //RequestContext.getCurrentInstance().execute("PF('addDlg').hide();");
            MessageUtil.setInfoMessage("Thao tác thành công");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            MessageUtil.setErrorMessage("Thao tác xảy ra lỗi");
        }
    }

    public void delete(HaEntity delObj) {
        if (delObj != null) {
            try {
                haService.delete(delObj);
                MessageUtil.setInfoMessage("Xóa thành công");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                MessageUtil.setErrorMessage("Chưa xóa được dữ liệu này");
            }
        }
    }

    public void onDelete() {
        try {
            haService.delete(selectedObj);
            MessageUtil.setInfoMessage("Thao tác thành công");
        } catch (Exception ex) {
            logger.error(ex.toString());
            MessageUtil.setErrorMessage("Chưa xóa được dữ liệu này");

        }
    }

    public boolean validate() {
//        if (StringUtils.isNullOrEmpty(newObj.getBks())) {
//            MessageUtil.setErrorMessage("Chưa nhập biển kiểm soát");
//            return false;
//        }
//        if (StringUtils.isNullOrEmpty(newObj.getName()) || "".equals(newObj.getName())) {
//            MessageUtil.setErrorMessage("Chưa nhập tên chủ phương tiện");
//            return false;
//        }
//        if (newObj.getFactor() == null || "".equals(newObj.getFactor()==0)) {
//            MessageUtil.setErrorMessage("Chưa nhập phần trăm cho lái xe");
//            return false;
//        }
        return true;
    }


    public LazyDataModel<HaEntity> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<HaEntity> lazyModel) {
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

    public HaEntity getNewObj() {
        return newObj;
    }

    public void setNewObj(HaEntity newObj) {
        this.newObj = newObj;
    }

    public Boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Boolean isEdit) {
        this.isEdit = isEdit;
    }

    public HaServiceImpl gethaService() {
        return haService;
    }

    public void sethaService(HaServiceImpl haService) {
        this.haService = haService;
    }

    public List<HaEntity> getSelectedObj() {
        return selectedObj;
    }

    public void setSelectedObj(List<HaEntity> selectedObj) {
        this.selectedObj = selectedObj;
    }
}
