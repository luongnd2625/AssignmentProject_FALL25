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
import java.text.SimpleDateFormat;
import java.util.Date; 

public class UserRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("User_Request.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        try {
            // 1. Lấy thông tin người dùng hiện tại từ Session
            Users currentUser = (Users) session.getAttribute("user");
            // 2. Lấy thông tin từ Form
            String title = request.getParameter("title");
            String fromDateStr = request.getParameter("fromDate");
            String toDateStr = request.getParameter("toDate");
            String reason = request.getParameter("reason");
            // 3. Tìm người duyệt đơn TỰ ĐỘNG 
            UsersDAO udao = new UsersDAO();
            Integer approverID = udao.getAutomaticApproverID(currentUser.getDeptID(), currentUser.getRoleID());
            // 4. Xử lý logic
            if (approverID == null) {
                // Lỗi: Không tìm thấy người duyệt (cho admin)
                session.setAttribute("user_message_error", "Không tìm thấy người duyệt đơn. (Tài khoản Admin không thể tạo đơn, hoặc phòng ban của bạn chưa có Trưởng phòng)");
            } else {
                // 5. Chuyển đổi String (từ form) sang java.util.Date
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date fromDate = formatter.parse(fromDateStr);
                Date toDate = formatter.parse(toDateStr);
                // 6. Lấy ID tiếp theo cho Request
                RequestDAO rdao = new RequestDAO();
                int newReqID = rdao.getNextRequestID(); // Dùng hàm bạn đã thêm ở bước trước
                // 7. Tạo đối tượng Request mới
                // StatusID = 1 (Pending)
                Request newRequest = new Request(newReqID, title, currentUser.getUserID(), fromDate, toDate, reason, 1, approverID, null);
                // 8. Thêm vào Database
                rdao.addRequest(newRequest);
                // 9. Gửi thông báo thành công
                session.setAttribute("user_message_success", "Gửi đơn xin nghỉ thành công!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("user_message_error", "Đã xảy ra lỗi khi gửi đơn: " + e.getMessage());
        }
        // 10. Tải lại trang (để hiển thị thông báo)
        response.sendRedirect("userRequest");
    }
}