package com.mine.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Anonymous
 * @sin Jul 29, 2016
 * @version 1.0 
 */
public class HibernateUtil {
	
	
	private static Map<String,SessionFactory> sessionFactorys = new HashMap<String, SessionFactory>();
	private static SessionFactory buildSessionFactory(String resource) {
		try {
			if(sessionFactorys.get(resource) ==null){
				sessionFactorys.put(resource, new Configuration().configure(resource).buildSessionFactory());
			}
		    return sessionFactorys.get(resource);
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return buildSessionFactory("/hibernate.cfg.xml");
	}
	/**
	 * @param resource: "/hibernate.cfg.xml";
	 * @return
	 * @author huynx6
	 * 
	 */
	public static SessionFactory getSessionFactory(String resource) {
		if(resource==null)
			return getSessionFactory();
		return buildSessionFactory(resource);
	}

	public static void shutdown() {
		getSessionFactory().close();
	}
	public static void main(String[] args) {
		
	}
}
