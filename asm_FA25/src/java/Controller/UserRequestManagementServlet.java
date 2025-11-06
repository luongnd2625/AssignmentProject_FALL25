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

/**
 * Servlet này xử lý việc Quản lý (Duyệt/Từ chối) đơn
 * Dành cho Role 0 (Admin), Role 1 (Manager), và Role 2 (Leader)
 */
public class UserRequestManagementServlet extends HttpServlet {

    /**
     * doGet: Lấy danh sách các đơn "Pending" mà người dùng hiện tại
     * có quyền duyệt, dựa trên logic "Quyền hạn tích lũy".
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("user");
        
        // 1. Lấy danh sách ĐƠN CẦN BẠN DUYỆT (dùng hàm logic tích lũy mới nhất)
        RequestDAO rdao = new RequestDAO();
        List<Request> pendingRequests = rdao.getApprovableRequests(currentUser);
        
        // 2. Lấy danh sách TẤT CẢ USER (để tìm tên người gửi đơn)
        UsersDAO udao = new UsersDAO();
        List<Users> allUsers = udao.getAll();
        
        // 3. Lấy danh sách TẤT CẢ STATUS (để dùng cho Modal)
        StatusDAO sdao = new StatusDAO();
        // Dùng hàm getAll2() (bạn đã viết) để loại bỏ "Pending" và "Canceled"
        List<Status> statusOptions = sdao.getAll2(); 

        // 4. Gửi các danh sách này sang JSP
        request.setAttribute("pendingRequests", pendingRequests);
        request.setAttribute("allUsers", allUsers);
        request.setAttribute("statusOptions", statusOptions);

        // 5. Chuyển tiếp đến trang JSP
        request.getRequestDispatcher("User_RequestManagement.jsp").forward(request, response);
    }

    /**
     * doPost: Xử lý khi Manager/Leader/Admin nhấn "Lưu thay đổi" (Duyệt/Từ chối)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("user");

        try {
            // 1. Lấy dữ liệu từ form (trong Modal)
            int reqID = Integer.parseInt(request.getParameter("reqID"));
            int newStatusID = Integer.parseInt(request.getParameter("statusID"));
            String approverNote = request.getParameter("approverNote");
            // 2. Lấy request gốc từ DB (để điền các thông tin còn thiếu)
            RequestDAO rdao = new RequestDAO();
            // Tạo một đối tượng Request 'ảo' chỉ để update
            // (Vì hàm approveRequest của bạn chỉ cần 4 trường: 
            // statusID, approverID, approverNote, và reqID)
            Request requestToUpdate = new Request(
                    reqID, 
                    null, // title (không cần)
                    0,    // userID (không cần)
                    null, // fromDate (không cần)
                    null, // toDate (không cần)
                    null, // reason (không cần)
                    newStatusID, // statusID (mới)
                    currentUser.getUserID(), // approverID (là BẠN)
                    approverNote // approverNote (mới)
            );
            
            // 3. Gọi DAO để cập nhật (Approve/Reject)
            rdao.approveRequest(requestToUpdate); // Sử dụng hàm bạn đã code
            
            session.setAttribute("user_message_success", "Đã xử lý đơn #" + reqID + " thành công.");
            
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("user_message_error", "Lỗi khi xử lý đơn: " + e.getMessage());
        }

        // Tải lại trang quản lý đơn
        response.sendRedirect("userRequestManagement");
    }
}