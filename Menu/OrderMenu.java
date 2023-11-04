package GrupoA4.Menu;

import DAO.AbstractCustomerDAO;
import DAO.AbstractOrderDAO;
import DTO.Customer;
import DTO.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// The OrderMenu class extends the MenuFuncs class and implements an order menu.
public class OrderMenu extends MenuFuncs {
    private final AbstractCustomerDAO customerDAO;
    private final AbstractOrderDAO orderDAO;

    public OrderMenu(AbstractCustomerDAO abstractCustomerDAO, AbstractOrderDAO abstractOrderDAO) {
        this.customerDAO = abstractCustomerDAO;
        this.orderDAO = abstractOrderDAO;

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
            case 1 -> { // Add a new order to a client
                System.out.println("\n--ADDING NEW ORDER TO CLIENT--\n");

                Customer customer = customerDAO.getById(askInt("Enter customer ID for adding a order: " + getBetweenText()));
                
                if (customer != null) {
                    Order order = new Order();
                    order.setCustomerId(customer.getId());
                    
                    order.setNumber(askInt("Order number: " + getBetweenText()));
                    order.setPrice(askBigDecimal("Order price: "));
                    order.setDescription(askString("Order desciption: "));

                    orderDAO.add(order);
                } else {
                    printGetNotFoundMessage("Customer", "ID");
                }
            }

            case 2 -> { // Get an order by number
                System.out.println("\n--GET ORDER BY NUMBER--\n");

                Order order = orderDAO.getById(askInt("Enter order number to search" + getBetweenText()));

                if (order != null) {
                    Customer customer = customerDAO.getById(order.getCustomerId());
                    System.out.println("Customer: \n-Id: " + customer.getId() + "\n-Name: " + customer.getName() + "\n-Order: \n" + order);
                } else {
                    printGetNotFoundMessage("Order", "Number given");
                }
            }

            case 3 -> { // Delete an order
                System.out.println("\n--DELETING ORDER--\n");

                Order order = orderDAO.getById(askInt("Enter order number to delete: "));

                if (order != null) {
                    orderDAO.delete(order.getNumber());
                } else {
                    printGetNotFoundMessage("Order", "number given");
                }
            }


        }
        if (option != 4) { // Exit
            this.showMenu();
        }
    }
}
