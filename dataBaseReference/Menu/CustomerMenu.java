package dataBaseReference.Menu;


import dataBaseReference.DTO.Customer;
import dataBaseReference.System.Controller;
import java.util.ArrayList;


// The CustomerMenu class is a specific menu related to customers, extending MenuFuncs.
public class CustomerMenu extends MenuFuncs {
    Controller currentDB;

    public CustomerMenu(Controller controller) {
        // Creating the options that the menu has
        optionsMenu = new ArrayList<>();
        optionsMenu.add("1 - Insert a new customer");
        optionsMenu.add("2 - Get customer by ID");
        optionsMenu.add("3 - Get customers by NAME");
        optionsMenu.add("4 - Delete a customer by ID");
        optionsMenu.add("5 - List all customers");
        optionsMenu.add("6 - Back to main menu");

        this.currentDB = controller;
    }

    // Method to display the customer menu and handle selected options.
    @Override
    public void showMenu() {

        System.out.println("\nCustomer Actions:");

        showOptions();

        int option = askOption();
        
        Customer customer = new Customer();
        
        switch (option) {
            case 1: //Insert new costumer

                System.out.println("\n--INSERTING NEW CUSTOMER--\n");

                currentDB.insertCustomer();
                break;

            case 2: //Get costumer by ID
                System.out.println("\n--GET CUSTOMER BY ID--\n");

                currentDB.requestCustomerByID(askInt("Customer ID:" + getBetweenText()));

                break;

            case 3: // Get costumer that have the name typed
                System.out.println("\n--GET CUSTOMER BY NAME--\n");

                currentDB.requestCustomerByName();
                
                break;

            case 4: // Delete costumer by ID
                System.out.println("\n--DELETE CUSTOMER BY ID--\n");
                currentDB.deleteCustomerByID(askInt("ID of Costumer to be deleted:"));
                break;

            case 5: // List all Customers
                System.out.println("\n--LISTING ALL CUSTOMERS--\n");
                currentDB.requestAllCostumers();
                break;
        }

        if (option != 6) { //exit
            this.showMenu();
        }
    }
}

