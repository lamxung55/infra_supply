/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.persistence;

import com.mine.model.MdDependentBO;

import java.io.Serializable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author vinhvh
 */
@Scope("session")
@Service(value = "mdDependentService")
public class MdDependentServiceImpl  extends GenericDaoImplNewV2<MdDependentBO, Long> implements GenericDaoServiceNewV2<MdDependentBO, Long> ,  Serializable{
	private static final long serialVersionUID = -4109611148855610L;

}