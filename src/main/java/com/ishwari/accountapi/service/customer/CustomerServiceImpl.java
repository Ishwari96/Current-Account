package com.ishwari.accountapi.service.customer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ishwari.accountapi.entity.Customer;
import com.ishwari.accountapi.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

		@Autowired
	    private CustomerRepository customerRepository;

	    public CustomerServiceImpl(CustomerRepository customerRepository2) {
	    	this.customerRepository = customerRepository2;
		}

		@Override
	    public Optional<Customer> findByID(Long customerID) {
	        return customerRepository.findById(customerID);
	    }

	    @Override
	    public boolean checkCustomerExists(Long customerID) {
	        return customerRepository.existsById(customerID);
	    }
}
