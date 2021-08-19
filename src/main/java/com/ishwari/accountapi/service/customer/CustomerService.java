package com.ishwari.accountapi.service.customer;

import java.util.Optional;

import com.ishwari.accountapi.entity.Customer;

public interface CustomerService {

	/**
	 * Return {@link Customer} for the given customer ID
	 *
	 * @param customerID
	 * @return Optional of {@link Customer}
	 */
	public Optional<Customer> findByID(Long customerID);

	/**
	 * Check if a customer with the given ID exists.
	 *
	 * @param customerID
	 * @return
	 */
	public boolean checkCustomerExists(Long customerID);

}
