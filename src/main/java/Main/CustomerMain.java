package Main;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import DAOImpl.CustomerDAOImpl;
import Entities.Customer;
import Entities.Payments;
import Interface.CustomerDAO;
import DAOImpl.CarTypesDaoImpl;
import Entities.CarTypes;
import Interface.CarTypesDao;
import DAOImpl.BookingsDaoImpl;
import Entities.Bookings;
import Interface.BookingsDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class CustomerMain {

	public static void main(String[] args) {
		
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.openSession();
		
		// Initialize DAOs
		CustomerDAO customerDAO = new CustomerDAOImpl(factory);
		CarTypesDaoImpl carTypesDaoImpl = new CarTypesDaoImpl(factory);

		// Scanner for user input
		Scanner scanner = new Scanner(System.in);

		// Display menu
		System.out.println("Welcome to the Customer Management System!");
		System.out.println("1. Register");
		System.out.println("2. Login");
		System.out.println("3. Exit");

		// Main menu loop
		boolean exit = false;
		while (!exit) {
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			switch (choice) {
			case 1:
				registerCustomer(customerDAO, scanner);
				break;
			case 2:
				loginCustomer(customerDAO, scanner, carTypesDaoImpl);
				break;
			case 3:
				exit = true;
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}

		// Clean up resources
        session.close();
		factory.close();
		scanner.close();
	}

	 // Method for customer registration
	public static void registerCustomer(CustomerDAO customerDAO, Scanner scanner) {
		System.out.print("Enter your name: ");
		String name = scanner.nextLine();

		System.out.print("Enter your email: ");
		String email = scanner.nextLine();

		System.out.print("Enter your password: ");
		String password = scanner.nextLine();

		System.out.print("Enter your contact number: ");
		String contactNumber = scanner.nextLine();

		Customer customer = new Customer();
		customer.setName(name);
		customer.setEmail(email);
		customer.setPassword(password);
		customer.setContactNumber(contactNumber);

		customerDAO.addCustomer(customer);

		System.out.println("Registration successful!");
	}

	 // Method for customer login
	private static void loginCustomer(CustomerDAO customerDAO, Scanner scanner, CarTypesDaoImpl carTypesDaoImpl) {
		System.out.print("Enter your email: ");
		String email = scanner.nextLine();

		System.out.print("Enter your password: ");
		String password = scanner.nextLine();

		Customer customer = customerDAO.getCustomerByEmail(email);
		if (customer != null && customer.getPassword().equals(password)) {
			System.out.println("Login successful! Welcome, " + customer.getName() + "!");

			// Get All Room Types
			List<CarTypes> allCarTypes = carTypesDaoImpl.readAllCarTypes();
			if (!allCarTypes.isEmpty()) {
				System.out.println("All Car Types:");
				for (CarTypes carType : allCarTypes) {
					System.out.println(carType.getTypeID() + ": " + carType.getTypeName() + ": "
							+ carType.getPurpose() + ": " + carType.getRentPrice() + ": " + carType.getAmenities());
				}
				System.out.print("Enter the ID of the car type you want to book: ");
				int carTypeID = scanner.nextInt();
				scanner.nextLine(); // Consume newline

				System.out.print("Enter check-in date (yyyy-MM-dd): ");
				String checkInDateStr = scanner.nextLine();
				System.out.print("Enter check-out date (yyyy-MM-dd): ");
				String checkOutDateStr = scanner.nextLine();

				// Convert check-in and check-out date strings to Date objects
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date checkInDate = null;
				Date checkOutDate = null;
				try {
					checkInDate = dateFormat.parse(checkInDateStr);
					checkOutDate = dateFormat.parse(checkOutDateStr);
				} catch (ParseException e) {
					System.out.println("Invalid date format. Please enter dates in yyyy-MM-dd format.");
					return; // Exit method if parsing fails
				}

				// Calculate total amount based on car price and duration 
				CarTypes selectedCarType = carTypesDaoImpl.getCarTypeById(carTypeID);
				double totalAmount = calculateTotalAmount(selectedCarType.getRentPrice(), checkInDate, checkOutDate);

				// Create a new booking object
				Bookings booking = new Bookings();
				booking.setCustomer(customer);
				booking.setTypeID(selectedCarType);
				booking.setCheckInDate(checkInDate);
				booking.setCheckOutDate(checkOutDate);
				booking.setTotalAmount(totalAmount);
				booking.setBookingStatus("DONE"); // Set initial booking status

				// Save the booking to the database
				saveBooking(booking);

				System.out.println("Booking successful!");
			} else {
				System.out.println("No car types found.");
			}
		} else {
			System.out.println("Invalid email or password. Please try again.");
		}
	}

	// Method to calculate total booking amount
	private static double calculateTotalAmount(double carPricePerNight, Date checkInDate, Date checkOutDate) {
		
		long durationInMillis = checkOutDate.getTime() - checkInDate.getTime();
		long durationInDays = TimeUnit.MILLISECONDS.toDays(durationInMillis);

		// Calculate total amount based on room price per night and duration of stay
		return carPricePerNight * durationInDays;
	}

	// Method to save booking and payment details
	private static void saveBooking(Bookings booking) {
		Scanner scanner = new Scanner(System.in);
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			// Ensure that the CarTypes instance referenced by the booking is persisted
			session.saveOrUpdate(booking.getTypeID());

			// Calculate total amount based on room price and duration of stay
			double totalAmount = calculateTotalAmount(booking.getTypeID().getRentPrice(), booking.getCheckInDate(),
					booking.getCheckOutDate());
			System.out.println("Total amount to pay: " + totalAmount);

			// Save the booking entity
			session.save(booking);
			// Prompt user for payment details
			System.out.println("Please enter payment details:");
			System.out.print("Enter payment amount: ");
			double amount = scanner.nextDouble();
			scanner.nextLine(); // Consume newline
			System.out.print("Enter payment date (yyyy-MM-dd): ");
			String paymentDateStr = scanner.nextLine();
			System.out.print("Enter payment method: ");
			String paymentMethod = scanner.nextLine();
			System.out.print("Enter transaction ID: ");
			String transactionID = scanner.nextLine();

			// Convert payment date string to Date object
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date paymentDate = dateFormat.parse(paymentDateStr);

			// Create a new Payments object
			Payments payment = new Payments();
			payment.setBooking(booking);
			payment.setAmount(amount);
			payment.setPaymentDate(paymentDate);
			payment.setPaymentMethod(paymentMethod);
			payment.setTransactionID(transactionID);

			// Save the payment to the database
			session.save(payment);

			tx.commit();
			System.out.println("Booking and payment successful!");
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
	}
}