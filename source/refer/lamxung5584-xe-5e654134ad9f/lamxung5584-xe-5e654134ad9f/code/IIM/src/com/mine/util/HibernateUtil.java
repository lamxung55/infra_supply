package com.mine.util;

import com.viettel.security.PassTranformer;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.metadata.ClassMetadata;

/**
 * @author Nguyễn Xuân Huy <huynx6@viettel.com.vn>
 * @version 1.0
 * @sin Jul 29, 2016
 */
public class HibernateUtil {

    private static Map<String, SessionFactory> sessionFactorys = new HashMap<String, SessionFactory>();
    private static final Logger logger = Logger.getLogger(HibernateUtil.class.getSimpleName());

    private static SessionFactory buildSessionFactory(String resource) {
        try {

            if (sessionFactorys.get(resource) == null) {

                Configuration cfg = new Configuration().configure(resource);
//                try {
//                    String vietelSecKey = ResourceBundle.getBundle("config").getString("viettel_secure_key");
//                    PassTranformer.setInputKey(vietelSecKey);
//                    cfg.setProperty("hibernate.connection.username",
//                            PassTranformer.decrypt(cfg.getProperty("hibernate.connection.username")));
//                    cfg.setProperty("hibernate.connection.password",
//                            PassTranformer.decrypt(cfg.getProperty("hibernate.connection.password")));
//                    logger.info("Finish decode database information");
//                } catch (Exception e) {
//                    logger.error(e.getMessage(), e);
//                }

                sessionFactorys.put(resource, cfg.buildSessionFactory());
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
     */
    public static SessionFactory getSessionFactory(String resource) {
        if (resource == null) {
            return getSessionFactory();
        }
        return buildSessionFactory(resource);
    }

    public static Session openSession() {
        return getSessionFactory().openSession();
    }

    public static Session getCurrentSession() {
        return getSessionFactory().getCurrentSession();
    }

    public static ClassMetadata getClassMetadata(Class _class) {
        return getSessionFactory().getClassMetadata(_class);
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

    public static Session openSession(String hibernateConfig) {
        return getSessionFactory(hibernateConfig).openSession();
    }

}
