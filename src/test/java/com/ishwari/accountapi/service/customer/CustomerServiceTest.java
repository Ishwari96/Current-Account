package com.ishwari.accountapi.service.customer;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.ishwari.accountapi.entity.Customer;
import com.ishwari.accountapi.repository.CustomerRepository;


class CustomerServiceTest {

    private CustomerServiceImpl customerService;

    private CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);

    @BeforeEach
    void setup() {
        customerService = new CustomerServiceImpl(customerRepository);
    }


    @Test
    @DisplayName("Find by id with results")
    void findByIDWithResult() {
        Customer customer = new Customer();
        customer.setId(1L);
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Optional<Customer> customerOptional = customerService.findByID(1L);
        Assertions.assertEquals(1L,customerOptional.get().getId());
    }

    @Test
    @DisplayName("Find by id with NO results")
    void findByIDWithNOResult() {
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Customer> customerOptional = customerService.findByID(1L);
        Assertions.assertFalse(customerOptional.isPresent());
    }

    @Test
    @DisplayName("checkCustomerExists with true result")
    void checkCustomerExistsOK() {
        Mockito.when(customerRepository.existsById(1L)).thenReturn(true);
        Assertions.assertTrue(customerService.checkCustomerExists(1L));
    }

    @Test
    @DisplayName("checkCustomerExists with false result")
    void checkCustomerExistsKO() {
        Mockito.when(customerRepository.existsById(1L)).thenReturn(false);
        Assertions.assertFalse(customerService.checkCustomerExists(1L));
    }
}
