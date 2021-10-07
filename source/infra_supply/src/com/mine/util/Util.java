/*
 * Created on Jun 7, 2013
 *
 * Copyright (C) 2013 by mine Network Company. All rights reserved
 */
package com.mine.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 *
 * Class chua cac ham tien ich dung chung cua ca project
 *
 * @author
 * @since Jun 7, 2013
 * @version 1.0.0
 *
 */
public class Util {

    public static ExternalContext externalContext;
    public static File TOMCAT_DIR;
    public static File RESOURCES_DIR;
    protected static Logger LOGGER = LoggerFactory.getLogger("MYLOG");

//    static {
//        try {
//            externalContext = FacesContext.getCurrentInstance()
//                    .getExternalContext();
//            TOMCAT_DIR = new File(
//                    ((ServletContext) externalContext.getContext())
//                    .getRealPath("")).getParentFile().getParentFile(); // ...../tomcat			
//            RESOURCES_DIR = new File((new File(
//                    ((ServletContext) externalContext.getContext())
//                    .getRealPath("")).getParentFile())
//                    + "/resources"); // ...../resources            
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage(), e.toString());
//        }
//    }
    static {
        try {
            externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();
//            TOMCAT_DIR = new File(
//                    ((ServletContext) externalContext.getContext())
//                    .getRealPath("")).getParentFile().getParentFile(); // ...../tomcat			
            RESOURCES_DIR = new File(((ServletContext) externalContext.getContext()).getRealPath("/resources"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e.toString());
        }
    }

    /**
     * Lay gia tri ip cua client.
     */
    public static String getClientIp() {
        ExternalContext context = FacesContext.getCurrentInstance()
                .getExternalContext();
        HttpServletRequest req = (HttpServletRequest) context.getRequest();

        return req.getRemoteHost();
    }

    public static String getUploadFolder(String handleFolder) {
        String dir = RESOURCES_DIR + File.separator + Config.ROOT_FOLDER_DATA
                + File.separator + handleFolder;
        new File(dir).mkdirs();
        return dir;
    }

//    public static boolean storeFile(String handleFoder, UploadedFile fileUpload) {
//        File file = new File(getUploadFolder(handleFoder) + File.separator
//                + fileUpload.getFileName());
//        FileOutputStream out = null;
//        try {
//            out = new FileOutputStream(file);
//            out.write(fileUpload.getContents());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                out.close();
//                return true;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }
//    public static boolean storeFile(String handleFoder, UploadedFile fileUpload, String fileName) {
//        File file = new File(getUploadFolder(handleFoder) + File.separator
//                + fileName);
//        FileOutputStream out = null;
//        try {
//            out = new FileOutputStream(file);
//            out.write(fileUpload.getContents());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                out.close();
//                return true;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }
    public static String storeFile(String handleFoder, UploadedFile fileUpload, String fileName) {
        File file = new File(getUploadFolder(handleFoder) + File.separator
                + fileName);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(fileUpload.getContents());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                return file.getPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getFullFilePath(String relatePath) {
        return getUploadFolder(Config.GAME_FILE_FOLDER) + File.separator
                + relatePath;
    }
    
    public static JSONObject createJSON(Object obj) {
        JSONObject jsonObject = new JSONObject();
        if (obj == null) {
            return jsonObject;
        }
        Class<?> cls = obj.getClass();
        Field[] fieldlist = cls.getDeclaredFields();
        for (Field field : fieldlist) {
            try {

                if (Long.class.isAssignableFrom(field.getType()) || Integer.class.isAssignableFrom(field.getType())
                        || String.class.isAssignableFrom(field.getType()) || Date.class.isAssignableFrom(field.getType())) {
                    if (PropertyUtils.getPropertyDescriptor(obj, field.getName()) != null) {
                        jsonObject.put(field.getName(), PropertyUtils.getSimpleProperty(obj, field.getName()));
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    public static void main(String[] args) {
        String x = "Capture.PNG";
        String[] y = x.split("\\.");
        System.out.println("xmskkfmsd");
    }
}
