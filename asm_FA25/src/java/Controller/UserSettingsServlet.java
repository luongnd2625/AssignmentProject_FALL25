package Controller;

import Model.Users;
import dal.UsersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class UserSettingsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("User_Settings.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        UsersDAO udao = new UsersDAO();
        
        // Take user object from session
        Users currentUser = (Users) session.getAttribute("user");

        try {
            // Get new information from form
            String newPass = request.getParameter("password");
            String newFullname = request.getParameter("fullname");
            String newEmail = request.getParameter("email");
            String newPhone = request.getParameter("phone");

            // Update data
            currentUser.setPassword(newPass);
            currentUser.setFullname(newFullname);
            currentUser.setEmail(newEmail);
            currentUser.setPhone(newPhone);
            // Call DAO to update db 
            udao.updateUserProfile(currentUser);
            //Update session to show new data of user
            session.setAttribute("user", currentUser);
            //Send message
            session.setAttribute("user_message_success", "Cập nhật thông tin thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("user_message_error", "Đã xảy ra lỗi: " + e.getMessage());
        }
        // Reload page
        response.sendRedirect("userSettings");
    }
}