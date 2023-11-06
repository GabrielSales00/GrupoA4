package dataBaseReference.Menu;


import dataBaseReference.System.Controller;

import java.sql.SQLException;
import java.util.ArrayList;


// The OrderMenu class extends the MenuFuncs class and implements an order menu.
public class OrderMenu extends MenuFuncs {
    Controller currentDB;
    public OrderMenu(Controller controller) {
        this.currentDB = controller;

        optionsMenu = new ArrayList<>();
        optionsMenu.add("1 - Add a new order to a client");
        optionsMenu.add("2 - Get an order by number");
        optionsMenu.add("3 - Delete an order");
        optionsMenu.add("4 - Back to main menu");
    }

    // Method to display the order menu and handle selected options.
    @Override
    public void showMenu() throws SQLException {

        System.out.println("\nOrders actions:");

        showOptions(); //Calling the function declared on "MenuFuncs"

        int option = askOption();

        switch (option) {
            case 1: // Add a new order to a client
                System.out.println("\n--ADDING NEW ORDER TO CLIENT--\n");

                currentDB.insertOrder();

                break;

            case 2: // Get an order by number
                System.out.println("\n--GET ORDER BY NUMBER--\n");

                currentDB.insertOrderByNumber();
                break;

            case 3:  // Delete an order
                System.out.println("\n--DELETING ORDER--\n");
                currentDB.deleteOrder();
                break;
        }
        if (option != 4) { // Exit
            this.showMenu();
        }
    }
}
