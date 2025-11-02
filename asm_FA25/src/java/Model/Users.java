package Model;
public class Users {
    private int userID;
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String phone;
    private int deptID;
    private int roleID;

    public Users(int userID, String username, String password, String fullname, String email, String phone, int deptID, int roleID) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.deptID = deptID;
        this.roleID = roleID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDeptID() {
        return deptID;
    }

    public void setDeptID(int deptID) {
        this.deptID = deptID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    @Override
    public String toString() {
        return "Users{" + "userID=" + userID + ", username=" + username + ", password=" + password + ", fullname=" + fullname + ", email=" + email + ", phone=" + phone + ", deptID=" + deptID + ", roleID=" + roleID + '}';
    }
    
    
}
