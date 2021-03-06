/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.controller;

import com.mine.common.util.Constant;
import com.mine.datamodel.*;
import com.mine.lazy.LazyDataModelBaseNew;
import com.mine.persistence.*;
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
        orders.put("deviceName", Constant.ORDERS.ASC);
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
            MessageUtil.setErrorMessage("Thao ta??c xa??y ra l????i");
        }
        return lstdevice;
    }

    private String selectedInfraType;
    private String selectedInfraCode;
    private long quantity;
    private String note;

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
            }else if("BBBG".equalsIgnoreCase(selectedInfraType)) {
                BBBGServiceImpl service = new BBBGServiceImpl();
                List<BbbgEntity> lst = service.findList(filters,orders);
                for(BbbgEntity obj:lst) {
                    result.add(obj.getCode());
                }
            }else if("CONTRACT".equalsIgnoreCase(selectedInfraType)) {
                ContractServiceImpl service = new ContractServiceImpl();
                List<ContractEntity> lst = service.findList(filters,orders);
                for(ContractEntity obj:lst) {
                    result.add(obj.getCode());
                }
            }else if("UNIT".equalsIgnoreCase(selectedInfraType)) {
                UnitServiceImpl service = new UnitServiceImpl();
                List<UnitEntity> lst = service.findList(filters,orders);
                for(UnitEntity obj:lst) {
                    result.add(obj.getCode());
                }
            }else if("POOL".equalsIgnoreCase(selectedInfraType)) {
                PoolServiceImpl service = new PoolServiceImpl();
                List<PoolEntity> lst = service.findList(filters,orders);
                for(PoolEntity obj:lst) {
                    result.add(obj.getCode());
                }
            }else if("HA".equalsIgnoreCase(selectedInfraType)) {
                HaServiceImpl service = new HaServiceImpl();
                List<HaEntity> lst = service.findList(filters,orders);
                for(HaEntity obj:lst) {
                    result.add(obj.getCode());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            MessageUtil.setErrorMessage("Thao ta??c xa??y ra l????i");
        }
        return result;
    }

    public void handleChangeInfraType(ValueChangeEvent event) {
        selectedInfraType = (String) event.getNewValue();
    }


    public void preInsert() {
        newObj = new DeviceInfraEntity();
        isEdit = false;
        selectedInfraType=null;
        selectedInfraCode=null;
        quantity = 0;
        note = null;
        selectedDevice = new DeviceEntity();
    }

    public void preUpdate(DeviceInfraEntity node) {
        try {
            newObj = node;
            isEdit = true;
            selectedDevice = deviceService.findById(newObj.getDeviceId());
            selectedInfraType = newObj.getInfraType();
            selectedInfraCode = newObj.getInfraCode();
            quantity = newObj.getCount();
            note = newObj.getNote();
            lstdevice = new ArrayList<>();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            MessageUtil.setErrorMessage("Xa??y ra l????i!!!");
        }
    }

    public void onSaveOrUpdate() {
        if (!validate()) {
            return;
        }
        try {
            if(!isEdit) {
                newObj = new DeviceInfraEntity();
            }
            newObj.setDeviceId(selectedDevice.getId());
            newObj.setDeviceCode(selectedDevice.getCode());
            newObj.setDeviceName(selectedDevice.getName());
            newObj.setInfraType(selectedInfraType);
            newObj.setInfraCode(selectedInfraCode);
            newObj.setCount(quantity);
            newObj.setNote(note);
            Map<String, Object> filters = new HashMap<>();
            filters.put("code",selectedInfraCode);
            List lst = new ArrayList<>();
            if("PROJECT".equalsIgnoreCase(selectedInfraType)) {
                ProjectServiceImpl service = new ProjectServiceImpl();
                lst = service.findList(filters);
                if(lst.size()>=0 ) {
                    newObj.setInfraId(((ProjectEntity)lst.get(0)).getId());
                    newObj.setInfraName(((ProjectEntity)lst.get(0)).getName());
                }
            } else if ("BBBG".equalsIgnoreCase(selectedInfraType)){
                BBBGServiceImpl service = new BBBGServiceImpl();
                lst = service.findList(filters);
                if(lst.size()>=0 ) {
                    newObj.setInfraId(((BbbgEntity)lst.get(0)).getId());
                    newObj.setInfraName(((ProjectEntity)lst.get(0)).getName());
                }
            } else if ("CONTRACT".equalsIgnoreCase(selectedInfraType)){
                ContractServiceImpl service = new ContractServiceImpl();
                lst = service.findList(filters);
                if(lst.size()>=0 ) {
                    newObj.setInfraId(((ContractEntity)lst.get(0)).getId());
                    newObj.setInfraName(((ContractEntity)lst.get(0)).getName());
                }
            } else if ("UNIT".equalsIgnoreCase(selectedInfraType)){
                UnitServiceImpl service = new UnitServiceImpl();
                lst = service.findList(filters);
                if(lst.size()>=0 ) {
                    newObj.setInfraId(((UnitEntity)lst.get(0)).getId());
                    newObj.setInfraName(((UnitEntity)lst.get(0)).getName());
                }
            } else if ("POOL".equalsIgnoreCase(selectedInfraType)){
                PoolServiceImpl service = new PoolServiceImpl();
                lst = service.findList(filters);
                if(lst.size()>=0 ) {
                    newObj.setInfraId(((PoolEntity)lst.get(0)).getId());
                    newObj.setInfraName(((PoolEntity)lst.get(0)).getName());
                }
            }else if ("HA".equalsIgnoreCase(selectedInfraType)){
                HaServiceImpl service = new HaServiceImpl();
                lst = service.findList(filters);
                if(lst.size()>=0 ) {
                    newObj.setInfraId(((HaEntity)lst.get(0)).getId());
                    newObj.setInfraName(((HaEntity)lst.get(0)).getName());
                }
            }
            deviceInfraService.saveOrUpdate(newObj);
            //RequestContext.getCurrentInstance().execute("PF('addDlg').hide();");
            MessageUtil.setInfoMessage("Thao ta??c tha??nh c??ng");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            MessageUtil.setErrorMessage("Thao ta??c xa??y ra l????i");
        }
    }

    public void delete(DeviceInfraEntity delObj) {
        if (delObj != null) {
            try {
                deviceInfraService.delete(delObj);
                MessageUtil.setInfoMessage("Xo??a tha??nh c??ng");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                MessageUtil.setErrorMessage("Ch??a xo??a ????????c d??? li???u n??y");
            }
        }
    }

    public void onDelete() {
        try {
            deviceInfraService.delete(selectedObj);
            MessageUtil.setInfoMessage("Thao t??c th??nh c??ng");
        } catch (Exception ex) {
            logger.error(ex.toString());
            MessageUtil.setErrorMessage("Ch??a xo??a ????????c d??? li???u n??y");

        }
    }

    public boolean validate() {
//        if (StringUtils.isNullOrEmpty(newObj.getBks())) {
//            MessageUtil.setErrorMessage("Ch??a nh????p bi????n ki????m soa??t");
//            return false;
//        }
//        if (StringUtils.isNullOrEmpty(newObj.getName()) || "".equals(newObj.getName())) {
//            MessageUtil.setErrorMessage("Ch??a nh????p t??n chu?? ph????ng ti????n");
//            return false;
//        }
//        if (newObj.getFactor() == null || "".equals(newObj.getFactor()==0)) {
//            MessageUtil.setErrorMessage("Ch??a nh????p ph????n tr??m cho la??i xe");
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

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
