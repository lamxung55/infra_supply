/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.controller;

import com.mine.common.util.Constant;
import com.mine.common.util.StringUtils;
import com.mine.datamodel.ProjectEntity;
import com.mine.lazy.LazyDataModelBaseNew;
import com.mine.persistence.ProjectServiceImpl;
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
public class ProjectController {
    protected static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    @ManagedProperty(value = "#{projectService}")
    private ProjectServiceImpl projectService;
    private LazyDataModel<ProjectEntity> lazyModel;
    private List<Boolean> togglerColumn = new ArrayList<>();
    private ProjectEntity newObj;
    private Boolean isEdit = false;
    private List<ProjectEntity> lstTest = new ArrayList<>();

    @PostConstruct
    public void onStart() {
        Map<String, Object> filters = new HashMap<>();
        LinkedHashMap<String, String> orders = new LinkedHashMap<>();
        orders.put("name", Constant.ORDERS.ASC);
        lazyModel = new LazyDataModelBaseNew<>(projectService, filters, orders);
        togglerColumn = Arrays.asList(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
    }

    public void preInsert() {
        newObj = new ProjectEntity();
        isEdit = false;
    }

    public void preUpdate(ProjectEntity node) {
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
            projectService.saveOrUpdate(newObj);
            RequestContext.getCurrentInstance().execute("PF('addDepentDlg').hide();");
            MessageUtil.setInfoMessage("Thao tác thành công");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            MessageUtil.setErrorMessage("Thao tác xảy ra lỗi");
        }
    }

    public void delete(ProjectEntity delObj) {
        if (delObj != null) {
            try {
                projectService.delete(delObj);
                MessageUtil.setInfoMessage("Xóa thành công");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                MessageUtil.setErrorMessage("Chưa xóa được xe này. Xe này đã có dữ liệu về chuyến hàng. Hãy xóa hàng trước.");
            }
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


    public LazyDataModel<ProjectEntity> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<ProjectEntity> lazyModel) {
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

    public ProjectEntity getNewObj() {
        return newObj;
    }

    public void setNewObj(ProjectEntity newObj) {
        this.newObj = newObj;
    }

    public Boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Boolean isEdit) {
        this.isEdit = isEdit;
    }

    public ProjectServiceImpl getProjectService() {
        return projectService;
    }

    public void setProjectService(ProjectServiceImpl projectService) {
        this.projectService = projectService;
    }


    public List<ProjectEntity> getLstTest() {
        return lstTest;
    }

    public void setLstTest(List<ProjectEntity> lstTest) {
        this.lstTest = lstTest;
    }
}
