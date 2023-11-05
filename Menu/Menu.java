package GrupoA4.Menu;

import java.util.*;
import Menu.CustomerMenu;

public class Menu {
    public static void main(String[] args) {
        showWelcomeMessage();

        boolean keepExc= true;
        Scanner scanner = new Scanner(System.in);

        while (keepExc) {
            showMainMenu();
            int option= scanner.nextInt();

            switch (option) {
                case 1:
                    // Customers
                    CustomerMenu customermenu = new CustomerMenu();
                    customermenu.showMenu();
                case 2:
                    // Orders
                    OrderMenu order = new OrderMenu();
                    order.showMenu();
                case 3:
                    // Reports
                    ReportMenu reportmenu = new ReportMenu();
                    reportmenu.showMenu();
                case 4:
                    // Informations
                    InformationsMenu infomenu = new InformationsMenu();
                    infomenu.showMenu();
                case 5:
                    keepExc = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        showGoodbyeMessage();
    }

    public static void showWelcomeMessage() {
        System.out.println("Welcome!");
        System.out.println("This is a Java program designed to manage a MariaDB database on a remote Linux server, performing CRUD operations.");
        System.out.println("Version: 1.0");
        System.out.println("Updated in: ");
    }

    public static void showMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Customers");
        System.out.println("2. Orders");
        System.out.println("3. Reports");
        System.out.println("4. Informations");
        System.out.println("5. Exit");
        System.out.print("Choose option: ");
    }

    public static void showGoodbyeMessage() {
        System.out.println("Finished");
    }
}