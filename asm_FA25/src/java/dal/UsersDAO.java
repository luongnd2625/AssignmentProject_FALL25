package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Users;
import java.util.ArrayList;
import java.util.List;


public class UsersDAO extends DBcontext{
    //Login
    public Users getUsersByUsernameAndPassword(String username, String password) {
        Users account = null;
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";

        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                int userID = rs.getInt("userID");
                String fullname = rs.getString("fullname");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                int deptID = rs.getInt("deptID");
                int roleID = rs.getInt("roleID");
                account = new Users(userID, username, password, fullname, email, phone, deptID, roleID);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return account;
    }
    //Check email
    public boolean isEmailTaken(String email) {
        String query = "SELECT * FROM Users WHERE email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            return rs.next(); //If found email, true
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    //Check phone for register
    public boolean isPhoneTaken(String phone) {
        String query = "SELECT * FROM Users WHERE phone = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, phone);
            ResultSet rs = st.executeQuery();
            return rs.next(); //If found phone, true
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    //Get all users
    public List<Users> getAll() {
        List<Users> list = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int userID = rs.getInt("userID");
                String username = rs.getString("Username");
                String password = rs.getString("password");
                String fullname = rs.getString("fullname");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                int deptID = rs.getInt("deptID");
                int roleID = rs.getInt("roleID");
                Users user = new Users(userID, username, password, fullname, email, phone, deptID, roleID);
                list.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    //Get all user expect admin
    public List<Users> getAllNotAdmin() {
        List<Users> list = new ArrayList<>();
        String sql = "SELECT * FROM Users where userID != 0";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int userID = rs.getInt("userID");
                String username = rs.getString("Username");
                String password = rs.getString("password");
                String fullname = rs.getString("fullname");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                int deptID = rs.getInt("deptID");
                int roleID = rs.getInt("roleID");
                Users user = new Users(userID, username, password, fullname, email, phone, deptID, roleID);
                list.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    //Filter for roleID
    public List<Users> filterByRoleID(int roleID) {
        List<Users> list = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE roleID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, roleID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt("userID");
                String username = rs.getString("Username");
                String password = rs.getString("password");
                String fullname = rs.getString("fullname");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                int deptID = rs.getInt("deptID");
                Users user = new Users(userID, username, password, fullname, email, phone, deptID, roleID);
                list.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    //Add user
    public void addUser(Users users){
        String query = "INSERT INTO Users (username, password, fullname, email, phone, deptID, roleID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, users.getUsername());
            st.setString(2, users.getPassword());
            st.setString(3, users.getFullname());
            st.setString(4, users.getEmail());
            st.setString(5, users.getPhone());
            st.setInt(6, users.getDeptID());
            st.setInt(7, users.getRoleID());
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    //Update user's information
     public void editUser(Users users){
        String query = "UPDATE Users SET username = ?, password = ?, fullname = ?, email = ?, phone = ?, deptID = ?, roleID = ? WHERE userID =?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, users.getUsername());
            st.setString(2, users.getPassword());
            st.setString(3, users.getFullname());
            st.setString(4, users.getEmail());
            st.setString(5, users.getPhone());
            st.setInt(6, users.getDeptID());
            st.setInt(7, users.getRoleID());
            st.setInt(8, users.getUserID());
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    //Delete user in db
    public void deleteUsers(int userID) {
        String sql = "DELETE from Users WHERE userID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userID);
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    
    //Check username
    public boolean isUsernameTaken(String username) {
        String query = "SELECT * FROM Users WHERE username = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            return rs.next(); //If found username, true
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    //Update user's OWN profile (for UserSettings)
    public void updateUserProfile(Users users){
        String query = "UPDATE Users SET password = ?, fullname = ?, email = ?, phone = ? WHERE userID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, users.getPassword());
            st.setString(2, users.getFullname());
            st.setString(3, users.getEmail());
            st.setString(4, users.getPhone());
            st.setInt(5, users.getUserID()); // Use userID to check owner
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Lấy UserID của người duyệt đơn TỰ ĐỘNG dựa trên quy tắc nghiệp vụ.
     * @param userDeptID Phòng ban của người gửi
     * @param userRoleID Vai trò của người gửi
     * @return UserID của người duyệt, hoặc null nếu không tìm thấy.
     */
    public Integer getAutomaticApproverID(int userDeptID, int userRoleID) {
        String sql = "";
        Integer approverRole = null;

        if (userRoleID == 3 || userRoleID == 2) {
            // Role 3 (Employee) & Role 2 (Leader) -> Gửi cho Role 1 (Manager) CÙNG PHÒNG BAN
            approverRole = 1;
            sql = "SELECT TOP 1 userID FROM Users WHERE deptID = ? AND roleID = ?";
        } else if (userRoleID == 1) {
            // Role 1 (Manager) -> Gửi cho Role 0 (Admin)
            approverRole = 0;
            sql = "SELECT TOP 1 userID FROM Users WHERE roleID = 0";
        } else {
            // Role 0 (Admin) không gửi đơn
            return null;
        }

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            
            if (approverRole == 1) { // Chỉ set deptID nếu tìm Manager (Role 1)
                st.setInt(1, userDeptID);
            }
            
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // Trả về userID của người duyệt
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        // Trả về null nếu không tìm thấy
        return null; 
    }
    
    
}
