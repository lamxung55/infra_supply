/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.persistence;

import com.mine.datamodel.UnitEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 *
 * @author anhdt
 */
@Scope("session")
@Service(value = "unitService")
public class UnitServiceImpl extends GenericDaoImplNewV2<UnitEntity, Long> implements GenericDaoServiceNewV2<UnitEntity, Long> ,  Serializable{
	private static final long serialVersionUID = -4109611148855610L;
}
