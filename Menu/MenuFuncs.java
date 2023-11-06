package dataBaseReference.Menu;

import dataBaseReference.DAO.AbstractCustomerDAO;
import dataBaseReference.DAO.AbstractOrderDAO;
import dataBaseReference.DTO.Customer;
import dataBaseReference.DTO.Orders;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

// The MenuFuncs class is defined as abstract and contains common methods and variables that different menus can use.
public abstract class MenuFuncs {
    protected final Scanner scanner = new Scanner(System.in); // Used for reading terminal inputs

    protected List<String> optionsMenu; // List of options that the menu will display

    // Abstract method to display the menu, to be implemented by subclasses that contain specific menus.
    abstract public void showMenu() throws SQLException;

    // Closes the scanner when it's no longer needed.
    public void closeScanner() {
        scanner.close();
    }

    // Displays the available options from the list of options.
    public void showOptions() {
        for (String option : optionsMenu) {
            System.out.println(option);
        }
    }

    // Private method to prompt user input with a specific message.
    private String ask(String message) {

        System.out.println(message);

        // Verify the input made
        try {
            String input = scanner.nextLine();
            if (input.isEmpty() || input.isBlank()) {
                throw new NoSuchElementException();
            } else {
                return input;
            }
        } catch (NoSuchElementException | IllegalStateException exception) {
            System.err.println("Invalid input! Try again!");
            return ask(message);
        }
    }

    // Prompts the user for a string input.
    protected String askString(String message) {
        return ask(message);
    }

    // Prompts the user for a BigDecimal input.
    protected BigDecimal askBigDecimal(String message) {
        try {
            return BigDecimal.valueOf(Long.parseLong(ask(message)));
        } catch (NumberFormatException exception) {

            System.err.println("Invalid input! Try again!");
            return askBigDecimal(message);
        }
    }

    // Prompts the user for an integer input.
    protected int askInt(String message) {
        try {
            return Integer.parseInt(ask(message));
        } catch (NumberFormatException exception) {

            System.err.println("Invalid input! Try again!");
            return askInt(message);
        }
    }

    // Prompts the user for an option and returns it as an integer.
    protected int askOption() {
        try {
            return Integer.parseInt(ask("Choose an action: "));

        } catch (NumberFormatException exception) {

            System.err.println("Invalid option! Try again!");
            return askOption();
        }
    }

    // Retrieves and returns a message that indicates the available values within a specific range.
    protected String getBetweenText() {
        return " between [" + DAO.getAvailableRange()[0] + "] and [" + DAO.getAvailableRange()[1] + "]: ";
    }

    protected void printGetNotFoundMessage(String dto, String attr) {
        System.out.println("No " + dto + " found with " + attr + "!");
    }
}
