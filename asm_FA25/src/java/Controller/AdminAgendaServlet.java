package Controller;

import Model.Request;
import Model.Users;
import dal.RequestDAO;
import dal.UsersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.List;

/**
 * Xử lý trang Lịch nghỉ (Agenda)
 * CHO ADMIN (Role 0) - Phạm vi Toàn công ty
 */
public class AdminAgendaServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("user"); // Đây là Admin
        
        // 1. Lấy Năm/Tháng từ request (hoặc dùng mặc định là tháng hiện tại)
        int year;
        int month;
        try {
            year = Integer.parseInt(request.getParameter("year"));
            month = Integer.parseInt(request.getParameter("month"));
        } catch (NumberFormatException e) {
            Calendar cal = Calendar.getInstance();
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH) + 1; // Calendar.MONTH bắt đầu từ 0
        }

        // 2. Lấy danh sách Users (Phạm vi toàn công ty)
        UsersDAO udao = new UsersDAO();
        // (Hàm này sẽ tự động trả về "toàn công ty" vì currentUser là Role 0)
        List<Users> viewableUsers = udao.getAgendaViewableUsers(currentUser);
        
        // 3. Lấy danh sách Đơn đã duyệt trong tháng đó
        RequestDAO rdao = new RequestDAO();
        List<Request> approvedRequests = rdao.getApprovedRequestsByMonth(year, month);

        // 4. Lấy thông tin lịch để vẽ
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue(); // 1=Thứ 2, 7=Chủ Nhật

        // 5. Gửi tất cả dữ liệu sang JSP
        request.setAttribute("viewableUsers", viewableUsers);
        request.setAttribute("approvedRequests", approvedRequests);
        request.setAttribute("selectedYear", year);
        request.setAttribute("selectedMonth", month);
        request.setAttribute("daysInMonth", daysInMonth);
        request.setAttribute("firstDayOfWeek", firstDayOfWeek % 7); // Chuyển Chủ Nhật về 0 (để khớp logic JSP)

        // 6. Chuyển tiếp đến trang JSP của Admin
        request.getRequestDispatcher("Admin_Agenda.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Form chọn Năm/Tháng sẽ dùng POST, xử lý như GET
        doGet(request, response);
    }
}