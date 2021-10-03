/*
 * Copyright YYYY Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mine.common.util;

import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author thienkq1@viettel.com.vn
 * @since 12,Apr,2010
 * @version 1.0
 */
public class ResourceBundleUtils {
    protected static final Logger logger = LoggerFactory.getLogger(ResourceBundleUtils.class);

    /**
     * rb.
     */
    private static volatile ResourceBundle rb = null;

    /**
     * Creates a new instance of ResourceBundleUtils
     */
    private ResourceBundleUtils() {
    }

    /**
     * method get resource
     *
     * @param key String
     * @return String
     */
    public static String getResource(String key) {
        rb = ResourceBundle.getBundle("config");
        return rb.getString(key);
    }

    public static String getServerConfig(String key) {
        rb = ResourceBundle.getBundle("server");
        return rb.getString(key);
    }
//    vietnv14 20150520 add start

    public static String getValueByKey(String key) {
        try {
            rb = ResourceBundle.getBundle("config");
            return rb.getString(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
//    vietnv14 20150520 add end

    public static String getCasConfig(String key) {
        rb = ResourceBundle.getBundle("cas");
        return rb.getString(key);
    }

    public static String getVsaKey(String key) {
        rb = ResourceBundle.getBundle("vsa_service");
        return rb.getString(key);
    }
}
