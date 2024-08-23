public class CProvider {
    private DatabaseConnection dConnect;

    public CProvider(){
        this.dConnect= new DatabaseConnection();
    }

    public void getCProviderMenu(){
        System.out.println("Course Provider Menu");
        System.out.println("---------");
        System.out.println("1. Get Student Details");
        System.out.println("2. Add Student");
        System.out.println("3. Remove Student");
        System.out.println("4. Exit");

        System.out.print("Enter the corresponding number to select the option : ");
        switch (LoginMenu.getChoice()) {
            case 1:
                
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
            getCProviderMenu();
                break;
        }
    }


}
