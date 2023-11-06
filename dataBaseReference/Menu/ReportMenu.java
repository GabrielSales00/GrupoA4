package dataBaseReference.Menu;

import dataBaseReference.DAO.AbstractCustomerDAO;
import dataBaseReference.DAO.AbstractOrderDAO;
import dataBaseReference.DTO.Customer;
import dataBaseReference.DTO.Orders;
import dataBaseReference.System.Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ReportMenu extends MenuFuncs {  

        public Controller currentDB;

        public ReportMenu(Controller controller) {

            currentDB = controller;
    
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
                    currentDB.requestAllCustomersById();
                    break;
                case 2:
                    currentDB.requestCustomerByName();
                    break;
                case 3:
                    currentDB.req
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
        

