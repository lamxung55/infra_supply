/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;



/**
 *
 * @author qlmvt_longdt1
 */

public class SessionManager implements HttpSessionListener {
    protected static volatile int activeSessions = 0;
 
    public void sessionCreated(HttpSessionEvent event) {
        synchronized (this) {
            activeSessions++;
        }
 
        System.out.println("Session Created: " + event.getSession().getId());
        System.out.println("Total Sessions: " + activeSessions);
    }
 
    public void sessionDestroyed(HttpSessionEvent event) {
        synchronized (this) {
            activeSessions--;
        }
        System.out.println("Session Destroyed: " + event.getSession().getId());
        System.out.println("Total Sessions: " + activeSessions);
    }
    
    public static int getActiveSessions() {
        return activeSessions;
    }
}