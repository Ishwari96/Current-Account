package com.ishwari.accountapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ishwari.accountapi.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Return all the customer's {@link Account}
     *
     * @param customerID
     * @return a list of {@link Account} or an empty list
     */
    @Query("SELECT account FROM Account account WHERE account.customerID = :customerID")
    List<Account> findByCustomer(@Param("customerID") Long customerID);

}