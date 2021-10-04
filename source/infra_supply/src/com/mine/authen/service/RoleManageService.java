/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.authen.service;

import com.mine.authen.bean.OamUserRole;
import com.mine.exception.AppException;
import com.mine.exception.SysException;

import java.util.List;

/**
 *
 * @author havt4
 */
public interface RoleManageService {
    public void insert(OamUserRole r) throws AppException, SysException;
    public void update(OamUserRole r) throws AppException, SysException;
    public boolean delete(OamUserRole r) throws AppException, SysException;
    public List<OamUserRole> list() throws AppException, SysException;
    public OamUserRole findById(Integer id) throws AppException, SysException;
    public List<OamUserRole> findByRoleName(String rowname) throws AppException, SysException;
    public void deleteRoleByRoleId(Integer roleId) throws AppException, SysException;
}
