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
    
    
}
