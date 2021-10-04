package com.mine.webservice;

import com.mine.util.Util;
import com.mine.model.User;
import com.mine.persistence.UserServiceImpl;
import org.primefaces.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Servlet implementation class LoginEntry This class indicates which login page
 * may be used for Web or Mobile
 */
@WebServlet("/loginService")
public class LoginService extends HttpServlet {

    private static final long serialVersionUID = 1L;
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private UserServiceImpl userService = new UserServiceImpl();

    public LoginService() {

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();

            PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8), true);

            String user = request.getParameter("username");
            String pass = request.getParameter("password");
            Map<String, Object> filters = new HashMap<>();
            filters.put("account", user);
            filters.put("password", pass);
            List<User> lstUser = userService.findListExac(filters,null);
            JSONObject res = new JSONObject();
            if (lstUser != null && lstUser.size() > 0) {
                session.setAttribute("login", "true");
                session.setAttribute("user", lstUser.get(0));
                res.put("result", "OK");
                res.put("user", Util.createJSON(lstUser.get(0)));
                out.println(res.toString());
            } else {
                res.put("result", "NOK");
                out.println(res.toString());
            }
            System.out.println(res.toString());
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    

    public static void main(String[] args) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
