/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.persistence;

import com.mine.model.HisChargeMobile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 *
 * @author cert2
 */
@Scope("session")
@Service(value = "hisChargeMobileService")
public class HisChargeMobileServiceImpl extends GenericDaoImplNewV2<HisChargeMobile, Long> implements GenericDaoServiceNewV2<HisChargeMobile, Long>, Serializable {

    private static final long serialVersionUID = -4109611148855610L;

}
