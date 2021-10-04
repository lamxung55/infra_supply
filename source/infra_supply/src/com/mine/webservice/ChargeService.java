/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.webservice;

import com.google.gson.GsonBuilder;
import com.mine.persistence.HisChargeServiceImpl;
import com.mine.persistence.UserServiceImpl;
import com.mine.model.HisChargeMobile;
import com.mine.model.User;
import com.mine.persistence.HisChargeMobileServiceImpl;
import com.mine.util.TripleDES;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.primefaces.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

import static org.springframework.http.HttpHeaders.USER_AGENT;

/**
 *
 * @author anhdt8
 */
@WebServlet("/chargeService")
public class ChargeService extends HttpServlet {

    private Logger log = Logger.getLogger(ChargeService.class.getSimpleName());
    private static long period_check_attemp = 5 * 60 * 1000;
    private static int retry_times = 5;
    private static String SECRETKEY = "KNP_Secret";
    private static String CHARGE_URL = "http://domain/?param=";
    private static String TXTPARTNERID = "123";
    private static String MYSIGNAL = "MySignal";

    static void readConfig() {
        FileInputStream fileInput = null;
        try {
            File file = new File("config.properties");
            fileInput = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInput);
            period_check_attemp = Long.parseLong(properties
                    .getProperty("period_check_attemp")) * 1000 * 60;
            retry_times = Integer.parseInt(properties
                    .getProperty("retry_times"));
            CHARGE_URL = properties.getProperty("charge_url");
            SECRETKEY = properties.getProperty("KNP_Secret");
            TXTPARTNERID = properties.getProperty("TxtPartnerID");
            MYSIGNAL = properties.getProperty("MySignal");

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            try {
                if (fileInput != null) {
                    fileInput.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private HisChargeServiceImpl gameService = new HisChargeServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcessRequest(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcessRequest(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    private void doProcessRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                response.getOutputStream(), StandardCharsets.UTF_8), true);
        String jResult = "";
        try {
            JSONObject res = new JSONObject();
            //Check username, password >>>
            String username = request.getParameter("username");
            String pass = request.getParameter("password");
            Map<String, Object> filters = new HashMap<>();
            filters.put("account-EXAC", username);
            filters.put("password", pass);
            List<User> lstUser = new UserServiceImpl().findList(filters);
            User user = null;
            if (lstUser == null || lstUser.size() <= 0) {
                res.put("result", "not_login");
                out.println(res.toString());
                return;
            }
            user = lstUser.get(0);
            //Check username, password <<<
            String action = request.getParameter("action");
            HttpSession session = request.getSession();
            if ("charge".equals(action)) {
                UUID uniqueKey = UUID.randomUUID();
                log.info("TransactionID: " + uniqueKey + " generated!");
                String TxtType = (String) request.getParameter("TxtType");
                String TxtMaThe = (String) request.getParameter("TxtMaThe");
                String TxtSeri = (String) request.getParameter("TxtSeri");
                String resultCode = null;
                String resultDes = null;
                String money = null;
                try {
                    Date lock_time = user.getLockTime();
                    if (lock_time != null) {
                        if (new Date().getTime() - lock_time.getTime() < period_check_attemp) {
                            log.info(user.toString() + " dang tam khoa do nap sai nhieu lan...");
                            res.put("result", "lock");
                            res.put("remain_time", Math.round((float) (new Date().getTime() - lock_time.getTime()) / 1000));
                            out.println(res.toString());
                            return;
                        } else {
                            user.setLockTime(null);
                            user.setFailNumber(null);
                        }
                    }
                    // Thực hiện charging...

                    resultCode = doCharge(TxtType, TxtMaThe, TxtSeri, uniqueKey.toString());

                    if ("10@".contains(resultCode)) {
                        out.println(res.toString());
                        money = "10000";
                        user.setMoney(user.getMoney() + Double.parseDouble(money));// return money from charging API
                        user.setLockTime(null);
                        user.setFailNumber(null);
                        new UserServiceImpl().save(user);
                        res.put("result", "OK");
                        res.put("user", user);
                        resultDes = "Nạp thành công: " + user.getMoney();
                        return;
                    } else if ("00@".contains(resultCode)) {
                        // Goi lai ham callback
                    } else if ("99@".contains(resultCode)
                            || "02@".contains(resultCode)
                            || "03@".contains(resultCode)
                            || "05@".contains(resultCode)
                            || "06@".contains(resultCode)
                            || "07@".contains(resultCode)
                            || "08@".contains(resultCode)
                            || "12@".contains(resultCode)) {
                        // Bi sai do nguoi dung can luu lai de khoa neu vuot qua

//                        Integer lastFail = (Integer) session.getAttribute("fail_number");
                        Integer lastFail = (Integer) user.getFailNumber();
                        lastFail = (lastFail == null ? 0 : lastFail);
                        lastFail = lastFail + 1;
                        if (lastFail.intValue() > retry_times) {
//                                session.setAttribute("lock_time", new Date());
                            user.setLockTime(new Date());
                        } else {
//                                session.setAttribute("fail_number", lastFail);
                            user.setFailNumber(lastFail);
                        }
                        res.put("result", "NOK");
                        res.put("fail_number", lastFail);
                        res.put("user", user);
                        resultDes = "Thẻ không đúng do người dùng nhập";
                        out.println(res.toString());
                        return;
                    } else {
                        // Sai do he thong - khong bi khoa
                        res.put("result", "NOK");
                        out.println(res.toString());
                        resultDes = "Lỗi nạp thẻ do hệ thống";
                        return;
                    }
                } catch (Exception e) {
                    log.error(e);
                    // Sai do he thong - khong bi khoa
                    res.put("result", "NOK");
                    out.println(res.toString());
                    resultDes = "Lỗi hệ thống:" + e.getMessage();
                    return;
                } finally {
                    // Luu lai lich su
                    String telCoString = "N/A";
                    if ("1".equals(TxtType)) {
                        telCoString = "mine";
                    } else if ("2".equals(TxtType)) {
                        telCoString = "Mobiphone";
                    } else if ("3".equals(TxtType)) {
                        telCoString = "Vinaphone";
                    }
                    HisChargeMobile hisCharge = new HisChargeMobile();
                    String info = "PIN:" + TxtMaThe + "|Serial:" + TxtSeri
                            + "|Mạng:" + telCoString
                            + "|Kết quả:" + resultCode + "|Mô tả:" + resultDes
                            + "|Số tiền:" + money;

                    hisCharge.setTime((new SimpleDateFormat("HH:mm:ss dd/MM/yyyy")).format(new Date()));
                    hisCharge.setChargeInfo(info);
                    hisCharge.setUserId(user.getUserId());
                    new HisChargeMobileServiceImpl().save(hisCharge);
                }
            } else if ("exchange".equals(action)) {

            } else if ("hisCharge".equals(action)) {
                int start = -1;
                int end = -1;
                try {
                    start = Integer.parseInt(request.getParameter("start"));
                    end = Integer.parseInt(request.getParameter("end"));
                } catch (Exception ex) {

                }
                response.setContentType("text/plain;charset=UTF-8");
                request.setCharacterEncoding("UTF-8");
//                filters.clear();
//                filters.put("userId", user.getUserId());
//                HashMap<String, String> order = new HashMap<String, String>();
//                order.put("time", "DESC");
                List<?> objs = new HisChargeMobileServiceImpl().findList("from HisChargeMobile where userId=? order by time desc", start, end, user.getUserId());
                jResult = new GsonBuilder().create().toJson(objs);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e, e);
        } finally {
            out.println(jResult);
        }
    }

    private String doCharge(String TxtType, String TxtMaThe, String TxtSeri, String TxtTransId) throws Exception {

        String url = CHARGE_URL;

        StringBuilder param = new StringBuilder();
        param.append("TxtPartnerID=").append(TXTPARTNERID);
        param.append("TxtType=").append(TxtType);
        param.append("TxtMaThe=").append(TxtMaThe);
        param.append("TxtSeri=").append(TxtSeri);
        param.append("TxtTransId=").append(TxtTransId);

        String TxtKey = DigestUtils.md5Hex(TXTPARTNERID + TxtType + TxtTransId + TxtMaThe + MYSIGNAL);
        param.append("TxtKey=").append(TxtKey);

        String encryptParam = TripleDES.encrypt(param.toString(), SECRETKEY);

        url = url + encryptParam;

        log.info("Charge url sent: " + url);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        log.info("\nSending 'GET' request to URL : " + url);
        log.info("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        return response.toString();
    }

    public static void main(String[] args) {
        Date d1 = new Date();
        try {
            Thread.sleep(150);
            Date d2 = new Date();
//            Math.round(((double) (lastStarAvg * lastVoteTimes + starVote) / (lastVoteTimes + 1) * 100.0)
            System.out.println("xxx:" + (float) (new Date().getTime() - d1.getTime()) / 1000);
            System.out.println("xxx:" + Math.round((float) (new Date().getTime() - d1.getTime()) / 1000));
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(ChargeService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
