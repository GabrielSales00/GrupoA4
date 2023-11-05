import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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