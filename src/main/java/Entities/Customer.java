package Entities;
import javax.persistence.*;

@Entity
@Table(name = "Customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CustomerId")
    private Long id;
    
    @Column(name = "Name")
    private String name;
    
    @Column(name = "Password")
    private String password;
    
    @Column(name = "Email")
    private String email;
    
    @Column(name = "ContactNumber")
    private String contactNumber;

	public Customer(Long id, String name, String password, String email, String contactNumber) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.contactNumber = contactNumber;
	}

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}   
}