/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.controller;

import com.mine.common.util.Constant;
import com.mine.datamodel.DeviceEntity;
import com.mine.datamodel.DeviceInfraEntity;
import com.mine.datamodel.ProjectEntity;
import com.mine.exception.SysException;
import com.mine.lazy.LazyDataModelBaseNew;
import com.mine.persistence.DeviceInfraServiceImpl;
import com.mine.persistence.DeviceServiceImpl;
import com.mine.persistence.ProjectServiceImpl;
import com.mine.util.HibernateUtil;
import com.mine.util.MessageUtil;
import org.hibernate.Session;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.Visibility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import java.util.*;

/**
 * @author anhdt
 */
@ViewScoped
@ManagedBean
public class DeviceInfraController {
    protected static final Logger logger = LoggerFactory.getLogger(DeviceInfraController.class);
    @ManagedProperty(value = "#{deviceInfraService}")
    private DeviceInfraServiceImpl deviceInfraService;
    @ManagedProperty(value = "#{deviceService}")
    private DeviceServiceImpl deviceService;


    private LazyDataModel<DeviceInfraEntity> lazyModel;
    private List<Boolean> togglerColumn = new ArrayList<>();
    private DeviceInfraEntity newObj;
    private Boolean isEdit = false;
    private List<DeviceInfraEntity> selectedObj;

    @PostConstruct
    public void onStart() {
        Map<String, Object> filters = new HashMap<>();
        LinkedHashMap<String, String> orders = new LinkedHashMap<>();
        orders.put("name", Constant.ORDERS.ASC);
        lazyModel = new LazyDataModelBaseNew<>(deviceInfraService, filters, orders);
        togglerColumn = Arrays.asList(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
    }


    private DeviceEntity selectedDevice;
    private List<DeviceEntity> lstdevice;
    public List<DeviceEntity> onSearchDevice(String input) {
        lstdevice = new ArrayList<>();
        Map<String, Object> filters = new HashMap<>();
        filters.put("code",input);
        LinkedHashMap<String, String> orders = new LinkedHashMap<>();
        orders.put("name", Constant.ORDERS.ASC);
        try {
            lstdevice = deviceService.findList(filters,orders);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            MessageUtil.setErrorMessage("Thao tác xảy ra lỗi");
        }
        return lstdevice;
    }

    private String selectedInfraType;
    private String selectedInfraCode;

    public List<String> onSearchInfra(String input) {
        List<String> result = new ArrayList<>();
        Map<String, Object> filters = new HashMap<>();
        filters.put("code",input);
        LinkedHashMap<String, String> orders = new LinkedHashMap<>();
        orders.put("name", Constant.ORDERS.ASC);
        try {
            if("PROJECT".equalsIgnoreCase(selectedInfraType)) {
                ProjectServiceImpl service = new ProjectServiceImpl();
                List<ProjectEntity> lst = service.findList(filters,orders);
                for(ProjectEntity obj:lst) {
                    result.add(obj.getCode());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            MessageUtil.setErrorMessage("Thao tác xảy ra lỗi");
        }
        return result;
    }

    public void handleChangeInfraType(ValueChangeEvent event) {
        selectedInfraType = (String) event.getNewValue();
    }


    public void preInsert() {
        newObj = new DeviceInfraEntity();
        isEdit = false;
    }

    public void preUpdate(DeviceInfraEntity node) {
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
            deviceInfraService.saveOrUpdate(newObj);
            //RequestContext.getCurrentInstance().execute("PF('addDlg').hide();");
            MessageUtil.setInfoMessage("Thao tác thành công");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            MessageUtil.setErrorMessage("Thao tác xảy ra lỗi");
        }
    }

    public void delete(DeviceInfraEntity delObj) {
        if (delObj != null) {
            try {
                deviceInfraService.delete(delObj);
                MessageUtil.setInfoMessage("Xóa thành công");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                MessageUtil.setErrorMessage("Chưa xóa được dữ liệu này");
            }
        }
    }

    public void onDelete() {
        try {
            deviceInfraService.delete(selectedObj);
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


    public LazyDataModel<DeviceInfraEntity> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<DeviceInfraEntity> lazyModel) {
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

    public DeviceInfraEntity getNewObj() {
        return newObj;
    }

    public void setNewObj(DeviceInfraEntity newObj) {
        this.newObj = newObj;
    }

    public Boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Boolean isEdit) {
        this.isEdit = isEdit;
    }

    public DeviceInfraServiceImpl getdeviceInfraService() {
        return deviceInfraService;
    }

    public void setdeviceInfraService(DeviceInfraServiceImpl deviceInfraService) {
        this.deviceInfraService = deviceInfraService;
    }

    public List<DeviceInfraEntity> getSelectedObj() {
        return selectedObj;
    }

    public void setSelectedObj(List<DeviceInfraEntity> selectedObj) {
        this.selectedObj = selectedObj;
    }

    public DeviceServiceImpl getDeviceService() {
        return deviceService;
    }

    public void setDeviceService(DeviceServiceImpl deviceService) {
        this.deviceService = deviceService;
    }

    public DeviceEntity getSelectedDevice() {
        return selectedDevice;
    }

    public void setSelectedDevice(DeviceEntity selectedDevice) {
        this.selectedDevice = selectedDevice;
    }

    public List<DeviceEntity> getLstdevice() {
        return lstdevice;
    }

    public void setLstdevice(List<DeviceEntity> lstdevice) {
        this.lstdevice = lstdevice;
    }

    public String getSelectedInfraType() {
        return selectedInfraType;
    }

    public void setSelectedInfraType(String selectedInfraType) {
        this.selectedInfraType = selectedInfraType;
    }

    public String getSelectedInfraCode() {
        return selectedInfraCode;
    }

    public void setSelectedInfraCode(String selectedInfraCode) {
        this.selectedInfraCode = selectedInfraCode;
    }
}
