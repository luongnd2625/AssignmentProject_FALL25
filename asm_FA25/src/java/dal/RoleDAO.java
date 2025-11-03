package dal;

import Model.Role;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO extends DBcontext {
    //GetAll
    public List<Role> getAll() {
        List<Role> list = new ArrayList<>();
            String sql = "SELECT * FROM Role";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int roleId = rs.getInt("roleID");
                String roleName = rs.getString("roleName");
                Role r = new Role(roleId, roleName);
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
