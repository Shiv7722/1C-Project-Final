import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.ResultSet;

public class Student {
    public static void main(String[] args) {
        Student st = new Student();
        st.getstudentMenu(57689);
        // st.View_Course();
    }

    Scanner sc = new Scanner(System.in);

    public void getstudentMenu(int cProviderID) {
        System.out.println("Student");
        System.out.println("---------");

        System.out.println("1. View courses");
        System.out.println("2. Enroll");
        System.out.println("3. Track Progress");
        System.out.println("4. Certificate");

        System.out.print("Enter the corresponding number to select the option : ");
        int choice1 = sc.nextInt();

        switch (choice1) {
            case 1:
                View_Course();
                break;
            case 2:
                // Registration();
                // registerUser();
                Enroll();
                break;
            case 3:

                break;
            case 4:

                break;

            default:
                break;
        }
    }

    private Connection conn;

    public void Registration() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/1cproject", "root", "root");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void registerUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter studentID: ");
        int studentID = scanner.nextInt();
        System.out.print("Enter studentName: ");
        String studentName = scanner.next();
        System.out.print("Enter studentRollno: ");
        int stRoll = scanner.nextInt();
        System.out.print("Enter student contact no.: ");
        long stCon = scanner.nextLong();
        // System.out.println("Enter course provider ID: ");
        // int cProviderID;

        try {
            String query = "INSERT INTO student ( studentID, studentName , stRoll , stCon ) VALUES (?, ?, ? , ?  )";
            PreparedStatement ps = conn.prepareStatement(query);

            // Set the values for the query
            ps.setInt(1, studentID);
            ps.setString(2, studentName);
            ps.setInt(3, stRoll);
            ps.setLong(4, stCon);
            // ps.setInt(5, cProviderID);

            ps.executeUpdate();
            System.out.println("Welcome!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void View_Course() {

        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/1cproject";
        String username = "root";
        String password = "root";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM courses ";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                // Set the parameter value
                // pstmt.setInt(1, 18);

                // Execute the query
                try (ResultSet rs = pstmt.executeQuery()) {
                    // Process the results
                    while (rs.next()) {
                        int courseID = rs.getInt("courseID");
                        String courseName = rs.getString("courseName");
                        int duration = rs.getInt("duration");
                        int capacity = rs.getInt("capacity");

                        // Print the fetched data to the terminal
                        System.out.println("courseID: " + courseID + ", courseName: " + courseName + ", duration: "
                                + duration + ", capacity: " + capacity);
                        System.out.println(
                                "-------------------------------------------------------------------------------------------");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void Enroll() {
        String url = "jdbc:mysql://localhost:3306/1cproject";
        String username = "root";
        String password = "root";

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the student's ID: ");
        int studentID = scanner.nextInt();

        System.out.print("Enter the course ID: ");
        int courseID = scanner.nextInt();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String query = "INSERT INTO enrollment (studentID , courseId) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                // Set the parameter values
                pstmt.setInt(1, studentID);
                pstmt.setInt(2, courseID);

                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Student enrolled successfully! " + rowsAffected + " row(s) affected.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
