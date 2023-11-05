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
                    }
                }
            }
        }
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

package DAO;

import DTO.Customer;
import DTO.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends AbstractCustomerDAO {
    private Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    public Customer getById(int id) throws SQLException {
        String sql = "SELECT * FROM Customers WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("city"), rs.getString("state"));
            }
        }
        return null;
    }

    public void add(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customers (id, name, city, state) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customer.getId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getCity());
            ps.setString(4, customer.getState());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Customers WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void update(Customer customer) throws SQLException {
        String sql = "UPDATE Customers SET name = ?, city = ?, state = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getCity());
            ps.setString(3, customer.getState());
            ps.setInt(4, customer.getId());
            ps.executeUpdate();
        }
    }

    public List<Customer> getAllSortedById() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customers ORDER BY id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                customers.add(new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("city"), rs.getString("state")));
            }
        }
        return customers;
    }

    public List<Customer> getAllSortedByName() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customers ORDER BY name";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                customers.add(new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("city"), rs.getString("state")));
            }
        }
        return customers;
    }
}

public class OrderDAO extends AbstractOrderDAO {
    private Connection connection;

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    public Order getById(int number) throws SQLException {
        String sql = "SELECT * FROM Orders WHERE number = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, number);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Order(rs.getInt("number"), rs.getInt("customerId"), rs.getString("description"), rs.getBigDecimal("price"));
            }
        }
        return null;
    }

    public void add(Order order) throws SQLException {
        String sql = "INSERT INTO Orders (number, customerId, description, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, order.getNumber());
            ps.setInt(2, order.getCustomerId());
            ps.setString(3, order.getDescription());
            ps.setBigDecimal(4, order.getPrice());
            ps.executeUpdate();
        }
    }

    public void delete(int number) throws SQLException {
        String sql = "DELETE FROM Orders WHERE number = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, number);
            ps.executeUpdate();
        }
    }

    public void update(Order order) throws SQLException {
        String sql = "UPDATE Orders SET customerId = ?, description = ?, price = ? WHERE number = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, order.getCustomerId());
            ps.setString(2, order.getDescription());
            ps.setBigDecimal(3, order.getPrice());
            ps.setInt(4, order.getNumber());
            ps.executeUpdate();
        }
    }

    public List<Order> getAllSortedByNumber() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders ORDER BY number";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orders.add(new Order(rs.getInt("number"), rs.getInt("customerId"), rs.getString("description"), rs.getBigDecimal("price")));
            }
        }
        return orders;
    }

    public List<Order> getAllByCustomerIdSortedByNumber(int customerId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE customerId = ? ORDER BY number";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orders.add(new Order(rs.getInt("number"), rs.getInt("customerId"), rs.getString("description"), rs.getBigDecimal("price")));
            }
        }
        return orders;
    }
}