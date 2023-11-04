import java.util.*;

/*import  insira as classes corretas aqui */

public class MemoryDBConnection {
    private ArrayList<Customer> customersDB;
    private ArrayList<Orders> ordersDB;

    public MemoryDBConnection() {
        super();
        customerDB = new ArrayList<>();
        ordersDB =  new ArrayList <> ();
    }

    public ArrayList<Customer> getCustomerList()
      {
      return (customersDB);
      }
   public ArrayList<Orders> getOrderList()
      {
      return (ordersDB);
      }

   public void close()
      {
      customersDB.clear();
      customersDB = null;
      ordersDB.clear();
      ordersDB = null;
      }

}
