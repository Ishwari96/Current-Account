package com.ishwari.accountapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ishwari.accountapi.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
