package dataBaseReference.System;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import dataBaseReference.DAO.AbstractCustomerDAO;
import dataBaseReference.DAO.AbstractOrderDAO;
import dataBaseReference.DAO.Customer_DB_DAO;
import dataBaseReference.DAO.Customer_Mem_DAO;
import dataBaseReference.DAO.Order_DB_DAO;
import dataBaseReference.DAO.Order_Mem_DAO;
import dataBaseReference.DTO.Customer;
import dataBaseReference.DTO.Orders;
import dataBaseReference.RDBMS.MariaDBConnection;
import dataBaseReference.RDBMS.MemoryDBConnection;
import dataBaseReference.Menu.MenuFuncs;

public class Controller
   {
   public Scanner scanner = new Scanner(System.in);
   public AbstractCustomerDAO customerDAO        = null;
   public AbstractOrderDAO    ordersDAO          = null;
   private MariaDBConnection   myDBConnection     = null;
   private MemoryDBConnection  memoryDBConnection = null;
   public DataBaseType        selectedDataBase   = DataBaseType.INVALID;

   public AbstractCustomerDAO getCustomerDAO (){
      return customerDAO;
   }

   public AbstractOrderDAO getOrdersDAO() {
      return ordersDAO;
   }

   public Controller(DataBaseType selectedDataBase)
      {
      super();
      this.selectedDataBase = selectedDataBase;
      }

   public void openConnection()
      {
      switch (selectedDataBase)
         {
         case MEMORY:
            {
            memoryDBConnection = new MemoryDBConnection();
            this.customerDAO = new Customer_Mem_DAO(memoryDBConnection);
            this.ordersDAO = new Order_Mem_DAO(memoryDBConnection);
            }
            break;
         case MARIADB:
            {
            myDBConnection = new MariaDBConnection();
            this.customerDAO = new Customer_DB_DAO(myDBConnection.getConnection());
            this.ordersDAO = new Order_DB_DAO(myDBConnection.getConnection());
            }
            break;
         default:
            {
            System.out.println("Database selection not supported.");
            throw new InvalidParameterException("Selector is unspecified: " + selectedDataBase);
            }
         }
      }

   private void closeConnection()
      {
      if (myDBConnection != null)
         {
         myDBConnection.close();
         }
      if (memoryDBConnection != null)
         {
         memoryDBConnection.close();
         }
      }
   
      //arrumar
   public void insertOrder(int id, int orderNumber, BigDecimal orderPrice, String Description) {
      try {
         Customer customer = customerDAO.getCustomerById(id);
                
         if (customer != null) {
               Orders order = new Orders();
               order.setCustomerId(customer.getId());
            
               
               order.setNumber(orderNumber);
               order.setPrice(orderPrice);
               order.setDescription(Description);

               ordersDAO.addOrder(order);

         } else {
            System.out.println("ERROR: No match found");
         }
      }
      catch (SQLException e) {
         System.out.println("Exception launched. Program aborted.");
         System.out.println(e.getMessage());
         System.exit(1);
      }

   }

   public void deleteOrder(int number) {
      try {
         Orders order = ordersDAO.getOrderByNumber(number);

         if (order != null) {
               ordersDAO.deleteOrder(order.getNumber());
         } else {
            System.out.println("ERROR: No match found");
         }
      }
      catch (SQLException e) {
         System.out.println("Exception launched. Program aborted.");
         System.out.println(e.getMessage());
         System.exit(1);
      }
   }

   public void insertOrderByNumber(int number) {
      try {
         Orders order = ordersDAO.getOrderByNumber(number);
         if (order != null) {
            Customer customer = customerDAO.getCustomerById(order.getCustomerId());
            System.out.println("Customer: \n-Id: " + customer.getId() + "\n-Name: " + customer.getName() + "\n-Order: \n" + order);
         } else {
            System.out.println("ERROR: No match found");
         }
      } 
      catch (SQLException e) {
         System.out.println("Exception launched. Program aborted.");
         System.out.println(e.getMessage());
         System.exit(1);
      }

   }
   public void insertCustomer(int id, String Name, String City, String State) {
      try {
         Customer customer = new Customer();
         customer.setId(id);
         customer.setName(Name);
         customer.setCity(City);
         customer.setState(State);
         customerDAO.addCustomer(customer);
      }
      catch (SQLException e) {
         System.out.println("Exception launched. Program aborted.");
         System.out.println(e.getMessage());
         System.exit(1);
      }
   }

   public void requestCustomerByID(int Id) {
      try
      {
         int customerId = Id; // Replace with the desired customer ID
         Customer customer = customerDAO.getCustomerById(customerId);
         if (customer != null)
            {
               System.out.println("Customer Name: " + customer.getName());
               System.out.println("City: " + customer.getCity());
               System.out.println("State: " + customer.getState());
            }
         else
            {
            System.out.println("Customer not found.");
            }
      }
      catch (SQLException e)
      {
         System.err.println("Error retrieving customer: " + e.getMessage());
      }
   }

   public void requestCustomerByName () {
      try {
         List<Customer> customers = customerDAO.getAllCustomersOrderedByName();
         if (!customers.isEmpty()) {
            System.out.println("Customers: \n");
            for (Customer customer1 : customers) {
               System.out.println(customer1);
            }
         } else {
            System.out.println("Match not found.");
         }
      }
      catch (SQLException e) {
         System.out.println("Exception launched. Program aborted.");
         System.out.println(e.getMessage());
         System.exit(1);
      }
   }

   public void requestAllCustomersById () {
      try {
         List<Customer> customers = customerDAO.getAllCustomersOrderedById();
         for (Customer customer : customers) {
            System.out.println(customer);
         }
      }
      catch (SQLException e) {
         System.out.println("Exception launched. Program aborted.");
         System.out.println(e.getMessage());
         System.exit(1);
      }
   }

   public void deleteCustomerByID(int id) {
      try {
         Customer customerToDelete = customerDAO.getCustomerById(id);
                  
         if (customerToDelete != null) {
            List<Orders> orders = ordersDAO.getOrdersByCustomerId(customerToDelete.getId());
            if (!orders.isEmpty()) {
               for (Orders order : orders) {
                     ordersDAO.deleteOrder(order.getNumber());
               }
            }
            customerDAO.deleteCustomer(customerToDelete.getId());
            System.out.println("Customer successfully deleted");
         } else {
            System.out.println("Deleting of Costumer failed. Could not find match.");
         }
      }
      catch (SQLException e) {
         System.out.println("Exception launched. Program aborted.");
         System.out.println(e.getMessage());
         System.exit(1);
      }
   }

   public void requestAllCostumers() {
      try
         {
         List<Customer> customers = customerDAO.getAllCustomersOrderedByName();
         for (Customer customer : customers)
            {
               System.out.println(customer.getName());
            }
         }
      catch (SQLException e)
         {
         System.err.println("Error retrieving customers: " + e.getMessage());
         }
   }


   public void requestAllOrdersByNumber() {
      try {
         List<Orders> order = ordersDAO.getAllOrdersOrderedByNumber();
         for (Orders order1 : order) {
             System.out.println(order);
         }
      }
      catch (SQLException e) {
         System.out.println("Exception launched. Program aborted.");
         System.out.println(e.getMessage());
         System.exit(1);
      }
   }

   public void requestAllOrdersByCustomerId(int id) {
      try {
         List<Orders> order = ordersDAO.getAllOrdersByCustomerId(id);
         for (Orders order1 : order) {
             System.out.println(order);
         }
      }
      catch (SQLException e) {
         System.out.println("Exception launched. Program aborted.");
         System.out.println(e.getMessage());
         System.exit(1);
      }
   }

   public void requestAllOrdersByCustomerIdAndNumber(int id) {
      try {
         List<Orders> order = ordersDAO.getAllOrdersByCustomerIdByNumber(id);
         for (Orders order1 : order) {
             System.out.println(order);
         }
      }
      catch (SQLException e) {
         System.out.println("Exception launched. Program aborted.");
         System.out.println(e.getMessage());
         System.exit(1);
      } 
   }


   private void updateData()
      {
      // Single Customer
      try
         {
         int customerId = 1; // Replace with the desired customer ID
         Customer customer = customerDAO.getCustomerById(customerId);

         if (customer != null)
            {
            System.out.println("Customer Name: " + customer.getName());
            System.out.println("City: " + customer.getCity());
            System.out.println("State: " + customer.getState());

            customer.setCity("Limeira");
            customer.setState("SP");
            customerDAO.updateCustomer(customer);
            }
         else
            {
            System.out.println("Customer not found.");
            }
         }
      catch (SQLException e)
         {
         System.err.println("Error retrieving customer: " + e.getMessage());
         }

      }

   private void deleteData()
      {
      try
         {
         int orderNumber = 1; // Replace with the desired order number
         Orders order = ordersDAO.getOrderByNumber(orderNumber);

         if (order != null)
            {
            System.out.println("Order Number: " + order.getNumber());
            System.out.println("Customer ID: " + order.getCustomerId());
            System.out.println("Description: " + order.getDescription());
            System.out.println("Price: " + order.getPrice());

            ordersDAO.deleteOrder(order.getNumber());
            }
         else
            {
            System.out.println("Order not found.");
            }
         }
      catch (SQLException e)
         {
         System.err.println("Error retrieving order: " + e.getMessage());
         }
      }

   private void deleteAllData()
      {
      System.out.println("Deleting all data");

      try
         {
         ordersDAO.deleteAllOrders();
         customerDAO.deleteAllCustomers();
         }
      catch (SQLException e)
         {
         System.err.println("Error retrieving order: " + e.getMessage());
         }
      }

}
      
