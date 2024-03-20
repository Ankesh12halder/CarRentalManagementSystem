package Entities;

import javax.persistence.*;

@Entity
@Table(name = "RoomTypes")
public class CarTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TypeID")
    private int typeID;

    @Column(name = "TypeName")
    private String typeName;

    @Column(name = "Purpose")
    private String purpose;
    
    @Column(name = "RentPrice")
    private int rentPrice;


    @Column(name = "Amenities")
    private String amenities;

	public CarTypes(int typeID, String typeName, String purpose,int rentPrice, String amenities) {
		super();
		this.typeID = typeID;
		this.typeName = typeName;
		this.purpose = purpose;	
		this.rentPrice=rentPrice;
		this.amenities = amenities;
	}

	public CarTypes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getTypeID() {
		return typeID;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}	

	public int getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(int rentPrice) {
		this.rentPrice = rentPrice;
	}

	public String getAmenities() {
		return amenities;
	}

	public void setAmenities(String amenities) {
		this.amenities = amenities;
	}

	

	
    
}
