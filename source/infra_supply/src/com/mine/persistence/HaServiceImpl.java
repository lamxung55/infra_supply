/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.persistence;

import com.mine.datamodel.ContractEntity;
import com.mine.datamodel.HaEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 *
 * @author anhdt
 */
@Scope("session")
@Service(value = "haService")
public class HaServiceImpl extends GenericDaoImplNewV2<HaEntity, Long> implements GenericDaoServiceNewV2<HaEntity, Long> ,  Serializable{
	private static final long serialVersionUID = -4109611148855610L;
}
