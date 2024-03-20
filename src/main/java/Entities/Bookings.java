package Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Bookings")
public class Bookings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "BookingID")
//    @OneToOne(cascade = CascadeType.ALL)
    private int bookingID;

    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "RoomTypeID")
    private CarTypes typeID;

    @Column(name = "CheckInDate")
    private Date checkInDate;

    @Column(name = "CheckOutDate")
    private Date checkOutDate;

    @Column(name = "TotalAmount")
    private double totalAmount;

    @Column(name = "BookingStatus")
    private String bookingStatus;

	public Bookings(int bookingID, Customer customer, CarTypes typeID, Date checkInDate, Date checkOutDate,
			double totalAmount, String bookingStatus) {
		super();
		this.bookingID = bookingID;
		this.customer = customer;
		this.typeID = typeID;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.totalAmount = totalAmount;
		this.bookingStatus = bookingStatus;
	}

	public Bookings() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getBookingID() {
		return bookingID;
	}

	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CarTypes getTypeID() {
		return typeID;
	}

	public void setTypeID(CarTypes typeID) {
		this.typeID = typeID;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	

    
}
