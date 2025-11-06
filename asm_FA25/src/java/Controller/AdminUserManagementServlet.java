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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        UsersDAO udao = new UsersDAO();
        System.out.println("ACTION: " + action);

        if (action.equalsIgnoreCase("addSave")) {
        } else if (action.equalsIgnoreCase("edit")) {
            int userID = Integer.parseInt(request.getParameter("userID"));
            String usernameStr = request.getParameter("username");
            String password = request.getParameter("password");
            String fullname = request.getParameter("fullname"); 
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            int deptId = Integer.parseInt(request.getParameter("deptId"));
            int roleId = Integer.parseInt(request.getParameter("roleId"));
            Users userEdit = new Users(userID, usernameStr, password, fullname, email, phone, deptId, roleId);
            System.out.println("User Edit: "+userEdit.toString());
            udao.editUser(userEdit);
        } 

        response.sendRedirect("adminUserManagement");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        UsersDAO udao = new UsersDAO();
        DepartmentDAO ddao = new DepartmentDAO();
        RoleDAO rdao = new RoleDAO();

        List<Users> ulist = udao.getAll();
        List<Department> dlist = ddao.getAll();
        List<Role> rlist = rdao.getAll();

        if (action != null && action.equalsIgnoreCase("add")) {
            request.setAttribute("dlist", dlist);
            request.setAttribute("rlist", rlist);
            System.out.println("ACTION: " + action);
            for (Role r : rlist) {
                System.out.println("ROLE LIST: " + r);
            }
            for (Department d : dlist) {
                System.out.println("DEPARTMENT LIST: " + d);
            }
            request.getRequestDispatcher("Admin_UserAdd.jsp").forward(request, response);
            return;
        } else if (action != null && action.equalsIgnoreCase("delete")) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            udao.deleteUsers(userId);
            response.sendRedirect("adminUserManagement");
            return;
        }
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
