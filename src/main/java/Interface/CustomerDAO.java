package Interface;
import java.util.List;

import Entities.Customer;

public interface CustomerDAO {
    void addCustomer(Customer customer);
    Customer getCustomerByEmail(String email);
    List<Customer> getAllCustomers();
}