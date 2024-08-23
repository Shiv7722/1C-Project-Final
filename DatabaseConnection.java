import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.protocol.Resultset;

import java.lang.Class;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/1cproject";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    private Connection con;

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

    public int getStudentLoginInfo( String name, int id) throws SQLException {
    ResultSet rSet = null;
    PreparedStatement pStatement = null;
    try {
        pStatement = con.prepareStatement("select stRoll from student where studentName = ? and studentID = ? ");
        pStatement.setString(1, name);
        pStatement.setInt(2, id);
        rSet = pStatement.executeQuery();
        if (rSet.next()) {
            return rSet.getInt("stRoll");
        } else {
            return -1; // No data found
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



public boolean checkCProviderInfo( String name, String pass,String contact) throws SQLException {
    ResultSet rSet = null;
    PreparedStatement pStatement = null;
    try {
        pStatement = con.prepareStatement("select cProviderName,cProviderPass,cProviderCon from cprovider where cProviderCon = ? and cProviderPass = ?");
        pStatement.setString(1, contact);
        pStatement.setString(2, LoginMenu.passEncrypter(pass));
        rSet = pStatement.executeQuery();
        if (rSet.next()) {
            if((rSet.getString("cProviderName").equals(name))&&(LoginMenu.passMatch(pass,rSet.getString("cProviderPass")))&&((rSet.getString("cProviderCon")).equals(contact))){
                boolean x = rSet.getString("cProviderName").equals(name)&&(rSet.getString("cProviderPass")).equals(pass)&&(rSet.getString("cProviderCon")).equals(contact);
                System.out.println(x);
                return true;
            }else{
                return false;
            }
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
        } else {
            System.out.println("Unsuccessfull");
        }
    } finally {
        
        if (pStatement != null) {
            pStatement.close();
        }
    }
}

public ResultSet getStudentDetails(int id) throws SQLException{
    ResultSet rSet = null;
    PreparedStatement pStatement = null;
    try {
        pStatement = con.prepareStatement("select studentName, stRoll, stCon from student where cProviderId = cprovider.");
        
        pStatement.setInt(1, id);
        rSet = pStatement.executeQuery();
        if (rSet!=null) {
            return rSet;
        } else {
            return null;
        }
    } finally {
        
        if (pStatement != null) {
            pStatement.close();
        }
    }
}
public static void main(String[] args) {
    DatabaseConnection dCon = new DatabaseConnection();
    try {
        boolean x = dCon.checkCProviderInfo("Shivraj Singh", "Shiv1234", "7722974467");
         System.out.println(x);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}