package Controller;

import Model.Users;
import dal.UsersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Register.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get all data from register
        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        int deptID = Integer.parseInt(request.getParameter("deptID"));
        int roleID = Integer.parseInt(request.getParameter("roleID"));

        UsersDAO udao = new UsersDAO();
            
        try {      //Check if any type user trying to register is existed 
            if (udao.isUsernameTaken(username)) {
                request.setAttribute("error", "Username '" + username + "' đã được sử dụng!");
                request.getRequestDispatcher("Register.jsp").forward(request, response);
            } 
            else if (udao.isEmailTaken(email)) {
                request.setAttribute("error", "Email '" + email + "' đã được sử dụng!");
                request.getRequestDispatcher("Register.jsp").forward(request, response);
            }
            else if (udao.isPhoneTaken(phone)) {
                request.setAttribute("error", "Số điện thoại '" + phone + "' đã được sử dụng!");
                request.getRequestDispatcher("Register.jsp").forward(request, response);
            }
            else {  //If register successfully,add new user to db and forward to login
                Users newUser = new Users(0, username, pass, fullname, email, phone, deptID, roleID);
                udao.addUser(newUser);
                response.sendRedirect("login"); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi. Vui lòng thử lại. (Chi tiết: " + e.getMessage() + ")");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        }
    }
}