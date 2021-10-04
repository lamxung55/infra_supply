/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.controller;

import com.mine.common.util.Constant;
import com.mine.common.util.StringUtils;
import com.mine.datamodel.ContractEntity;
import com.mine.lazy.LazyDataModelBaseNew;
import com.mine.persistence.ContractServiceImpl;
import com.mine.util.MessageUtil;
import org.primefaces.context.RequestContext;
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
public class ContractController {
    protected static final Logger logger = LoggerFactory.getLogger(ContractController.class);
    @ManagedProperty(value = "#{contractService}")
    private ContractServiceImpl contractService;
    private LazyDataModel<ContractEntity> lazyModel;
    private List<Boolean> togglerColumn = new ArrayList<>();
    private ContractEntity newObj;
    private Boolean isEdit = false;
    private List<ContractEntity> selectedObj;

    @PostConstruct
    public void onStart() {
        Map<String, Object> filters = new HashMap<>();
        LinkedHashMap<String, String> orders = new LinkedHashMap<>();
        orders.put("name", Constant.ORDERS.ASC);
        lazyModel = new LazyDataModelBaseNew<>(contractService, filters, orders);
        togglerColumn = Arrays.asList(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
    }

    public void preInsert() {
        newObj = new ContractEntity();
        isEdit = false;
    }

    public void preUpdate(ContractEntity node) {
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
            contractService.saveOrUpdate(newObj);
            //RequestContext.getCurrentInstance().execute("PF('addDlg').hide();");
            MessageUtil.setInfoMessage("Thao tác thành công");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            MessageUtil.setErrorMessage("Thao tác xảy ra lỗi");
        }
    }

    public void delete(ContractEntity delObj) {
        if (delObj != null) {
            try {
                contractService.delete(delObj);
                MessageUtil.setInfoMessage("Xóa thành công");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                MessageUtil.setErrorMessage("Chưa xóa được dữ liệu này");
            }
        }
    }

    public void onDelete() {
        try {
            contractService.delete(selectedObj);
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


    public LazyDataModel<ContractEntity> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<ContractEntity> lazyModel) {
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

    public ContractEntity getNewObj() {
        return newObj;
    }

    public void setNewObj(ContractEntity newObj) {
        this.newObj = newObj;
    }

    public Boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Boolean isEdit) {
        this.isEdit = isEdit;
    }

    public ContractServiceImpl getContractService() {
        return contractService;
    }

    public void setContractService(ContractServiceImpl contractService) {
        this.contractService = contractService;
    }

    public List<ContractEntity> getSelectedObj() {
        return selectedObj;
    }

    public void setSelectedObj(List<ContractEntity> selectedObj) {
        this.selectedObj = selectedObj;
    }
}
