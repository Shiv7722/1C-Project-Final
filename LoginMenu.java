import java.util.Scanner;
import java.io.Console;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
import java.util.regex.Pattern;
public class LoginMenu {
    private static Scanner sc;

    private DatabaseConnection dconnection;
    private CProvider cProvider;
    public LoginMenu() {
        this.sc = new Scanner(System.in);
        this.dconnection = new DatabaseConnection();
        this.cProvider = new CProvider();
    }

    // public static int getChoice() {
        
    //     return choice;
    // }

    public void getHomeMenu(){
        System.out.println("Login/SignUp Menu");
        System.out.println("---------");
        System.out.println("1. Login as Course Provider");
        System.out.println("2. SignUp as Course Provider");
        System.out.println("3. Login as Student");
        System.out.println("4. Exit");

        System.out.print("Enter the corresponding number to select the option : ");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1:
                cProviderLogin();
                break;
            case 2:
                cProviderSignUp();
                break;
            case 3:
                loginAsStudent();
                break;
            case 4:
                break;
            default:
            System.out.println("Please enter the valid option : ");
            getHomeMenu();
                break;
        }
    }

    
    public void cProviderSignUp(){
        String cProviderName;
        String password;
        String contact;
        int cProviderID;

        System.out.println("To SignUp as Course Provider : \nPlease enter your name : ");
        cProviderName = sc.nextLine();
        System.out.println("Please enter your Contact : ");
        while(true){
        contact = sc.nextLine();
        if(isValidCon(contact)){
            break;
        }else{
            System.out.println("Please enter valid contact\nRe-enter your contact : ");
        }
        }
        
        Console console = System.console();

        while (true) {
            String passCheck;
            char[] passwordCheckArray = console.readPassword("Create your password: ");
            passCheck = new String(passwordCheckArray);
            char[] passwordArray = console.readPassword("Re-enter the password: ");
            password = new String(passwordArray);
            if(passCheck.equals(password)){
                if(isValidPassword(password)) {
                    try {
                        dconnection.addCourseProvider(cProviderName, contact,passEncrypter(password));
                        break;
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                       
                    }
                
                } else {
                System.out.println("Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, and one digit.");
                }
        }else{
            System.out.println("Password doesn't match");
        }
        }

        System.out.println("SignedUp Successfully");
        try {
            cProviderID=dconnection.getCProviderID(cProviderName, password, contact);
            cProvider.getCProviderMenu(cProviderID);
        } catch (SQLException e) {
            e.getMessage();
        }
        
        

    }

    public void cProviderLogin(){
        String cProviderName;
        String password;
        String contact;
        int cProviderID;

        System.out.println("To Login as Course Provider : \nPlease enter your name : ");
        cProviderName = sc.nextLine();
        
        while(true){
        System.out.println("Please enter your Contact : ");
        contact = sc.nextLine();
        if(isValidCon(contact)){
            break;
        }else{
            System.out.println("Please enter valid contact\nRe-enter your contact : ");
        }
        }
        
        Console console = System.console();

        while (true) {
            char[] passwordArray = console.readPassword("Enter the password: ");
            password = new String(passwordArray);
                if(isValidPassword(password)) {                    
                    try {
                        if(dconnection.checkCProviderInfo(cProviderName, password, contact)){
                            cProviderID=dconnection.getCProviderID(cProviderName, password, contact);
                        break;
                        }else{
                            System.out.println("No such data found");
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                       
                    }
                }else {
                System.out.println("Wrong password !");
                }
        
        }
        System.out.println("Login successful");
        cProvider.getCProviderMenu(cProviderID);

    }

    // Method to validate the password
    private static boolean isValidPassword(String password) {
        String passPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$";
        return Pattern.matches(passPattern, password);
    }

    private static boolean isValidCon(String contact) {
        String conPattern = "^[6-9][0-9]{9}$";
        return Pattern.matches(conPattern, contact);
    }

    public static String passEncrypter(String pass){
        return BCrypt.hashpw(pass,BCrypt.gensalt());
    }

    public static boolean passMatch(String pass,String passEncrypted){
        return BCrypt.checkpw(pass,passEncrypted);
    }

        
    
    

    
    public void loginAsStudent() {
        String username;
        int studentID, roll;

        System.out.println("To login as Student : \nPlease enter your name : ");
        username = sc.nextLine();
        System.out.println("Please enter your StudentID : ");
        studentID = sc.nextInt();
        

        System.out.println("Please enter your Roll Number : ");
        roll = sc.nextInt();
        try {
            boolean checkResult =dconnection.checkStudentLoginInfo(username, studentID);
         if (checkResult) {
                System.out.println("Login successful! Welcome, "+username);
                
            }else{
                System.out.println("Please enter correct information :");
                loginAsStudent();
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage()+"\nPlease enter the correct information :");
            loginAsStudent();
        }
        
       
        
    }

    

    
    public static void main(String[] args) {
        LoginMenu lMenu = new LoginMenu();
        lMenu.getHomeMenu();
    }
}