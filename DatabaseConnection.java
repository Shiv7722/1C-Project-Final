import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.lang.Class;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/1cproject";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    private static Connection con;

    public DatabaseConnection() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            System.out.print("The driver class is not available to connect to database");
            System.exit(1);
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());

        }
    }

    public boolean checkStudentLoginInfo( String name, int id) throws SQLException {
    ResultSet rSet = null;
    PreparedStatement pStatement = null;
    try {
        pStatement = con.prepareStatement("select * from student where studentName = ? and studentID = ? ");
        pStatement.setString(1, name);
        pStatement.setInt(2, id);
        rSet = pStatement.executeQuery();
        if (rSet.next()) {
            return true;
        } else {
            return false; // No data found
        }
    } finally {
        if (rSet != null) {
            rSet.close();
        }
        if (pStatement != null) {
            pStatement.close();
        }
    }
}



public boolean checkCProviderInfo(String name, String pass, String contact) throws SQLException {
    PreparedStatement pStatement = null;
    ResultSet rSet = null;
    try {
        pStatement = con.prepareStatement("select * from cProvider where cProviderName = ? and cProviderCon= ?");
        pStatement.setString(1, name);
        pStatement.setString(2, contact);
        rSet = pStatement.executeQuery();
        if(rSet.next()){
            String encryptedPass = rSet.getString("cProviderPass");
            if(LoginMenu.passMatch(pass, encryptedPass)){
                return true;
            }else{
                return false;
            }
            
        }else{
            return false;
        }
    } finally{
        if (rSet != null) {
            rSet.close();
        }
        if (pStatement != null) {
            pStatement.close();
        }
    }    
}

public int getCProviderID(String name, String pass, String contact) throws SQLException {
    PreparedStatement pStatement = null;
    ResultSet rSet = null;
    try {
        pStatement = con.prepareStatement("select * from cProvider where cProviderName = ? and cProviderCon= ?");
        pStatement.setString(1, name);
        pStatement.setString(2, contact);
        rSet = pStatement.executeQuery();
        if(rSet.next()){
            String encryptedPass = rSet.getString("cProviderPass");
            if(LoginMenu.passMatch(pass, encryptedPass)){
                return rSet.getInt("cProviderID");
            }else{
                return -1;
            }
            
        }else{
            return -2;
        }
    } finally{
        if (rSet != null) {
            rSet.close();
        }
        if (pStatement != null) {
            pStatement.close();
        }
    }    
}

//Method to uplode new course provider to database
public void addCourseProvider(String name,String contact,String pass) throws SQLException{
    PreparedStatement pStatement = null;
    try {
        pStatement = con.prepareStatement("insert into cprovider(cProviderName,cProviderCon,cProviderPass) values(?,?,?)");
        pStatement.setString(1, name);
        pStatement.setString(2, contact);
        pStatement.setString(3, pass);
        int result = pStatement.executeUpdate();
        if (result==1) {
            System.out.println("Account Created Successfully");
        }
    } finally {
        
        if (pStatement != null) {
            pStatement.close();
        }
    }
}

public ResultSet getStudentDetails(int cProviderId) throws SQLException{
    ResultSet rSet = null;
    PreparedStatement pStatement = null;
    try {
        pStatement = con.prepareStatement("select studentName, stRoll, stCon from student where cProviderId = ?");
        pStatement.setInt(1, cProviderId);
        rSet = pStatement.executeQuery();
        return rSet;
        
    } finally {
        if (pStatement != null) {
            pStatement.close();
        }
        if (rSet != null) {
            rSet.close();
        }
    }
}

}