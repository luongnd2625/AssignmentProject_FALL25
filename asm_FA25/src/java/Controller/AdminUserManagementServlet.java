package Controller;

import Model.Department;
import Model.Role;
import Model.Users;
import dal.DepartmentDAO;
import dal.RoleDAO;
import dal.UsersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AdminUserManagementServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AdminUserManagementServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminUserManagementServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }

    }

    // Hãy đảm bảo bạn có import jakarta.servlet.http.HttpSession;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        UsersDAO udao = new UsersDAO();
        HttpSession session = request.getSession();

        try {
            if (action.equalsIgnoreCase("addSave")) {
                String usernameStr = request.getParameter("username");
                String password = request.getParameter("password");
                String fullname = request.getParameter("fullname");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                int deptID = Integer.parseInt(request.getParameter("deptID"));
                int roleID = Integer.parseInt(request.getParameter("roleID"));

                // Validation
                if (udao.isUsernameTaken(usernameStr)) {
                    session.setAttribute("admin_message_error", "Lỗi: Tên đăng nhập '" + usernameStr + "' đã tồn tại!");
                } else if (udao.isEmailTaken(email)) {
                    session.setAttribute("admin_message_error", "Lỗi: Email '" + email + "' đã tồn tại!");
                } else if (udao.isPhoneTaken(phone)) {
                    session.setAttribute("admin_message_error", "Lỗi: Số điện thoại '" + phone + "' đã tồn tại!");
                } else {
                    Users userAdd = new Users(0, usernameStr, password, fullname, email, phone, deptID, roleID);
                    udao.addUser(userAdd);
                    session.setAttribute("admin_message_success", "Thêm nhân viên '" + fullname + "' thành công!");
                }

            } else if (action.equalsIgnoreCase("edit")) {
                // Change data of user
                int userID = Integer.parseInt(request.getParameter("userID"));
                String usernameStr = request.getParameter("username");
                String password = request.getParameter("password");
                String fullname = request.getParameter("fullname");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                int deptID = Integer.parseInt(request.getParameter("deptID"));
                int roleID = Integer.parseInt(request.getParameter("roleID"));

                Users userEdit = new Users(userID, usernameStr, password, fullname, email, phone, deptID, roleID);
                udao.editUser(userEdit);
                session.setAttribute("admin_message_success", "Cập nhật nhân viên '" + fullname + "' thành công!");
            }

        } catch (Exception e) {
            session.setAttribute("admin_message_error", "Đã xảy ra lỗi hệ thống: " + e.getMessage());
            e.printStackTrace();
        }

        response.sendRedirect("adminUserManagement");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        UsersDAO udao = new UsersDAO();
        if (action != null && action.equalsIgnoreCase("delete")) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            udao.deleteUsers(userId);
            response.sendRedirect("adminUserManagement");
            return;
        }
        DepartmentDAO ddao = new DepartmentDAO();
        RoleDAO rdao = new RoleDAO();

        List<Users> ulist = udao.getAll();
        List<Department> dlist = ddao.getAll();
        List<Role> rlist = rdao.getAll();

        request.setAttribute("ulist", ulist);
        request.setAttribute("dlist", dlist);
        request.setAttribute("rlist", rlist);
        request.getRequestDispatcher("Admin_UserManagement.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "description";
    }

}
