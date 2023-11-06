package dataBaseReference.Menu;

import dataBaseReference.DAO.AbstractCustomerDAO;
import dataBaseReference.DAO.AbstractOrderDAO;
import dataBaseReference.DTO.Customer;
import dataBaseReference.DTO.Orders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// The CustomerMenu class is a specific menu related to customers, extending MenuFuncs.
public class CustomerMenu extends MenuFuncs {
    private final AbstractCustomerDAO customerDAO;
    private final AbstractOrderDAO orderDAO;

    public CustomerMenu(AbstractCustomerDAO abstractCustomerDAO, AbstractOrderDAO abstractOrderDAO) {
        this.customerDAO = abstractCustomerDAO;
        this.orderDAO = abstractOrderDAO;

        // Creating the options that the menu has
        optionsMenu = new ArrayList<>();
        optionsMenu.add("1 - Insert a new customer");
        optionsMenu.add("2 - Get customer by ID");
        optionsMenu.add("3 - Get customers by NAME");
        optionsMenu.add("4 - Delete a customer by ID");
        optionsMenu.add("5 - List all customers");
        optionsMenu.add("6 - Back to main menu");
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

                customer.setId(askInt("Customer ID" + getBetweenText()));
                customer.setName(askString("Customer NAME: "));
                customer.setCity(askString("Customer CITY: "));
                customer.setState(askString("Customer STATE: "));

                customerDAO.addCustomer(customer);
                break;

            case 2: //Get costumer by ID
                System.out.println("\n--GET CUSTOMER BY ID--\n");

                customer = customerDAO.getCustomerById(askInt("Enter customer ID to search" + getBetweenText()));

                if (customer != null) {
                    System.out.println("Customer: \n" + customer);
                } else {
                    printGetNotFoundMessage("Customer", "ID");
                }
                break;

            case 3: // Get costumer that have the name typed
                System.out.println("\n--GET CUSTOMER BY NAME--\n");

                List<Customer> customers = customerDAO.getAllCustomersOrderedByName();

                if (!customers.isEmpty()) {
                    System.out.println("Customers: \n");
                    for (Customer customer1 : customers) {
                        System.out.println(customer1);
                    }
                } else {
                    printGetNotFoundMessage("Customers", "NAME");
                }
                break;

            case 4: // Delete costumer by ID
                System.out.println("\n--DELETE CUSTOMER BY ID--\n");

                Customer customerToDelete = customerDAO.getCustomerById(askInt("Enter customer ID to delete" + getBetweenText()));
                
                if (customerToDelete != null) {
                    List<Orders> orders = orderDAO.getOrdersByCustomerId(customerToDelete.getId());
                    if (!orders.isEmpty()) {
                        for (Orders order : orders) {
                            orderDAO.deleteOrder(order.getNumber());
                        }
                    }
                    customerDAO.deleteCustomer(customerToDelete.getId());
                    System.out.println("Customer successfully deleted");
                } else {
                    printGetNotFoundMessage("Customer", "ID");
                }
                break;

            case 5: // List all Customers
                System.out.println("\n--LISTING ALL CUSTOMERS--\n");

                List<Customer> allCustomers = customerDAO.getAllCustomersOrderedByName();

                if (!allCustomers.isEmpty()) {
                    System.out.println("Customers: \n");
                    for (Customer customer1 : allCustomers) {
                        System.out.println(customer1);
                    }
                } else {
                    System.out.println("No customers found.");
                }
                break;
        }

        if (option != 6) { //exit
            this.showMenu();
        }
    }
}

