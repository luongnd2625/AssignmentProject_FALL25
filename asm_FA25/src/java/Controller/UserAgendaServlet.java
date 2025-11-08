package Controller;

import Model.Department;
import Model.Request;
import Model.Role;
import Model.Users;
import dal.DepartmentDAO;
import dal.RequestDAO;
import dal.RoleDAO;
import dal.UsersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate; // Sử dụng java.time
import java.time.YearMonth;
import java.util.Calendar;
import java.util.List;

public class UserAgendaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("user");
        
        // 1. Lấy Năm/Tháng từ request (hoặc dùng mặc định là tháng hiện tại)
        int year;
        int month;

        try {
            year = Integer.parseInt(request.getParameter("year"));
            month = Integer.parseInt(request.getParameter("month"));
        } catch (NumberFormatException e) {
            // Nếu không có param, dùng tháng/năm hiện tại
            Calendar cal = Calendar.getInstance();
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH) + 1; // (Calendar.MONTH bắt đầu từ 0)
        }

        // 2. Lấy danh sách Users (Phạm vi phòng ban)
        UsersDAO udao = new UsersDAO();
        // (Hàm này sẽ tự động trả về phạm vi phòng ban vì currentUser là Role 1/2)
        List<Users> viewableUsers = udao.getAgendaViewableUsers(currentUser);
        
        // 3. Lấy danh sách Đơn đã duyệt trong tháng đó
        RequestDAO rdao = new RequestDAO();
        List<Request> approvedRequests = rdao.getApprovedRequestsByMonth(year, month);

        // 4. Lấy thông tin lịch để vẽ
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        
        // Lấy ngày bắt đầu của tháng (là thứ mấy)
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue(); // 1=Thứ 2, 7=Chủ Nhật
        DepartmentDAO ddao = new DepartmentDAO();
        RoleDAO rodao = new RoleDAO();
        List<Department> dlist = ddao.getAll();
        List<Role> rlist = rodao.getAll();
        // 5. Gửi tất cả dữ liệu sang JSP
        request.setAttribute("viewableUsers", viewableUsers);
        request.setAttribute("approvedRequests", approvedRequests);
        request.setAttribute("selectedYear", year);
        request.setAttribute("selectedMonth", month);
        request.setAttribute("daysInMonth", daysInMonth);
        request.setAttribute("firstDayOfWeek", firstDayOfWeek % 7); // Chuyển Chủ Nhật về 0 (để khớp logic JSP)
        request.setAttribute("dlist", dlist);
        request.setAttribute("rlist", rlist);
        
        request.getRequestDispatcher("User_Agenda.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Form chọn Năm/Tháng sẽ dùng POST, xử lý như GET
        doGet(request, response);
    }
}