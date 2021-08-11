package com.ishwari.accountapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ishwari.accountapi.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	    /**
	     * Return all the account's {@link Transaction}
	     *
	     * @param accountID
	     * @return a list of {@link Transaction} or an empty list
	     */
	    @Query("SELECT transaction FROM Transaction transaction WHERE transaction.accountID = :accountID")
	    List<Transaction> findByAccount(@Param("accountID") Long accountID);
	}

	
