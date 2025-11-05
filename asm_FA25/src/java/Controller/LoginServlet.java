package Controller;

import Model.Users;
import dal.UsersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        
        String user = req.getParameter("username");
        String pass = req.getParameter("password");
        String remember = req.getParameter("remember");
        
        Cookie cu = new Cookie("cuser", user);
        Cookie cp = new Cookie("cpass", pass);
        Cookie cr = new Cookie("crem", remember);
        
        //If user turn on button "Nho mat khau", user can get a cookie for 7days, forward to jsp that user can be access
        if (remember != null) {
            cu.setMaxAge(604800);
            cp.setMaxAge(604800);       //7days
            cr.setMaxAge(604800);
        } else {
            cu.setMaxAge(0);
            cp.setMaxAge(0);            
            cr.setMaxAge(0);
        }
        
        resp.addCookie(cu);
        resp.addCookie(cp);
        resp.addCookie(cr);
        UsersDAO d = new UsersDAO();
        try {
            Users n = d.getUsersByUsernameAndPassword(user, pass);
            if(n ==  null) {
                req.setAttribute("error", "Wrong username or password, try again !!!");
                req.getRequestDispatcher("Login.jsp").forward(req, resp);
            } else {
                session.setAttribute("user", n);
                
                if (n.getRoleID() == 0) {
                    resp.sendRedirect("adminUserManagement");
                } else {
                    resp.sendRedirect("userRequest");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
