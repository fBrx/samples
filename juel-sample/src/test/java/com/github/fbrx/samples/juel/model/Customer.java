package com.github.fbrx.samples.juel.model;

import java.util.Collection;

/**
 * Dummy customer class for unit tests.
 */
public class Customer {

	private int age;
	private Collection<Contract> contracts;
	private String firstname;
	private String lastname;

	public Customer(String firstname, String lastname, int age, Collection<Contract> contracts) {
		super();
		this.age = age;
		this.contracts = contracts;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public int getAge() {
		return this.age;
	}

	public Collection<Contract> getContracts() {
		return this.contracts;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setContracts(Collection<Contract> contracts) {
		this.contracts = contracts;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

}
