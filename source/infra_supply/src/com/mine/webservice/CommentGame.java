/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.webservice;

import com.google.gson.GsonBuilder;
import com.mine.persistence.CommentServiceImpl;
import com.mine.persistence.UserServiceImpl;
import com.mine.model.Comment;
import com.mine.model.User;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author anhdt8
 */
@WebServlet("/commentGame")
public class CommentGame extends HttpServlet {

    private Logger log = Logger.getLogger(CommentGame.class.getSimpleName());

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
            response.setContentType("text/plain;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            String action = request.getParameter("action");
            if ("comment".equals(action)) {
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

                Long userId = user.getUserId();
                Long gameId = Long.parseLong(request.getParameter("gameId"));
                String content = null;
                try {
                    content = request.getParameter("comment");
                    log.info("Content1:" + content);
                    content = new String(content.getBytes(
                            "iso-8859-1"), "UTF-8");
                    content = URLDecoder.decode(content, "utf-8");
                    log.info("Content2:" + content);
                } catch (Exception e) {
                }

                CommentServiceImpl commentService = new CommentServiceImpl();
                Comment comment = new Comment();
                comment.setUserId(userId);
                comment.setGameId(gameId);
                comment.setContent(content);
                comment.setSentTime(new Date());
                commentService.save(comment);
                res.put("result", "OK");
                out.println(res.toString());
                return;
            } else if ("getcomment".equals(action)) {
                int start = -1;
                int end = -1;
                try {
                    start = Integer.parseInt(request.getParameter("start"));
                    end = Integer.parseInt(request.getParameter("end"));
                } catch (Exception ex) {
                }
                Long gameId = Long.parseLong(request.getParameter("gameId"));
                CommentServiceImpl commentService = new CommentServiceImpl();
                List<?> objs = commentService.findList("from VComment where gameId=? order by sentTime desc", start, end, gameId);
                jResult = new GsonBuilder().create().toJson(objs);
                out.println(jResult);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e, e);
            out.println("");
        } finally {
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println("XXX:" + (new String("th%E1%BB%AD+c%C3%A1i+nh%C3%A9".getBytes(), "UTF-8")));
        String x = new String("thá»­ xem nÃ o".getBytes(
                "iso-8859-1"), "UTF-8");
        System.out.println("UUU:" + x);
    }

}
