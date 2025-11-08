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

public class AdminRequestManagementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("user");
        // 1. Lấy danh sách ĐƠN CẦN BẠN DUYỆT (dùng hàm logic tích lũy)
        // Vì currentUser.getRoleID() == 0, hàm này sẽ tự động chạy logic của Admin
        RequestDAO rdao = new RequestDAO();
        List<Request> allRequests = rdao.getApprovableRequests(currentUser);
        // 2. Lấy danh sách TẤT CẢ USER (để tìm tên người gửi đơn)
        UsersDAO udao = new UsersDAO();
        List<Users> allUsers = udao.getAll();
        // 3. Lấy danh sách TẤT CẢ STATUS (để dùng cho Modal)
        StatusDAO sdao = new StatusDAO();
        List<Status> statusOptions = sdao.getAll(); // (Approved, Rejected)
        // 4. Gửi các danh sách này sang JSP
        request.setAttribute("pendingRequests", allRequests);
        request.setAttribute("allUsers", allUsers);
        request.setAttribute("statusOptions", statusOptions);
        // 5. Chuyển tiếp đến trang JSP của Admin
        // (Đảm bảo tên file của bạn là Admin_RequestManagement.jsp)
        request.getRequestDispatcher("Admin_RequestManagement.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("user"); // Là Admin

        try {
            int reqID = Integer.parseInt(request.getParameter("reqID"));
            int newStatusID = Integer.parseInt(request.getParameter("statusID"));
            String approverNote = request.getParameter("approverNote");

            RequestDAO rdao = new RequestDAO();
            
            // Tạo đối tượng 'ảo' để update
            Request requestToUpdate = new Request(
                    reqID, null, 0, null, null, null, 
                    newStatusID, 
                    currentUser.getUserID(), // approverID (là Admin)
                    approverNote
            );
            
            // Gọi DAO để cập nhật
            rdao.approveRequest(requestToUpdate);
            
            // Gửi thông báo thành công (dùng session của Admin)
            session.setAttribute("admin_message_success", "Đã xử lý đơn #" + reqID + " thành công.");
            
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("admin_message_error", "Lỗi khi xử lý đơn: " + e.getMessage());
        }

        // Tải lại trang quản lý đơn của Admin
        response.sendRedirect("adminRequestManagement");
    }
}