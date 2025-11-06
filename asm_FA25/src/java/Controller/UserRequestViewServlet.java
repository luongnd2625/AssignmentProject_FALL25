package Controller;

import Model.Request;
import Model.Status;
import Model.Users;
import dal.RequestDAO;
import dal.StatusDAO;
import dal.UsersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class UserRequestViewServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("user");
        // Xử lý Hủy đơn (nếu có action=cancel)
        String action = request.getParameter("action");
        if (action != null && action.equalsIgnoreCase("cancel")) {
            try {
                int reqID = Integer.parseInt(request.getParameter("reqID"));
                RequestDAO rdao = new RequestDAO();
                // Dùng phương thức 'deleteRequest' (chính là "Cancel")
                rdao.deleteRequest(reqID);
                session.setAttribute("user_message_success", "Đã hủy đơn #" + reqID + " thành công.");
            } catch (Exception e) {
                session.setAttribute("user_message_error", "Lỗi khi hủy đơn: " + e.getMessage());
            }
        }
        // 1. Lấy danh sách ĐƠN CỦA TÔI
        RequestDAO rdao = new RequestDAO();
        List<Request> myRequests = rdao.getRequestByUserID(currentUser.getUserID());
        // 2. Lấy danh sách TẤT CẢ USER (để tìm tên người duyệt)
        UsersDAO udao = new UsersDAO();
        List<Users> allUsers = udao.getAll();
        // 3. Lấy danh sách TẤT CẢ STATUS (để tìm tên trạng thái)
        StatusDAO sdao = new StatusDAO();
        List<Status> allStatus = sdao.getAll();
        // 4. Gửi cả 3 danh sách này sang JSP
        request.setAttribute("myRequests", myRequests);
        request.setAttribute("allUsers", allUsers);
        request.setAttribute("allStatus", allStatus);
        // 5. Chuyển tiếp đến trang JSP
        request.getRequestDispatcher("User_RequestView.jsp").forward(request, response);
    }
}