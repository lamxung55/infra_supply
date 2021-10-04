/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.persistence;

import com.mine.datamodel.ProjectEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 *
 * @author anhdt
 */
@Scope("session")
@Service(value = "projectService")
public class ProjectServiceImpl extends GenericDaoImplNewV2<ProjectEntity, Long> implements GenericDaoServiceNewV2<ProjectEntity, Long> ,  Serializable{
	private static final long serialVersionUID = -4109611148855610L;

}
