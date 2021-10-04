/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.authen.controller;

import com.mine.authen.bean.OamUser;
import com.mine.authen.bean.OamUserFormBean;
import com.mine.authen.bean.OamUserRole;
import com.mine.authen.bean.OamUserRoleMapping;
import com.mine.authen.service.RoleManageServiceImpl;
import com.mine.authen.service.RoleUserMappingServiceImpl;
import com.mine.authen.service.UserManageService;
import com.mine.authen.service.UserManageServiceImpl;
import com.mine.authen.util.PasswordEncoder;
import com.mine.exception.AppException;
import com.mine.exception.SysException;
import com.mine.util.MessageUtil;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author havt4
 */
@ManagedBean
@ViewScoped
public class OamUserController implements Serializable {
    Logger logger = Logger.getLogger(OamUserController.class.getSimpleName());
    private UserManageService loginUsersService = new UserManageServiceImpl();
    private RoleManageServiceImpl roleManageService = new RoleManageServiceImpl();
    private boolean isEdit = false;

    private List<OamUser> userList;
    private OamUser selectedUser;
    private OamUserFormBean oamUserFormBean;
    private String dialogHeader = "Thêm User mới";

    private List<OamUserRole> selectedRoles;
    private List<OamUserRole> unSelectedRoles;
    private DualListModel<OamUserRole> dualRoleList;

    public static List<String> blackPassword = new ArrayList<>();

