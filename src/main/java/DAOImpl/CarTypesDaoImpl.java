package DAOImpl;

import java.util.List;
import java.util.Scanner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import Entities.CarTypes;

public class CarTypesDaoImpl {
	private SessionFactory factory;

	public CarTypesDaoImpl(SessionFactory factory) {
		this.factory = factory;
	}

	// Main method for interacting with RoomTypes
	public static void main(String[] args) {
		// Configuration setup
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		SessionFactory factory = config.buildSessionFactory();
		// Creating CarTypesDaoImpl instance
		CarTypesDaoImpl carTypesDao = new CarTypesDaoImpl(factory);
		Scanner scanner = new Scanner(System.in);

		// Menu loop
		while (true) {
			System.out.println("Select operation:");
			System.out.println("1. Add Car Type");
			System.out.println("2. Read");
			System.out.println("3. Update");
			System.out.println("4. Delete");
			System.out.println("5. Exit");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			switch (choice) {
			case 1:
				carTypesDao.addRoomType(scanner);
				break;
			case 2:
				carTypesDao.readAllCarTypes();
				break;
			case 3:
				carTypesDao.updateCarType(scanner);
				break;
			case 4:
				carTypesDao.deleteCarType(scanner);
				break;
			case 5:
				factory.close();
				scanner.close();
				System.exit(0);
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	// Method to add a new Car Type
	public void addRoomType(Scanner scanner) {
		// Taking input from user
		System.out.println("Enter Car Type Name:");
		String typeName = scanner.nextLine();
		System.out.println("Enter Car Purpose:");
		String purpose = scanner.nextLine();
		System.out.println("Enter Rent Price:");
		int rentPrice = scanner.nextInt();
		scanner.nextLine(); // Consume newline
		System.out.println("Enter Amenities:");
		String amenities = scanner.nextLine();

		// Database interaction
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			CarTypes carType = new CarTypes();
			carType.setTypeName(typeName);
			carType.setPurpose(purpose);
			carType.setRentPrice(rentPrice);
			carType.setAmenities(amenities);
			session.save(carType);
			tx.commit();
			System.out.println("Car Type added successfully.");
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// Method to read all Room Types
	public List<CarTypes> readAllCarTypes() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<CarTypes> carTypes = null; // Initialize the list

		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("from CarTypes");
			carTypes = query.list(); // Assign the query result to the list
			tx.commit();

			 // Output the Room Types
			if (carTypes.isEmpty()) {
				System.out.println("No Room Types found.");
			} else {
				for (CarTypes carType : carTypes) {
					System.out.println(carType);
				}
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

		return carTypes; // Return the list
	}

	// Method to update a CAr Type
	public void updateCarType(Scanner scanner) {
		System.out.println("Enter Car Type ID to update:");
		int typeId = scanner.nextInt();
		scanner.nextLine(); // Consume newline
		CarTypes carType = getCarTypeById(typeId);

		if (carType != null) {
			// Updating the Room Type
			System.out.println("Enter new Room Type Name:");
			carType.setTypeName(scanner.nextLine());
			System.out.println("Enter new Room Purpose:");
			carType.setPurpose(scanner.nextLine());
			System.out.println("Enter new Rent Price:");
			carType.setRentPrice(scanner.nextInt());
			scanner.nextLine(); // Consume newline
			System.out.println("Enter new Amenities:");
			carType.setAmenities(scanner.nextLine());

			 // Database interaction
			Session session = factory.openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				session.update(carType);
				tx.commit();
				System.out.println("Car Type updated successfully.");
			} catch (Exception e) {
				if (tx != null) {
					tx.rollback();
				}
				e.printStackTrace();
			} finally {
				session.close();
			}
		} else {
			System.out.println("Car Type not found.");
		}
	}

	// Method to delete a Car Type
	public void deleteCarType(Scanner scanner) {
		// Input from user
		System.out.println("Enter Car Type ID to delete:");
		int typeId = scanner.nextInt();
		scanner.nextLine(); // Consume newline
		CarTypes roomType = getCarTypeById(typeId);

		if (roomType != null) {
			// Database interaction
			Session session = factory.openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				session.delete(roomType);
				tx.commit();
				System.out.println("Car Type deleted successfully.");
			} catch (Exception e) {
				if (tx != null) {
					tx.rollback();
				}
				e.printStackTrace();
			} finally {
				session.close();
			}
		} else {
			System.out.println("Car Type not found.");
		}
	}
	

	// Method to get a Room Type by its ID
	public CarTypes getCarTypeById(int typeId) {
		Session session = factory.openSession();
		Transaction tx = null;
		CarTypes carType = null;
		try {
			tx = session.beginTransaction();
			carType = session.get(CarTypes.class, typeId);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return carType;
	}

}