package com.mine.util;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.mine.resource.AppMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class MessageUtil {

    protected static final Logger logger = LoggerFactory.getLogger(SessionUtil.class);
//	private static ResourceBundle bundle;
    protected static Locale local;

    public static Locale getLocal() {
        return local;
    }

    public static void setLocal(Locale local) {
        MessageUtil.local = local;
    }

    public static ResourceBundle getResourceBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
		//if (bundle == null)
//		{

        ResourceBundle bundle = context.getApplication()
                .getResourceBundle(context, "msg");
//		}
        return bundle;
    }

    public static void setResourceBundle() {
//		FacesContext context = FacesContext.getCurrentInstance();
//		bundle = context.getApplication()
//					.getResourceBundle(context, "msg");
    }

    public static void setErrorMessage(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error", message);

        FacesContext.getCurrentInstance().addMessage("mainMessage", msg);
    }

    public static void setInfoMessage(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
                message);

        FacesContext.getCurrentInstance().addMessage("mainMessage", msg);
    }

    public static void setWarnMessage(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn",
                message);

        FacesContext.getCurrentInstance().addMessage("mainMessage", msg);
    }

    public static void setFatalMessage(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                "Fatal", message);

        FacesContext.getCurrentInstance().addMessage("mainMessage", msg);
    }

    public static ResourceBundle getResourceBundle(String name) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
//		if (bundle == null) 
        {
            if (context == null) {
                bundle = ResourceBundle.getBundle("com.viettel.resource.messages", local == null ? new Locale("vi", "VN") : local, new AppMessages.UTF8Control());
            } else {
                bundle = context.getApplication().getResourceBundle(context, name);
            }
        }
        return bundle;
    }

    public static String getResourceBundleMessage(String key) {
        if (key == null) {
            return key;
        }
        if ("".equals(key)) {
            return "";
        }
        try {

            ResourceBundle bundle = getResourceBundle();
            return bundle.getString(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return key;
    }

    public static void setInfoMessageFromRes(String key) {
        setInfoMessage(getResourceBundleMessage(key));
    }

    public static void setErrorMessageFromRes(String key) {
        setErrorMessage(getResourceBundleMessage(key));
    }

    public static void setWarnMessageFromRes(String key) {
        setWarnMessage(getResourceBundleMessage(key));
    }

    public static String getResourceBundleConfig(String key) {
        if (key == null) {
            return key;
        }
        if ("".equals(key)) {
            return "";
        }
        try {
            ResourceBundle configBundle = ResourceBundle.getBundle("config");
            return configBundle.getString(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return key;
    }

    public static void setInfoMessageFromRes(String string, Object... params) {
        String msg = getResourceBundleMessage(string);
        try {
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    msg = msg.replace("{" + i + "}", params[i].toString());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        setInfoMessage(msg);
    }

    public static void setErrorMessageFromRes(String string, Object... params) {
        String msg = getResourceBundleMessage(string);
        try {
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    msg = msg.replace("{" + i + "}", params[i].toString());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        setErrorMessage(msg);
    }

    public static void setWarnMessageFromRes(String string, Object... params) {
        String msg = getResourceBundleMessage(string);
        try {
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    msg = msg.replace("{" + i + "}", params[i].toString());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        setWarnMessage(msg);
    }
}
