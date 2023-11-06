package dataBaseReference.Menu;

import java.util.*;
import dataBaseReference.System.*;



public class Menu extends MenuFuncs {
    private Scanner scanner;
    public Controller controller;

    public Menu() {
        scanner = new Scanner(System.in);
    }

    public void selectDB() {
        System.out.println("Please choose one of the following types of connection:");
        System.out.println("1. Local DB");
        System.out.println("2. MariaDB");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                this.controller = new Controller(DataBaseType.MEMORY);
                break;
            case 2:
                this.controller = new Controller(DataBaseType.MARIADB);
                break;
            default:
                System.out.println("\nThat's no good. Please choose either 1 or 2");
                selectDB();
        }
        this.controller.openConnection();
        showMenu();
    }
    
    @Override
    public void showMenu() {
        //usar List
        System.out.println("Menu:");
        System.out.println("1. Customer Menu");        
        System.out.println("2. Order Menu");                   
        System.out.println("3. Reports Menu");                      
        System.out.println("4. Information Menu");                    
        System.out.println("5. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
            	new CustomerMenu(this.controller).showMenu();
                this.showMenu();
                break;
            case 2:
              	// new OrderMenu(this.controller).showMenu();
                this.showMenu();
                break;
            case 3:
            	// new ReportMenu(this.controller.getCustomerDAO(), this.controller.getOrdersDAO()).showMenu();
                this.showMenu();
                break;
            case 4:
            	new InfoMenu().showMenu();
                this.showMenu();
                break;
            case 5:
                break;
            default:
                System.err.println("\nInvalid option. Please try again.");
                showMenu();
        }
    }
}
