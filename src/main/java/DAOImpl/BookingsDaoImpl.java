package DAOImpl;

import Interface.BookingsDao;
import Entities.Bookings;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class BookingsDaoImpl implements BookingsDao {
	// SessionFactory to create sessions for database operations
	private final SessionFactory sessionFactory;
	
	// Constructor to initialize the SessionFactory
	public BookingsDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	 // Method to save a booking into the database
	@Override
	public void saveBooking(Bookings booking) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(booking);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	 // Method to delete a booking from the database based on its ID
	@Override
	public void deleteBooking(int bookingID) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Bookings booking = session.get(Bookings.class, bookingID);
			if (booking != null) {
				session.delete(booking);
				transaction.commit();
				System.out.println("Booking with ID " + bookingID + " deleted successfully.");
			} else {
				System.out.println("Booking with ID " + bookingID + " does not exist.");
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// Method to update a booking in the database
	@Override
	public void updateBooking(Bookings booking) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.update(booking);
			transaction.commit();
			System.out.println("Booking updated successfully.");
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// Method to retrieve a booking from the database based on its ID
	@Override
	public Bookings getBookingById(int bookingId) {
		Session session = sessionFactory.openSession();
		try {
			return session.get(Bookings.class, bookingId);
		} finally {
			session.close();
		}
	}

	// Main method to run the program
	public static void main(String[] args) {
		// Load Hibernate configuration
		Configuration configuration = new Configuration().configure();
		try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
			BookingsDaoImpl dao = new BookingsDaoImpl(sessionFactory);// Create DAO instance
			Scanner scanner = new Scanner(System.in);// Scanner for user input
			boolean running = true;

			while (running) {
				System.out.println("Select operation:");

				System.out.println("1. Delete Booking");
				System.out.println("2. Update Booking");
				System.out.println("3. Exit");

				int choice = scanner.nextInt();// Get user choice
				scanner.nextLine(); // Consume newline

				switch (choice) {

				case 1:
					System.out.println("Enter Booking ID to delete:");
					int bookingIdToDelete = scanner.nextInt();// Get booking ID to delete
					dao.deleteBooking(bookingIdToDelete);// Delete booking
					break;
				case 2:
					System.out.println("Enter Booking ID to update:");
					int bookingIdToUpdate = scanner.nextInt();// Get booking ID to update
					scanner.nextLine(); // Consume newline
					// Fetch existing booking based on the ID
					Bookings existingBooking = dao.getBookingById(bookingIdToUpdate);
					if (existingBooking != null) {
						// Your update logic here
						System.out.println("Enter new total amount:");
						double newTotalAmount = scanner.nextDouble();// Get new total amount
						existingBooking.setTotalAmount(newTotalAmount);// Update booking with new total amount
						dao.updateBooking(existingBooking);// Update booking in the database
					} else {
						System.out.println("Booking with ID " + bookingIdToUpdate + " not found.");
					}
					break;
				case 3:
					running = false;// Exit the program
					break;
				default:
					System.out.println("Invalid choice.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}