package DAOImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import Entities.Customer;
import Interface.CustomerDAO;

import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    private final SessionFactory sessionFactory;

    public CustomerDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Method for adding a new customer to the database
    @Override
    public void addCustomer(Customer customer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(customer);// Saving the customer object to the database
            transaction.commit();// Committing the transaction            		
        }
    }

    // Method for retrieving a customer by their email
    @Override
    public Customer getCustomerByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Customer WHERE email = :email", Customer.class)
                    .setParameter("email", email)
                    .uniqueResult();// Getting a single result based on the email parameter
        }
    }

     // Method for retrieving all customers from the database
    @Override
    public List<Customer> getAllCustomers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Customer", Customer.class).list();// Getting a list of all customers
        
        }
    }
}