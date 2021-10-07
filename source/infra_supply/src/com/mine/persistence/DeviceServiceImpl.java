/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.persistence;

import com.mine.datamodel.DeviceEntity;
import com.mine.datamodel.PoolEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 *
 * @author anhdt
 */
@Scope("session")
@Service(value = "deviceService")
public class DeviceServiceImpl extends GenericDaoImplNewV2<DeviceEntity, Long> implements GenericDaoServiceNewV2<DeviceEntity, Long> ,  Serializable{
	private static final long serialVersionUID = -4109611148855610L;
}
