/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.webservice;

import com.google.gson.GsonBuilder;
import com.mine.persistence.GameServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author anhdt8
 */
@WebServlet("/getListGame")
public class GetListGame extends HttpServlet {

    private Logger log = Logger.getLogger(GetListGame.class.getSimpleName());

    private GameServiceImpl gameService = new GameServiceImpl();

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
            int start = -1;
            int end = -1;
            try {
                start = Integer.parseInt(request.getParameter("start"));
                end = Integer.parseInt(request.getParameter("end"));
            } catch (Exception ex) {
            }
            response.setContentType("text/plain;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            LinkedHashMap<String, String> order = new LinkedHashMap<String, String>();
            order.put("createDate", "DESC");
            List<?> objs = gameService.findList(start, end, null, order);
            jResult = new GsonBuilder().create().toJson(objs);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e,e);
        } finally {
            out.println(jResult);
        }
    }

}
