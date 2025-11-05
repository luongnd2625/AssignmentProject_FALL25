package Controller;

import Model.Users;
import dal.UsersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        
        String user = req.getParameter("username");
        String pass = req.getParameter("password");
        
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("Login.jsp").forward(req, resp);
    }
    
}