/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.authen.service;


import com.mine.authen.bean.OamUser;
import com.mine.authen.bean.OamUserRole;
import com.mine.exception.AppException;
import com.mine.exception.SysException;

import java.util.List;

/**
 *
 * @author havt4
 */
public interface UserManageService {
    public boolean insert(OamUser o) throws AppException, SysException;
    public boolean update(OamUser o) throws AppException, SysException;
    public boolean delete(OamUser o) throws AppException, SysException;
    public List<OamUser> list() throws AppException, SysException;
    public OamUser findById(Long id) throws AppException, SysException;
    public List<OamUser> findByUserName(String userName) throws AppException, SysException;
    public List<OamUserRole> findRoleByUserId(Long userId) throws AppException, SysException;
    public void deleteRoleByUserId(Long userId) throws AppException, SysException;
}
