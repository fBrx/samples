package com.github.fbrx.samples.juel.model;

import java.util.Date;

/**
 * Dummy contract class for unit tests.
 */
public class Contract {

	private double amount;
	private Date createdOn;
	private Customer customer;
	private Date expiresOn;
	private String type;

	public Contract(Customer customer, String type, double amount, Date createdOn, Date expiresOn) {
		super();
		this.customer = customer;
		this.type = type;
		this.amount = amount;
		this.createdOn = createdOn;
		this.expiresOn = expiresOn;
	}

	public double getAmount() {
		return this.amount;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public Date getExpiresOn() {
		return this.expiresOn;
	}

	public String getType() {
		return this.type;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setExpiresOn(Date expiresOn) {
		this.expiresOn = expiresOn;
	}

	public void setType(String type) {
		this.type = type;
	}

}
