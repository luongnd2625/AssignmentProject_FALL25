package dal;

import Model.Request;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Types;

public class RequestDAO extends DBcontext{
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
            while(rs.next()) {
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
        String query = "INSERT INTO Request(title, userID, fromDate, toDate, reason, statusID, approverID, approverNote) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, request.getTitle());
            st.setInt(2, request.getUserID());
            st.setDate(3, (java.sql.Date) request.getFromDate());
            st.setDate(4, (java.sql.Date) request.getToDate());
            st.setString(5, request.getReason());
            st.setInt(6, request.getStatusID());

            if (request.getApproverID() != null) {
                st.setInt(7, request.getApproverID());

                // If this request is be approved, they can have note
                if (request.getApproverNote() != null && !request.getApproverNote().trim().isEmpty()) {
                    st.setString(8, request.getApproverNote());
                } else {
                    st.setNull(8, java.sql.Types.NVARCHAR);
                }
            } else {
                // If no one approve/reject this request, their type must be null
                st.setNull(7, java.sql.Types.INTEGER);
                st.setNull(8, java.sql.Types.NVARCHAR);
            }
            st.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
    //Filter of request
}

