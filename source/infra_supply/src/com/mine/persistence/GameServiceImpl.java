/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.persistence;

import com.mine.model.Game;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 *
 * @author cert2
 */
@Scope("session")
@Service(value = "gameService")
public class GameServiceImpl extends GenericDaoImplNewV2<Game, Long> implements GenericDaoServiceNewV2<Game, Long>, Serializable {

    private static final long serialVersionUID = -4109611148855610L;

}
