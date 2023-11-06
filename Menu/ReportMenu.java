package dataBaseReference.Menu;

import dataBaseReference.DAO.AbstractCustomerDAO;
import dataBaseReference.DAO.AbstractOrderDAO;
import dataBaseReference.DTO.Customer;
import dataBaseReference.DTO.Orders;

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
        public void showMenu() {
            System.out.println("\nAções de relatórios:");
    
            showOptions();
    
            int option = askOption();
    
            switch (option) {
                case 1:
                    List<Customer> customers = customerDAO.getCostumersSortedById();
                    for (Customer customer : customers) {
                        System.out.println(customer);
                    }
                    break;
                case 2:
                    List<Customer> customers = customerDAO.getAllCustomersOrderedByName();
                    for (Customer customer : customers) {
                        System.out.println(customer);
                    }
                    break;
                case 3:
                    List<Orders> orders = orderDAO.getOrderByNumber();
                    for (Orders order : orders) {
                        System.out.println(order);
                    }
                    break;
                case 4 :
                    List<Customer> customers = customerDAO.getAllSortedByName();
                    for (Customer customer : customers) {
                        System.out.println(customer);
                        List<Order> orders = orderDAO.getAllByCustomerIdSortedByNumber(customer.getId());
                        for (Order order : orders) {
                            System.out.println(order);
                        }
                    }
                    break;
                if (option != 5) {
                    this.showMenu();
                }
            }
        }
}
        

