package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBcontext {

    protected Connection connection;

    public DBcontext() {
    try {
        String user = "luongnd";
        String pass = "12345";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Lrm;encrypt=false;"; 
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, user, pass);
    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(DBcontext.class.getName()).log(Level.SEVERE, null, ex);
    }
}
}