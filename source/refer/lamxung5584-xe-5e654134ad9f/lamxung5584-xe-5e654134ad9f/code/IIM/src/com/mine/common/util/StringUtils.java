/*
 * Copyright YYYY Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mine.common.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author thienkq1@viettel.com.vn
 * @since 12,Apr,2010
 * @version 1.0
 */
public final class StringUtils {
    protected static final Logger logger = LoggerFactory.getLogger(StringUtils.class);

    /**
     * alphabeUpCaseNumber.
     */
    private static String alphabeUpCaseNumber = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    /**
     * INVOICE_MAX_LENGTH.
     */
    private static final int INVOICE_MAX_LENGTH = 7;
    /**
     * ZERO.
     */
    private static final String ZERO = "0";

    /**
     * Creates a new instance of StringUtils
     */
    private StringUtils() {
    }

    /**
     * method compare two string
     *
     * @param str1 String
     * @param str2 String
     * @return boolean
     */
    public static boolean compareString(String str1, String str2) {
        if (str1 == null) {
            str1 = "";
        }
        if (str2 == null) {
            str2 = "";
        }

        if (str1.equals(str2)) {
            return true;
        }
        return false;
    }

    /**
     * method convert long to string
     *
     * @param lng Long
     * @return String
     * @throws abc Exception
     */
    public static String convertFromLongToString(Long lng) throws Exception {
        try {
            return Long.toString(lng);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    /*
     * @todo: convert from Long array to String array
     */
    public static String[] convertFromLongToString(Long[] arrLong) throws Exception {
        String[] arrResult = new String[arrLong.length];
        try {
            for (int i = 0; i < arrLong.length; i++) {
                arrResult[i] = convertFromLongToString(arrLong[i]);
            }
            return arrResult;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    /*
     * @todo: convert from String array to Long array
     */
    public static long[] convertFromStringToLong(String[] arrStr) throws Exception {
        long[] arrResult = new long[arrStr.length];
        try {
            for (int i = 0; i < arrStr.length; i++) {
                arrResult[i] = Long.parseLong(arrStr[i]);
            }
            return arrResult;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    /*
     * @todo: convert from String value to Long value
     */
    public static long convertFromStringToLong(String value) throws Exception {
        try {
            return Long.parseLong(value);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }


    /*
     * Check String that containt only AlphabeUpCase and Number Return True if
     * String was valid, false if String was not valid
     */
    public static boolean checkAlphabeUpCaseNumber(String value) {
        boolean result = true;
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (alphabeUpCaseNumber.indexOf(temp) == -1) {
                result = false;
                return result;
            }
        }
        return result;
    }

    public static String standardInvoiceString(Long input) {
        String temp;
        if (input == null) {
            return "";
        }
        temp = input.toString();
        if (temp.length() <= INVOICE_MAX_LENGTH) {
            int count = INVOICE_MAX_LENGTH - temp.length();
            for (int i = 0; i < count; i++) {
                temp = ZERO + temp;
            }
        }
        return temp;
    }

    public static boolean validString(String temp) {
        if (temp == null || "".equals(temp.trim())) {
            return false;
        }
        return true;
    }

    public static boolean containSpecialCharacter(String inputStr) {//QuyenNT11
        boolean splchr_flag = false;
//        String[] splChrs = {"<", ">", "script", "alert", "truncate", "delete", "insert", "drop", "null", "xp_", "<>", "!", "{", "}", "`", "~", "#", "$", "%", "^", "&", "*", "?", "/", "\\", "'", "\""};  // include spl characters as per your requirement
        String[] splChrs = {"<", ">", "<>", "#", "$", "/", "\\", "'", "\""};  // include spl characters as per your requirement

        for (int i = 0; i < splChrs.length; i++) {

            if (inputStr.indexOf(splChrs[i]) >= 0) {

                splchr_flag = true;                          //bad character are available

                break;

            }

        }
        return splchr_flag;
    }
    private static Pattern pattern;
    private static Matcher matcher;
    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

//    public IPAddressValidator() {
//        pattern = Pattern.compile(IPADDRESS_PATTERN);
//    }
    /**
     * Validate ip address with regular expression
     *
     * @param ip ip address for validation
     * @return true valid ip address, false invalid ip address
     */
    public static boolean validateIP(String ip) {
       Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
       Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }
    private static final String PORT_PATTERN = "^(\\d{0,8})$";

    public static boolean validatePort(String port) {
       Pattern pattern = Pattern.compile(PORT_PATTERN);
       Matcher matcher = pattern.matcher(port);
        return matcher.matches();
    }
    private static String STRING_PATTERN = "(\\d*\\s*\\w*)";

    public static boolean validateString(String content) {
       Pattern pattern = Pattern.compile(STRING_PATTERN);
        for (int i = 0; i < content.length(); i++) {
           Matcher matcher = pattern.matcher(content.substring(i, i + 1));
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }
    private static String INTERGER_PATTERN_POSITIVE = "(\\d+)";

    public static boolean validateIntPositive(String input) {
       Pattern pattern = Pattern.compile(INTERGER_PATTERN_POSITIVE);
        for (int i = 0; i < input.length(); i++) {
           Matcher matcher = pattern.matcher(input.substring(i, i + 1));
            if (!matcher.matches()) {
                return false;
            }
        }
        if (Long.valueOf(input) <= 0) {
            return false;
        }
        return true;
    }

    public static boolean validateLength(String input, int maxlen, int minlen) {
        int length = input.length();
        if (length > maxlen || length < minlen) {
            return false;
        }
        return true;
    }

    public static boolean validateBoundary(Long input, Long max, Long min) {
        if (input.compareTo(max) > 0 || input.compareTo(min) < 0) {
            return false;
        }
        return true;
    }

    private static boolean startsWith(String str, String prefix, boolean ignoreCase) {
        if (str == null || prefix == null) {
            return (str == null && prefix == null);
        }
        if (prefix.length() > str.length()) {
            return false;
        }
        return str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
    }

    //LinhLH2
    public static String getStandardQueryString(String str) {
        StringBuilder sb = new StringBuilder(3);

        sb.append("%");

        if (str != null) {
            sb.append(str.trim().toLowerCase());
        }
        sb.append("%");

        return sb.toString();
    }
//    vietnv14 getString
    public static String getValueOf(Object s) {
        if(s==null){
            return "";
        }else{
            return String.valueOf(s);
        }
    }
//vietnv14 20160405 add bao cao dong start

    public static String getSafeFileName(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != '/' && c != '\\' && c != 0) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /*
     *  @todo: compare two String
     */
    /**
     * Ham thuc hien loc ky tu dac biet cua 1 list cac object
     *
     * @param listObj
     */
    public static void escapeHTMLString(List listObj) {
        Object o;
        for (int i = 0; i < listObj.size(); i++) {
            o = listObj.get(i);
            escapeHTMLString(o);
            listObj.set(i, o);
        }
    }

    /**
     * Ham thuc hien loc ky tu dac biet cua 1 object
     *
     * @param escapeObject
     */
    public static void escapeHTMLString(Object escapeObject) {
        String oldData = "";
        String newData = "";
        try {
            if (escapeObject != null) {
                Class escapeClass = escapeObject.getClass();

                // class
                Field fields[] = escapeClass.getDeclaredFields();
                for (Field f : fields) {
                    if (f.getType().equals(java.lang.String.class)) {
                        f.setAccessible(true);
                        if (f.get(escapeObject) != null) {
                            oldData = f.get(escapeObject).toString();
                            newData = escapeHTMLString(oldData);
                            f.set(escapeObject, newData);
                        }

                    }
                }
                // supper class
                Field superFields[] = escapeClass.getSuperclass().getDeclaredFields();
                for (Field f : superFields) {
                    if (f.getType().equals(java.lang.String.class)) {
                        f.setAccessible(true);
                        if (f.get(escapeObject) != null) {
                            oldData = f.get(escapeObject).toString();
                            newData = escapeHTMLString(oldData);
                            f.set(escapeObject, newData);
                        }
                    }
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Ham loc ky tu dac biet loc vao ca cac subObject
     *
     * @param escapeObject
     * @param pagkageSubObject
     */
    public static void escapeHTMLString(Object escapeObject, String pagkageSubObject) {
        String oldData = "";
        String newData = "";
        try {
            if (escapeObject != null) {
                Class escapeClass = escapeObject.getClass();

                // class
                Field fields[] = escapeClass.getDeclaredFields();
                for (Field f : fields) {
                    f.setAccessible(true);
                    if (f.getType().equals(java.lang.String.class)) {
//                        f.setAccessible(true);
                        if (f.get(escapeObject) != null) {
                            oldData = f.get(escapeObject).toString();
                            newData = escapeHTMLString(oldData);
                            f.set(escapeObject, newData);
                        }

                    } else if (f.getType().getName().startsWith(pagkageSubObject)) {
                        escapeHTMLString(f.get(escapeObject), pagkageSubObject);
                    }
                }

                // supper class
                Field superFields[] = escapeClass.getSuperclass().getDeclaredFields();
                for (Field f : superFields) {
                    f.setAccessible(true);
                    if (f.getType().equals(java.lang.String.class)) {
                        if (f.get(escapeObject) != null) {
                            oldData = f.get(escapeObject).toString();
                            newData = escapeHTMLString(oldData);
                            f.set(escapeObject, newData);
                        }

                    } else if (f.getType().getName().startsWith(pagkageSubObject)) {
                        escapeHTMLString(f.get(escapeObject), pagkageSubObject);
                    }
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Loc ky tu dac biet
     *
     * @param str
     * @return
     */
    public static String escapeHTMLString(String str) {
        if (str == null) {
            return null;
        }
        if (str.contains("<BR/>") || str.contains("<br/>")) {
            str = str.replaceAll("<BR/>", "\\n;");
        } else {
            str = str.replaceAll(">", "&gt;");
            str = str.replaceAll("<", "&lt;");
            str = str.replaceAll(">", "&gt;");
            str = str.replaceAll("\"", "&quot;");
        }
        return str;
    }

    //dungvv8 Start

    public static boolean isNotNull(String value) {
        return value != null && value.trim().length() > 0;
    }

    public static boolean isNotNullAndNullStr(String value) {
        return value != null && value.trim().length() > 0 && !"null".equals(value);
    }
    //dungvv8 End
//vietnv14 end
    public static String upperFirstChar(String input) {
        if (isNullOrEmpty(input)) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
    public static boolean isNullOrEmpty(String obj1) {
        return (obj1 == null || "".equals(obj1.trim()));
    }
    public static String upperString(String input){
        if(isNotNull(input))input=input.toUpperCase();
        return input;
    }
    public static String lowerString(String input){
        if(isNotNull(input))input=input.toLowerCase();
        return input;
    }
    public static Boolean stringIsLong(String str) {
        Boolean check = false;
        try {
            Long d = Long.valueOf(str);
            if (d != null) {
                check = true;
            }
        } catch (Exception ex) {
            logger.debug(ex.getMessage(), ex);
            check = false;
        }
        return check;
    }
}
