package com.ishwari.accountapi.service.transaction;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.ishwari.accountapi.entity.Transaction;

public interface TransactionService {

	/**
	 * Return all the account's {@link Transaction}
	 *
	 * @param accountID
	 * @return a list of {@link Transaction} or an empty list
	 */
	List<Transaction> findByAccount(@NotNull Long accountID);

	/**
	 * Create a transaction on the given account for the given amount. If the
	 * account doesn't exist no transaction will be created.
	 *
	 * @param accountID
	 * @param amount
	 * @return
	 */
	Optional<Transaction> createTransaction(@NotNull Long accountID, double amount);

}
