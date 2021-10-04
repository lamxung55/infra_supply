/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.webservice;

import com.mine.persistence.UserServiceImpl;
import com.mine.model.Game;
import com.mine.model.User;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author anhdt8
 */
@WebServlet("/voteGame")
public class VoteGame extends HttpServlet {

    private Logger log = Logger.getLogger(VoteGame.class.getSimpleName());

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
        JSONObject res = new JSONObject();
        try {
            //Check username, password >>>
            String username = request.getParameter("username");
            String pass = request.getParameter("password");
            Map<String, Object> filters = new HashMap<>();
            filters.put("account-EXAC", username);
            filters.put("password", pass);
            List<User> lstUser = new UserServiceImpl().findList(filters);
            if (lstUser == null || lstUser.size() <= 0) {
                res.put("result", "not_login");
                out.println(res.toString());
                return;
            }
            response.setContentType("text/plain;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            Long gameId = Long.parseLong(request.getParameter("gameId"));
            Double starVote = Double.parseDouble(request.getParameter("star"));

            GameServiceImpl gameService = new GameServiceImpl();
            Game game = gameService.findById(gameId);
            if (game != null) {
                long lastVoteTimes = game.getVoteTimes() == null ? 0 : game.getVoteTimes();
                game.setVoteTimes(lastVoteTimes + 1);
                double lastStarAvg = game.getStarAvg() == null ? 0 : game.getStarAvg();
                double starAvg = Math.round(((double) (lastStarAvg * lastVoteTimes + starVote) / (lastVoteTimes + 1) * 100.0)) / 100.0;
                game.setStarAvg(starAvg);
                gameService.saveOrUpdate(game);
            }
            res.put("result", "OK");
            out.println(res.toString());
            return;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e, e);
            out.println("");
        } finally {
        }
    }
    public static void main(String[] args) {
        System.out.println("xxx:" + (Double.parseDouble("3.5")));
    }

}
