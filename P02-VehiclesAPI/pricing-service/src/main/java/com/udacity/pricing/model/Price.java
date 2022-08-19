package com.udacity.pricing.model;

import java.math.BigDecimal;

<<<<<<< HEAD:P02-VehiclesAPI/pricing-service/src/main/java/com/udacity/pricing/model/Price.java
=======
import javax.persistence.Column;
>>>>>>> 6f051c4d82f75b7205d648d1df1565eeb6b9c3bb:P02-VehiclesAPI/pricing-service/src/main/java/com/udacity/pricing/domain/price/Price.java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents the price of a given vehicle, including currency.
 */
@Entity
public class Price {

	@Id
<<<<<<< HEAD:P02-VehiclesAPI/pricing-service/src/main/java/com/udacity/pricing/model/Price.java
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
=======
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="CURRENCY")
>>>>>>> 6f051c4d82f75b7205d648d1df1565eeb6b9c3bb:P02-VehiclesAPI/pricing-service/src/main/java/com/udacity/pricing/domain/price/Price.java
    private String currency;
	
	@Column(name="PRICE")
    private BigDecimal price;
    
	@Column(name="VEHICLE_ID")
    private Long vehicleId;

    public Price() {
    }

    public Price(String currency, BigDecimal price, Long vehicleId) {
        this.currency = currency;
        this.price = price;
        this.vehicleId = vehicleId;
    }

    public Price(Long id, String currency, BigDecimal price, Long vehicleId) {
		super();
		this.id = id;
		this.currency = currency;
		this.price = price;
		this.vehicleId = vehicleId;
	}

	public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
