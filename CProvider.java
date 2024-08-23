import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class CProvider {
    private DatabaseConnection dConnect;
    private Scanner sc;
    public CProvider(){
        this.sc=new Scanner(System.in);
        this.dConnect= new DatabaseConnection();
    }

    public void getCProviderMenu(int cProviderID){
        System.out.println("Course Provider Menu");
        System.out.println("---------");

        System.out.println("1. Get Student Details");
        System.out.println("2. Add Student");
        System.out.println("3. Remove Student");
        System.out.println("4. Exit");

        System.out.print("Enter the corresponding number to select the option : ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
            try {
                printStudentInfo(dConnect.getStudentDetails(cProviderID));
            } catch (SQLException e) {
                e.getMessage();
            }
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                LoginMenu.exit();
                break;
            default:
            System.out.println("Please enter the valid option : ");
            getCProviderMenu(cProviderID);
                break;
        }
    }

    public void printStudentInfo(ResultSet rSet) throws SQLException{
        while (rSet.next()) {
            System.out.println(rSet.getString("studentName")+"\t|\t"+rSet.getInt("stRoll"));
        }
    }

}
