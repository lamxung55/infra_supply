/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.authen.controller;

import com.mine.authen.bean.OamRoleFormBean;
import com.mine.authen.bean.OamUserRole;
import com.mine.authen.service.RoleManageService;
import com.mine.authen.service.RoleManageServiceImpl;
import com.mine.exception.AppException;
import com.mine.exception.SysException;
import com.mine.util.MessageUtil;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.List;


@ManagedBean
@ViewScoped
public class OamRoleController implements Serializable{
    private static Logger logger= Logger.getLogger("RoleController.class");
    private static final long serialVersionUID = 1L;
    
    private RoleManageService roleMangeService = new RoleManageServiceImpl();
    private boolean isRoleEdit=false;
    private String dialogHeader= "Thêm Role mới";
    
    private List<OamUserRole> roleList;
    private OamUserRole selectedRole;

    private OamRoleFormBean oamRoleFormBean;
   
    public OamRoleController(){
        try{
            roleList=roleMangeService.list();
        }catch(AppException a){
            logger.error(a.getMessage(), a);
        }
    }
    
    public void preEdit(OamUserRole role){
       selectedRole=role;
       isRoleEdit=true;
       dialogHeader="Sửa Role";
       convertOamRoleFormBean(role);
    }
    
    public void preAdd(){
        isRoleEdit=false;
        dialogHeader="Thêm Role mới";
        clearOamRoleFormBean();
    }
    
    public void onAdd(ActionEvent event){
        try{
            List<OamUserRole> roles=roleMangeService.findByRoleName(oamRoleFormBean.getRoleName());
            if(roles.size()>0){
                MessageUtil.setErrorMessage("Role đã tồn tại!");
                RequestContext.getCurrentInstance().addCallbackParam("", true);
                return;
            }
      
            OamUserRole newRole=new OamUserRole();
            //newRole.setRoleId(Integer.parseInt(UserManageServiceImpl.getNewId("OAM_USER_ROLE_SEQ").toString()));
            newRole.setRoleName(oamRoleFormBean.getRoleName());
            newRole.setPageUrl(oamRoleFormBean.getPageUrl());
            newRole.setActionId(oamRoleFormBean.getActionId());
            newRole.setDescription(oamRoleFormBean.getDescription());
            roleMangeService.insert(newRole);
            roleList=roleMangeService.list();
            MessageUtil.setInfoMessage (" Thêm Role mới thành công :"+ newRole.getRoleName());
        }catch(Exception e){
            logger.error(e);
            MessageUtil.setErrorMessage(" Thêm Role mới không thành công! ");
        }
    }
    
    public void onEdit(ActionEvent event){
        try{
            List<OamUserRole> roles=roleMangeService.findByRoleName(oamRoleFormBean.getRoleName());
            if(roles.size()>0 && roles.get(0).getRoleId().intValue()!=selectedRole.getRoleId().intValue()){
                MessageUtil.setErrorMessage("Role đã tồn tại!");
                RequestContext.getCurrentInstance().addCallbackParam("", true);
                return;
            }
      
            OamUserRole newRole=roleMangeService.findById(selectedRole.getRoleId());
            newRole.setRoleName(oamRoleFormBean.getRoleName());
            newRole.setPageUrl(oamRoleFormBean.getPageUrl());
            newRole.setActionId(oamRoleFormBean.getActionId());
            newRole.setDescription(oamRoleFormBean.getDescription());
            roleMangeService.update(newRole);
            roleList=roleMangeService.list();
            MessageUtil.setInfoMessage ("Cập nhật Role thành công! ");
        }catch(Exception e){
            logger.error(e);
            MessageUtil.setErrorMessage(" Cập nhật không thành công! ");
        }
    
    }
    
    public void onDelete(){
            try {
                Integer roleId=selectedRole.getRoleId();
                if(roleMangeService.delete(selectedRole)){
                    roleMangeService.deleteRoleByRoleId(roleId);
                }      
                roleList=roleMangeService.list();
                MessageUtil.setInfoMessage("Xóa thành công Role : "+selectedRole.getRoleName());
            } catch (SysException s) {
                logger.error(s.getMessage(), s);
            } catch (AppException a) {
                logger.error(a.getMessage(), a);
            } catch (Throwable t) {
                logger.error(t.getMessage(), t);
            }
    }
    
    public void convertOamRoleFormBean(OamUserRole role){
        oamRoleFormBean = new OamRoleFormBean();
        oamRoleFormBean.setRoleId(role.getRoleId());
        oamRoleFormBean.setRoleName(role.getRoleName());
        oamRoleFormBean.setPageUrl(role.getPageUrl());
        oamRoleFormBean.setActionId(role.getActionId());
        oamRoleFormBean.setDescription(role.getDescription());
    }
    
    public void clearOamRoleFormBean(){
        oamRoleFormBean = new OamRoleFormBean();
    }

    public RoleManageService getRoleMangeService() {
        return roleMangeService;
    }

    public void setRoleMangeService(RoleManageService roleMangeService) {
        this.roleMangeService = roleMangeService;
    }

    public boolean isIsRoleEdit() {
        return isRoleEdit;
    }

    public void setIsRoleEdit(boolean isRoleEdit) {
        this.isRoleEdit = isRoleEdit;
    }

    public List<OamUserRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<OamUserRole> roleList) {
        this.roleList = roleList;
    }

    public OamUserRole getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(OamUserRole selectedRole) {
        this.selectedRole = selectedRole;
    }

    public OamRoleFormBean getOamRoleFormBean() {
        return oamRoleFormBean;
    }

    public void setOamRoleFormBean(OamRoleFormBean oamRoleFormBean) {
        this.oamRoleFormBean = oamRoleFormBean;
    }

    public String getDialogHeader() {
        return dialogHeader;
    }

    public void setDialogHeader(String dialogHeader) {
        this.dialogHeader = dialogHeader;
    }
   
}
