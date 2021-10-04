package com.mine.webservice;

import org.primefaces.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("serial")
@WebServlet("/logoutService")
public class LogoutService extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        mydoGet(req, resp);
    }

    private void mydoGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // TODO Auto-generated method stub
        req.getSession().removeAttribute("login");
        req.getSession().removeAttribute("user");
        PrintWriter out = resp.getWriter();
        JSONObject res = new JSONObject();
        try {
            resp.setContentType("text/plain; charset=UTF-8");
            res.put("result", "OK");
            out.println(res.toString());
        } catch (Exception ex) {
            resp.setStatus(500);
        } finally {
            out.close();
        }

    }

}
