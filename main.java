package GrupoA4.Menu;

import DAO.AbstractCustomerDAO;
import DAO.AbstractOrderDAO;
import DTO.Customer;
import DTO.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportMenu extends MenuFuncs {
    private final AbstractCustomerDAO customerDAO;
    private final AbstractOrderDAO orderDAO;

    public ReportMenu(AbstractCustomerDAO abstractCustomerDAO, AbstractOrderDAO abstractOrderDAO) {
        this.customerDAO = abstractCustomerDAO;
        this.orderDAO = abstractOrderDAO;

        optionsMenu = new ArrayList<>();
        optionsMenu.add("1 - Clientes ordenados por identificador");
        optionsMenu.add("2 - Clientes ordenados por nome");
        optionsMenu.add("3 - Pedidos ordenados por número");
        optionsMenu.add("4 - Pedidos dos clientes ordenados por nome");
        optionsMenu.add("5 - Voltar ao menu principal");
    }

    // Método para exibir o menu de relatórios e lidar com as opções selecionadas.
    @Override
    public void showMenu() throws SQLException {
        System.out.println("\nAções de relatórios:");

        showOptions();

        int option = askOption();

        switch (option) {
        case 1 -> {
            List<Customer> customers = customerDAO.getAllSortedById();
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }
        case 2 -> {
            List<Customer> customers = customerDAO.getAllSortedByName();
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }
        case 3 -> {
            List<Order> orders = orderDAO.getAllSortedByNumber();
            for (Order order : orders) {
                System.out.println(order);
            }
        }
        case 4 -> {
            List<Customer> customers = customerDAO.getAllSortedByName();
            for (Customer customer : customers) {
                System.out.println(customer);
                List<Order> orders = orderDAO.getAllByCustomerIdSortedByNumber(customer.getId());
                for (Order order : orders) {
                    System.out.println(order);
        if (option != 5) {
            this.showMenu();
        }
    }
}

public class InfoMenu extends MenuFuncs {
    public InfoMenu() {
        optionsMenu = new ArrayList<>();
        optionsMenu.add("1 - Ajuda");
        optionsMenu.add("2 - Sobre");
        optionsMenu.add("3 - Voltar ao menu principal");
    }

    // Método para exibir o menu de informações e lidar com as opções selecionadas.
    @Override
    public void showMenu() {
        System.out.println("\nAções de informações:");

        showOptions();

        int option = askOption();

        switch (option) {
            case 1 -> {
                System.out.println("Este programa permite gerenciar clientes e pedidos. Você pode adicionar, obter e deletar clientes e pedidos. Além disso, você pode visualizar relatórios de clientes e pedidos.");
            }
            case 2 -> {
                System.out.println("Versão 1.0.0. Desenvolvido por GrupoA4.");
            }
        }
        if (option != 3) {
            this.showMenu();
        }
    }
}