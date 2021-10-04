/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.webservice;

import com.mine.persistence.UserServiceImpl;
import com.mine.util.Util;
import com.mine.model.ResetPasswordMail;
import com.mine.model.User;
import com.mine.persistence.ResetPasswordServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.primefaces.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author anhdt8
 */
@WebServlet("/profileService")
public class ProfileService extends HttpServlet {

    private Logger log = Logger.getLogger(CommentGame.class.getSimpleName());
    private UserServiceImpl userService = new UserServiceImpl();
    private static final String characters = "0123456789";
    private static final int PASS_LEN = 6;
    private static final String mailcontent = "Mật khẩu mới của Quý khách là: ";

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
        try {
            response.setContentType("text/plain;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            String action = request.getParameter("action");
            if ("register".equals(action)) {
                JSONObject res = new JSONObject();
                String userName = request.getParameter("username");
                String pass = request.getParameter("password");
                String mail = request.getParameter("email");
                String phone = request.getParameter("phone");

                Map<String, Object> filters = new HashMap<>();
                filters.put("account", userName);
                List<User> lstUser = userService.findListExac(filters,null);
                if (lstUser != null && lstUser.size() > 0) {
                    res.put("result", "NOK");
                    res.put("code", "existed_username");
                    out.println(res.toString());
                    return;
                }
                filters.clear();
                filters.put("email", mail);
                lstUser = userService.findListExac(filters,null);
                if (lstUser != null && lstUser.size() > 0) {
                    res.put("result", "NOK");
                    res.put("code", "existed_email");
                    out.println(res.toString());
                    return;
                }

                User newUser = new User();
                newUser.setAccount(userName);
                newUser.setPassword(pass);
                newUser.setEmail(mail);
                newUser.setPhone(phone);

                userService.save(newUser);

                res.put("result", "OK");
                res.put("user", Util.createJSON(newUser));
                out.println(res.toString());
                return;
            } else if ("updateprofile".equals(action)) {
                JSONObject res = new JSONObject();
                //Check username, password >>>
                String username = request.getParameter("username");
                String pass = request.getParameter("password");
                Map<String, Object> filters = new HashMap<>();
                filters.put("account-EXAC", username);
                filters.put("password", pass);
                List<User> lstUser = new UserServiceImpl().findList(filters);
                User user = null;
                if (lstUser == null  || lstUser.size() <= 0) {
                    res.put("result", "not_login");
                    out.println(res.toString());
                    return;
                }
                user = lstUser.get(0);
                //Check username, password <<<
                String mail = request.getParameter("email");
                String phone = request.getParameter("phone");
                
                if (user != null) {
                    // Check trùng email
                    if (mail != null && mail.length() > 0) {
                        filters.clear();
                        filters.put("email", mail);
                        filters.put("account-NEQ", user.getAccount());
                        List<User> lstUser2 = userService.findList(filters);
                        if (lstUser2.size() > 0) {
                            res.put("result", "NOK");
                            res.put("code", "duplicate_email");
                            out.println(res.toString());
                            return;
                        } else {
                            user.setEmail(mail);
                        }
                    }
                    if (phone != null && phone.length() > 0) {
                        user.setPhone(phone);
                    }
                    userService.saveOrUpdate(user);
                    res.put("result", "OK");
                    res.put("user", Util.createJSON(user));
                    out.println(res.toString());
                    return;
                }
            } else if ("changepass".equals(action)) {
                JSONObject res = new JSONObject();

                //Check username, password >>>
                String username = request.getParameter("username");
                String pass = request.getParameter("password");
                Map<String, Object> filters = new HashMap<>();
                filters.put("account-EXAC", username);
                filters.put("password", pass);
                List<User> lstUser = new UserServiceImpl().findList(filters);
                User user = null;
                if (lstUser == null  || lstUser.size() <= 0) {
                    res.put("result", "not_login");
                    out.println(res.toString());
                    return;
                }
                user = lstUser.get(0);
                //Check username, password <<<

                String oldPass = request.getParameter("oldpass");
                String newPass = request.getParameter("newpass");
                
                if (user.getPassword().equals(oldPass)) {
                    user.setPassword(newPass);
                    userService.saveOrUpdate(user);
                    res.put("result", "OK");
                    res.put("user", Util.createJSON(user));
                    out.println(res.toString());
                    return;
                } else {
                    res.put("result", "NOK");
                    res.put("code", "wrong_old_pass");
                    out.println(res.toString());
                    return;
                }
            } else if ("resetpass".equals(action)) {
                JSONObject res = new JSONObject();
                String email = request.getParameter("email");
                String randomPass = RandomStringUtils.random(PASS_LEN, characters);

                Map<String, Object> filters = new HashMap<>();
                filters.put("email-EXAC_IGNORE_CASE", email.trim());
                List<User> lstUser = userService.findList(filters);
                if (lstUser != null && lstUser.size() > 0) {
                    User user = lstUser.get(0);
                    user.setPassword(randomPass);
                    userService.saveOrUpdate(user);

                    ResetPasswordMail mail = new ResetPasswordMail();
                    mail.setEmail(email);
                    mail.setContent(mailcontent + randomPass);
                    mail.setCreateTime(new Date());

                    new ResetPasswordServiceImpl().save(mail);
                    res.put("result", "OK");                    
                    out.println(res.toString());
                    System.out.println(res.toString());
                    return;
                } else {
                    res.put("result", "NOK");
                    res.put("code", "wrong_email");
                    out.println(res.toString());
                    System.out.println(res.toString());
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e, e);
            out.println("");
        } finally {
        }
    }

//    public static void main(String[] args) {
//        String characters = "0123456789";
//        String pwd = RandomStringUtils.random(6, characters);
//        System.out.println(pwd);
//    }
}
