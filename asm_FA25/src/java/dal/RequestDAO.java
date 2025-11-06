package dal;

import Model.Request;
import Model.Users;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Types;

public class RequestDAO extends DBcontext {

    //Get All request
    public List<Request> getAll() {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT * FROM Request";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int reqID = rs.getInt("reqID");
                String title = rs.getNString("title");
                int userID = rs.getInt("userID");
                Date fromDate = rs.getDate("fromDate");
                Date toDate = rs.getDate("toDate");
                String reason = rs.getString("reason");
                int statusID = rs.getInt("statusID");
                Integer approverID = rs.getInt("approverID");
                String approverNote = rs.getString("approverNote");
                Request r = new Request(reqID, title, userID, fromDate, toDate, reason, statusID, approverID, approverNote);
                list.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    //Get request by userID (To show requests from account that logged in app)
    public List<Request> getRequestByUserID(int userID) {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT * FROM Request WHERE userID = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int reqID = rs.getInt("reqID");
                String title = rs.getString("title");
                Date fromDate = rs.getDate("fromDate");
                Date toDate = rs.getDate("toDate");
                String reason = rs.getString("reason");
                int statusID = rs.getInt("statusID");
                Integer approverID = rs.getInt("approverID");
                String approverNote = rs.getString("approverNote");
                Request r = new Request(reqID, title, userID, fromDate, toDate, reason, statusID, approverID, approverNote);
                list.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    //Add request to db and list

    public void addRequest(Request request) {
        String query = "INSERT INTO Request(reqID, title, userID, fromDate, toDate, reason, statusID, approverID, approverNote) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            int newReqID = getNextRequestID(); 

            st.setInt(1, newReqID);
            st.setString(2, request.getTitle());
            st.setInt(3, request.getUserID());
            st.setDate(4, new java.sql.Date(request.getFromDate().getTime()));
            st.setDate(5, new java.sql.Date(request.getToDate().getTime()));
            st.setString(6, request.getReason());
            st.setInt(7, request.getStatusID()); // (StatusID: 1 = Pending)

            // 'approverID' is people that user select to send req
            if (request.getApproverID() != null) {
                st.setInt(8, request.getApproverID());
            } else {
                st.setNull(8, java.sql.Types.INTEGER);
            }

            // 'approverNote' always null when user send request
            st.setNull(9, java.sql.Types.NVARCHAR);

            st.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
        //Get next request ID
    public int getNextRequestID() {
        String sql = "SELECT MAX(reqID) FROM Request";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) + 1; // 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; //If null begin from 1
    }

    //Update request if user need
    public void updateRequest(Request request) {
        String query = "UPDATE Request SET title = ?, fromDate = ?, toDate = ?, reason = ? WHERE reqID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, request.getTitle());
            st.setDate(2, new java.sql.Date(request.getFromDate().getTime()));
            st.setDate(3, new java.sql.Date(request.getToDate().getTime()));
            st.setString(4, request.getReason());
            st.setInt(5, request.getApproverID());
            st.setString(6, request.getApproverNote());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Approve Or Reject Request(for approver)
    public void approveRequest(Request request) {
        String query = "UPDATE Request SET statusID = ?, approverID = ?, approverNote = ? WHERE reqID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, request.getStatusID());
            st.setInt(2, request.getApproverID());
            st.setString(3, request.getApproverNote());
            st.setInt(4, request.getReqID());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Delete(Cancel) request
    public void deleteRequest(int reqID) {
        String query = "UPDATE Request SET statusID = 4 WHERE reqID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, reqID);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Lấy tất cả các request được gửi đến một người duyệt (approver) cụ thể.
     * @param approverID UserID của người duyệt (Manager/Leader/Admin)
     * @return Danh sách các request
     */
    public List<Request> getRequestsForApprover(int approverID) {
        List<Request> list = new ArrayList<>();
        // Lấy tất cả các request có approverID là bạn, VÀ đang chờ (Pending)
        String sql = "SELECT * FROM Request WHERE approverID = ? AND statusID = 1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, approverID);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                int reqID = rs.getInt("reqID");
                String title = rs.getString("title");
                int userID = rs.getInt("userID"); // ID của người gửi đơn
                Date fromDate = rs.getDate("fromDate");
                Date toDate = rs.getDate("toDate");
                String reason = rs.getString("reason");
                int statusID = rs.getInt("statusID");
                String approverNote = rs.getString("approverNote");
                Request r = new Request(reqID, title, userID, fromDate, toDate, reason, statusID, approverID, approverNote);
                list.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    /**
     * LẤY DANH SÁCH ĐƠN MÀ TÔI CÓ THỂ DUYỆT (LOGIC MỚI: TÍCH LŨY)
     * @param currentUser Người dùng (Manager/Leader/Admin) đang đăng nhập
     * @return Danh sách các đơn (Pending) mà người này có quyền duyệt
     */
    public List<Request> getApprovableRequests(Users currentUser) {
        List<Request> list = new ArrayList<>();
        String sql = "";
        
        int userRole = currentUser.getRoleID();
        int userDept = currentUser.getDeptID();

        if (userRole == 3) {
            // Role 3 (Employee) không duyệt đơn của ai cả
            return list;
        }

        if (userRole == 0) {
            // Role 0 (Admin) -> Thấy đơn của Role 1, 2, 3 (TẤT CẢ PHÒNG BAN)
            sql = "SELECT r.* FROM Request r JOIN Users u ON r.userID = u.userID " +
                  "WHERE r.statusID = 1 AND u.roleID IN (1, 2, 3)";
        } else if (userRole == 1) {
            // Role 1 (Manager) -> Thấy đơn của Role 2, 3 (TRONG PHÒNG BAN)
            sql = "SELECT r.* FROM Request r JOIN Users u ON r.userID = u.userID " +
                  "WHERE r.statusID = 1 AND u.deptID = ? AND u.roleID IN (2, 3)";
        } else if (userRole == 2) {
            // Role 2 (Leader) -> Thấy đơn của Role 3 (TRONG PHÒNG BAN)
            sql = "SELECT r.* FROM Request r JOIN Users u ON r.userID = u.userID " +
                  "WHERE r.statusID = 1 AND u.deptID = ? AND u.roleID = 3";
        }

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            
            // Chỉ set deptID nếu người duyệt KHÔNG PHẢI là Admin
            if (userRole == 1 || userRole == 2) {
                st.setInt(1, userDept);
            }
            
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Request r = new Request(
                        rs.getInt("reqID"), rs.getString("title"), rs.getInt("userID"),
                        rs.getDate("fromDate"), rs.getDate("toDate"), rs.getString("reason"),
                        rs.getInt("statusID"), rs.getInt("approverID"), rs.getString("approverNote")
                );
                list.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    /**
     * Lấy tất cả các đơn ĐÃ ĐƯỢC DUYỆT (Approved) trong một tháng cụ thể.
     * Dùng để tô màu lịch nghỉ.
     * @param year (Vd: 2025)
     * @param month (Vd: 11)
     * @return Danh sách các đơn đã duyệt
     */
    public List<Request> getApprovedRequestsByMonth(int year, int month) {
        List<Request> list = new ArrayList<>();
        // Lấy tất cả các đơn có statusID = 2 (Approved) VÀ
        // có ngày bắt đầu (fromDate) hoặc ngày kết thúc (toDate) nằm trong tháng đó
        String sql = "SELECT * FROM Request WHERE statusID = 2 AND " +
                     "( (YEAR(fromDate) = ? AND MONTH(fromDate) = ?) OR " +
                     "  (YEAR(toDate) = ? AND MONTH(toDate) = ?) )";
        
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, year);
            st.setInt(2, month);
            st.setInt(3, year);
            st.setInt(4, month);
            
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Request r = new Request(
                        rs.getInt("reqID"), rs.getString("title"), rs.getInt("userID"),
                        rs.getDate("fromDate"), rs.getDate("toDate"), rs.getString("reason"),
                        rs.getInt("statusID"), rs.getInt("approverID"), rs.getString("approverNote")
                );
                list.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