    static {
        FileReader fr = null;
        try {
            ResourceBundle rs = ResourceBundle.getBundle("BlackListPass");
            for(String key:rs.keySet()){
                blackPassword.add(key);
            };
//            InputStream inputStream = this.getClass().getClassLoader()
//                    .getResourceAsStream("myApp.properties");
//            fr = new FileReader(new File (ec.getRealPath("/BlackListPass.properties")));
//            BufferedReader br = new BufferedReader(fr);
//            String line;
//            while ((line = br.readLine()) != null) {
//                blackPassword.add(line);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public OamUserController() {
        try {
            userList = loginUsersService.list();
            unSelectedRoles = new ArrayList<>();
            selectedRoles = new ArrayList<>();
            dualRoleList = new DualListModel<>(unSelectedRoles, selectedRoles);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    public void prepareEdit(OamUser user) throws AppException {
        selectedUser = user;
        isEdit = true;
        dialogHeader = "Sửa User";
        unSelectedRoles = roleManageService.list();
        selectedRoles = loginUsersService.findRoleByUserId(user.getUserId());
        for (OamUserRole role : selectedRoles) {
            unSelectedRoles.remove(role);
        }

        dualRoleList = new DualListModel<>(unSelectedRoles, selectedRoles);
        convertOamUserFormBean(user);
    }

    public void prepareAdd() throws AppException {
        isEdit = false;
        dialogHeader = "Thêm mới User";
        unSelectedRoles = roleManageService.list();
        selectedRoles = new ArrayList<>();
        dualRoleList = new DualListModel<>(unSelectedRoles, selectedRoles);
        clearOamUserFormBean();
    }

    public void onDelete() {
        try {
            Long userId = selectedUser.getUserId();
            if (loginUsersService.delete(selectedUser)) {
                loginUsersService.deleteRoleByUserId(userId);
            }
            userList = loginUsersService.list();
            MessageUtil.setInfoMessage("Xóa thành công Username : " + selectedUser.getUsername());
        } catch (SysException s) {
            logger.error(s.getMessage(), s);
        } catch (AppException a) {
            logger.error(a.getMessage(), a);
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        }
    }

    public void onEdit(ActionEvent event) throws AppException {
        try {
            MessageUtil.setErrorMessage("Chức năng chưa hỗ trợ! Xin cảm ơn!");
            RequestContext.getCurrentInstance().addCallbackParam("", true);
            return;
//            List<OamUser> users = loginUsersService.findByUserName(oamUserFormBean.getUsername());
//            if (users.size() > 0 && users.get(0).getUserId().longValue() != selectedUser.getUserId().longValue()) {
//                MessageUtil.setErrorMessage("User đã tồn tại!");
//                RequestContext.getCurrentInstance().addCallbackParam("", true);
//                return;
//            }
//
//            if(blackPassword.contains(oamUserFormBean.getPassword())){
//                MessageUtil.setErrorMessage("Password quá đơn giản, hãy chọn lại!");
//                RequestContext.getCurrentInstance().addCallbackParam("", true);
//                return;
//            }
//
//            OamUser user = loginUsersService.findById(selectedUser.getUserId());
//            user.setUserId(oamUserFormBean.getUserId());
//            user.setUsername(oamUserFormBean.getUsername());
//            user.setAccountStatus(oamUserFormBean.getAccountStatus());
//            user.setCreateDate(oamUserFormBean.getCreateDate());
//            user.setLastModifiedDate(oamUserFormBean.getLastModifiedDate());
//            user.setNumLoginFailAttempt(oamUserFormBean.getNumLoginFailAttempt());
//            user.setRandomString(oamUserFormBean.getRandomString());
//            user.setMobileNumber(oamUserFormBean.getMobileNumber());
//            user.setEmail(oamUserFormBean.getEmail());
//
//            if (oamUserFormBean.getPassword() != null && oamUserFormBean.getPassword().length() > 0) {
//                String passEncrypt = PasswordEncoder.get_SHA_512_SecurePassword(oamUserFormBean.getPassword(), oamUserFormBean.getUsername());
//                user.setPassword(passEncrypt);
//            }
//
//            if (loginUsersService.update(user)) {
//                doAddOrEditUserRoleMapping(user.getUserId());
//            }
//            userList = loginUsersService.list();
//            MessageUtil.setInfoMessage(" Cập nhật thành công! ");
        } catch (Exception e) {
            logger.error(e);
            MessageUtil.setErrorMessage(" Cập nhật không thành công! ");

        }
    }

    public void onAdd(ActionEvent event) throws AppException {
        try {
            List<OamUser> users = loginUsersService.findByUserName(oamUserFormBean.getUsername());
            if (users.size() > 0) {
                MessageUtil.setInfoMessage("User đã tồn tại!");
                RequestContext.getCurrentInstance().addCallbackParam("", true);
                return;
            }

            if(blackPassword.contains(oamUserFormBean.getPassword())){
                MessageUtil.setErrorMessage("Password quá đơn giản, hãy chọn lại!");
                RequestContext.getCurrentInstance().addCallbackParam("", true);
                return;
            }

            OamUser newUser = new OamUser();
            //newUser.setUserId(Long.parseLong(UserManageServiceImpl.getNewId("OAM_USER_SEQ").toString()));
            newUser.setUsername(oamUserFormBean.getUsername());
            newUser.setPassword(PasswordEncoder.decrypt(oamUserFormBean.getPassword()));
            newUser.setAccountStatus(oamUserFormBean.getAccountStatus());
            newUser.setCreateDate(oamUserFormBean.getCreateDate());
            newUser.setLastModifiedDate(oamUserFormBean.getLastModifiedDate());
            newUser.setNumLoginFailAttempt(oamUserFormBean.getNumLoginFailAttempt());
            newUser.setRandomString(oamUserFormBean.getRandomString());
            newUser.setMobileNumber(oamUserFormBean.getMobileNumber());
            newUser.setEmail(oamUserFormBean.getEmail());
            if (loginUsersService.insert(newUser)) {
                doAddOrEditUserRoleMapping(newUser.getUserId());
            }
            userList = loginUsersService.list();
            MessageUtil.setInfoMessage(" Thêm mới User thành công! ");
        } catch (Exception e) {
            logger.error(e);
            MessageUtil.setErrorMessage(" Thêm mới User không thành công! ");
        }
    }

    public void onRoleUpdate() throws AppException {
        unSelectedRoles = roleManageService.list();
        dualRoleList = new DualListModel<>(unSelectedRoles, selectedRoles);
    }

    public void doAddOrEditUserRoleMapping(long userId) throws AppException {
        RoleUserMappingServiceImpl mapping = new RoleUserMappingServiceImpl();
        //Long currentId = Long.parseLong(UserManageServiceImpl.getNewId("OAM_USER_ROLE_MAPPING_SEQ").toString());
        if (isEdit) {
            for (OamUserRole role : dualRoleList.getTarget()) {
                if (selectedRoles.contains(role)) {
                    selectedRoles.remove(role);
                } else {
                    OamUserRoleMapping item = new OamUserRoleMapping();
                    item.setUserId(userId);
                    item.setRoleId(role.getRoleId().intValue());
                    item.setValidFrom(new Date());
                    mapping.insert(item);
                    //currentId++;
                }
            }
            for (OamUserRole role : selectedRoles) {
                mapping.deleteByUserRole(userId, role.getRoleId());
            }
        } else {

            for (OamUserRole role : dualRoleList.getTarget()) {
                OamUserRoleMapping item = new OamUserRoleMapping();
                item.setUserId(userId);
                item.setRoleId(role.getRoleId().intValue());
                item.setValidFrom(new Date());
                mapping.insert(item);
//                currentId++;
            }
        }
    }

    private void convertOamUserFormBean(OamUser user) {
        oamUserFormBean = new OamUserFormBean();
        oamUserFormBean.setUserId(user.getUserId());
        oamUserFormBean.setUsername(user.getUsername());
        oamUserFormBean.setPassword(user.getPassword());
        oamUserFormBean.setAccountStatus(user.getAccountStatus());
        oamUserFormBean.setCreateDate(user.getCreateDate());
        oamUserFormBean.setLastModifiedDate(new Date());
        oamUserFormBean.setNumLoginFailAttempt(user.getNumLoginFailAttempt());
        oamUserFormBean.setRandomString(user.getRandomString());
        oamUserFormBean.setMobileNumber(user.getMobileNumber());
        oamUserFormBean.setEmail(user.getEmail());
    }

    private void clearOamUserFormBean() throws AppException {
        oamUserFormBean = new OamUserFormBean();
//        oamUserFormBean.setUserId(null);
//        oamUserFormBean.setUsername(null);
//        oamUserFormBean.setPassword(null);
//        oamUserFormBean.setAccountStatus(null);
//        oamUserFormBean.setCreateDate(new Date());
//        oamUserFormBean.setLastModifiedDate(new Date());
//        oamUserFormBean.setNumLoginFailAttempt(null);
//        oamUserFormBean.setRandomString(null);
//        oamUserFormBean.setMobileNumber(null);
//        oamUserFormBean.setEmail(null);
    }

    public void onRowSelect(SelectEvent event) {
        if (selectedUser == null) {
            selectedUser = ((OamUser) event.getObject());
        }
        convertOamUserFormBean(selectedUser);
    }

    public List<OamUser> getUserList() {
        return userList;
    }

    public void setUserList(List<OamUser> userList) {
        this.userList = userList;
    }

    public OamUser getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(OamUser selectedUser) {
        this.selectedUser = selectedUser;
    }

    public boolean isIsEdit() {
        return isEdit;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public List<OamUserRole> getSelectedRoles() {
        return selectedRoles;
    }

    public void setSelectedRoles(List<OamUserRole> selectedRoles) {
        this.selectedRoles = selectedRoles;
    }

    public List<OamUserRole> getUnSelectedRoles() {
        return unSelectedRoles;
    }

    public void setUnSelectedRoles(List<OamUserRole> unSelectedRoles) {
        this.unSelectedRoles = unSelectedRoles;
    }

    public DualListModel<OamUserRole> getDualRoleList() {
        return dualRoleList;
    }

    public void setDualRoleList(DualListModel<OamUserRole> dualRoleList) {
        this.dualRoleList = dualRoleList;
    }

    public OamUserFormBean getOamUserFormBean() {
        return oamUserFormBean;
    }

    public void setOamUserFormBean(OamUserFormBean oamUserFormBean) {
        this.oamUserFormBean = oamUserFormBean;
    }

    public String getDialogHeader() {
        return dialogHeader;
    }

    public void setDialogHeader(String dialogHeader) {
        this.dialogHeader = dialogHeader;
    }

}
