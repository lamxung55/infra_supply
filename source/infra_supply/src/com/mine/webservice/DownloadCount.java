/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.webservice;

import com.mine.persistence.GameServiceImpl;
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

/**
 *
 * @author anhdt8
 */
@WebServlet("/downloadCount")
public class DownloadCount extends HttpServlet {

    private Logger log = Logger.getLogger(DownloadCount.class.getSimpleName());

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
            Long gameId = Long.parseLong(request.getParameter("gameId"));
            response.setContentType("text/plain;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            gameService.execteBulk("update Game set downTimes=downTimes+1 where gameId = ?", gameId);
            JSONObject res = new JSONObject();
            res.put("result", "OK");
            out.println(res.toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e, e);
        } finally {
            out.println(jResult);
        }
    }

}
