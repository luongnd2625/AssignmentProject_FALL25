package dal;

import Model.Status;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatusDAO extends DBcontext{
    //GetAll
    public List<Status> getAll() {
        List<Status> list = new ArrayList<>();
        String sql = "SELECT * FROM Status";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int statusID = rs.getInt("statusID");
                String statusName = rs.getString("statusName");
                Status s = new Status(statusID, statusName);
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    //GetAll for approver
    public List<Status> getAll2() {
        List<Status> list = new ArrayList<>();
        String sql = "SELECT * FROM Status WHERE statusID != 1 AND statusID != 4";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int statusID = rs.getInt("statusID");
                String statusName = rs.getString("statusName");
                Status s = new Status(statusID, statusName);
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
