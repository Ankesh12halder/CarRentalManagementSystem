package Entities;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Payments")
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentID")
    private int paymentID;

    @ManyToOne
    @JoinColumn(name = "BookingID")
    private Bookings booking;

    @Column(name = "Amount")
    private double amount;

    @Column(name = "PaymentDate")
    private Date paymentDate;

    @Column(name = "PaymentMethod")
    private String paymentMethod;

    @Column(name = "TransactionID")
    private String transactionID;

	public Payments(int paymentID, Bookings booking, double amount, Date paymentDate, String paymentMethod,
			String transactionID) {
		super();
		this.paymentID = paymentID;
		this.booking = booking;
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.paymentMethod = paymentMethod;
		this.transactionID = transactionID;
	}

	public Payments() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	// Getters and setters
	public int getPaymentID() {
		return paymentID;
	}

	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}

	public Bookings getBooking() {
		return booking;
	}

	public void setBooking(Bookings booking) {
		this.booking = booking;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

    
    
}