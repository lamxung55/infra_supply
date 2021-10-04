/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.authen.service;


import com.mine.authen.bean.OamUserRoleMapping;
import com.mine.exception.AppException;
import com.mine.exception.SysException;

import java.util.List;

/**
 *
 * @author havt4
 */
public interface RoleUserMappingService {
    public void insert(OamUserRoleMapping m) throws AppException, SysException;
    public void update(OamUserRoleMapping m) throws AppException, SysException;
    public void delete(OamUserRoleMapping m) throws AppException, SysException;
    public List<OamUserRoleMapping> list() throws AppException, SysException;
    public OamUserRoleMapping findById(Long id) throws AppException, SysException;
    public void deleteByUserRole(Long userId, Integer roleId) throws AppException,SysException;

}
