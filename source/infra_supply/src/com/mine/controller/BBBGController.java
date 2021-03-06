/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.controller;

import com.mine.common.util.Constant;
import com.mine.datamodel.BbbgEntity;
import com.mine.datamodel.BbbgEntity;
import com.mine.lazy.LazyDataModelBaseNew;
import com.mine.persistence.BBBGServiceImpl;
import com.mine.util.AccentRemover;
import com.mine.util.Config;
import com.mine.util.MessageUtil;
import com.mine.util.Util;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.Visibility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author anhdt
 */
@ViewScoped
@ManagedBean
public class BBBGController {
    protected static final Logger logger = LoggerFactory.getLogger(BBBGController.class);
    @ManagedProperty(value = "#{bbbgService}")
    private BBBGServiceImpl bbbgService;
    private LazyDataModel<BbbgEntity> lazyModel;
    private List<Boolean> togglerColumn = new ArrayList<>();
    private BbbgEntity newObj;
    private Boolean isEdit = false;
    private List<BbbgEntity> selectedObj;

    private UploadedFile uploadedFile;

    @PostConstruct
    public void onStart() {
        Map<String, Object> filters = new HashMap<>();
        LinkedHashMap<String, String> orders = new LinkedHashMap<>();
        orders.put("name", Constant.ORDERS.ASC);
        lazyModel = new LazyDataModelBaseNew<>(bbbgService, filters, orders);
        togglerColumn = Arrays.asList(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
    }

    public void preInsert() {
        newObj = new BbbgEntity();
        isEdit = false;
    }

    public void preUpdate(BbbgEntity node) {
        try {
            newObj = node;
            isEdit = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            MessageUtil.setErrorMessage("Xa??y ra l????i!!!");
        }
    }

    private BbbgEntity detailObj;
    public void preDetail(Long id) {
        try {
            detailObj = bbbgService.findById(id);
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
            bbbgService.saveOrUpdate(newObj);
            //RequestContext.getCurrentInstance().execute("PF('addDlg').hide();");
            MessageUtil.setInfoMessage("Thao ta??c tha??nh c??ng");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            MessageUtil.setErrorMessage("Thao ta??c xa??y ra l????i");
        }
    }

    public void delete(BbbgEntity delObj) {
        if (delObj != null) {
            try {
                bbbgService.delete(delObj);
                MessageUtil.setInfoMessage("Xo??a tha??nh c??ng");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                MessageUtil.setErrorMessage("Ch??a xo??a ????????c d??? li???u n??y");
            }
        }
    }

    public void onDelete() {
        try {
            bbbgService.delete(selectedObj);
            MessageUtil.setInfoMessage("Thao t??c th??nh c??ng");
        } catch (Exception ex) {
            logger.error(ex.toString());
            MessageUtil.setErrorMessage("Ch??a xo??a ????????c d??? li???u n??y");

        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        logger.info("Uploading...");
        uploadedFile = event.getFile();
        if (uploadedFile != null) {
            String savedFilename = (new Date()).getTime() + "_" + AccentRemover.removeAccent(uploadedFile.getFileName()).replaceAll("  ", " ").replace(" ", "_");
            String savedFilePath = Util.storeFile(Config.GAME_FILE_FOLDER, uploadedFile, savedFilename);
            if (savedFilePath == null) {
                MessageUtil
                        .setErrorMessageFromRes("Kh??ng l??u ???????c file ch???y c???a game");
                return;
            }
            newObj.setAttachFile(savedFilePath);
        }
        logger.info("Uploaded");
    }

    public void downloadFile(String filePath) throws IOException {
        logger.info("downloading the log file");
        File file = new File(filePath);
        //FileUtils.writeStringToFile(file, selectedLog.getMessageContent(), Charset.defaultCharset());
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        response.setContentLength((int) file.length());
        FileInputStream input = null;
        try {
            int i = 0;
            input = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            while ((i = input.read(buffer)) != -1) {
                response.getOutputStream().write(buffer);
                response.getOutputStream().flush();
            }
            facesContext.responseComplete();
            facesContext.renderResponse();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
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


    public LazyDataModel<BbbgEntity> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<BbbgEntity> lazyModel) {
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

    public BbbgEntity getNewObj() {
        return newObj;
    }

    public void setNewObj(BbbgEntity newObj) {
        this.newObj = newObj;
    }

    public Boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Boolean isEdit) {
        this.isEdit = isEdit;
    }

    public BBBGServiceImpl getbbbgService() {
        return bbbgService;
    }

    public void setbbbgService(BBBGServiceImpl bbbgService) {
        this.bbbgService = bbbgService;
    }

    public List<BbbgEntity> getSelectedObj() {
        return selectedObj;
    }

    public void setSelectedObj(List<BbbgEntity> selectedObj) {
        this.selectedObj = selectedObj;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public BbbgEntity getDetailObj() {
        return detailObj;
    }

    public void setDetailObj(BbbgEntity detailObj) {
        this.detailObj = detailObj;
    }
}
