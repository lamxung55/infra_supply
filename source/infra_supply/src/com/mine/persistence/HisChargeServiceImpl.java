/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.persistence;

import com.mine.model.HisCharge;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 *
 * @author cert2
 */
@Scope("session")
@Service(value = "hisChargeService")
public class HisChargeServiceImpl extends GenericDaoImplNewV2<HisCharge, Long> implements GenericDaoServiceNewV2<HisCharge, Long>, Serializable {

    private static final long serialVersionUID = -4109611148855610L;

}
