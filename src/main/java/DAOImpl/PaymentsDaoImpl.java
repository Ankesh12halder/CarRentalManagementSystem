package DAOImpl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import Entities.Payments;
import Interface.PaymentsDao;

import java.util.List;

public class PaymentsDaoImpl implements PaymentsDao {
	private final SessionFactory factory;

	public PaymentsDaoImpl(SessionFactory factory) {
		this.factory = factory;
	}

	// Method for adding a new payment to the database
	public void addPayment(Payments payment) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(payment);// Saving the payment object to the database
			tx.commit();// Committing the transaction
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// Method for retrieving all payments from the database
	@SuppressWarnings("deprecation")
	public List<Payments> getAllPayments() {
		Session session = factory.openSession();
		try {
			@SuppressWarnings("deprecation")
			Query<Payments> query = session.createQuery("FROM Payments", Payments.class);
			return query.list();// Getting a list of all payments
		} finally {
			session.close();
		}
	}

	// Method for retrieving a payment by its ID
	public Payments getPaymentById(int paymentID) {
		Session session = factory.openSession();
		try {
			// Getting the payment object by its ID
			return session.get(Payments.class, paymentID);
		} finally {
			session.close();
		}
	}

	// Method for updating a payment in the database
	public void updatePayment(Payments payment) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(payment);// Updating the payment object in the database
			tx.commit();// Committing the transaction
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// Method for deleting a payment from the database
	public void deletePayment(int paymentID) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Payments payment = session.get(Payments.class, paymentID);
			if (payment != null) {
				session.delete(payment);// Deleting the payment object from the database
			}
			tx.commit();// Committing the transaction
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}