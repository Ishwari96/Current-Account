package com.ishwari.accountapi.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Transaction extends BaseTransactionalEntity{

	private static final long serialVersionUID = 1L;

	/**
	 * Account associated to this transaction
	 */
	@NotNull
	private Long accountID;

	/**
	 * Amount associated to this transaction, can be positive-negative-zero
	 */
	@NotNull
	private double amount;

	/**
	 * No-args constructor
	 */
	public Transaction() {
		super();
	}

	/**
	 * Create a transaction for the provided account and set the amount to
	 * transactionAmount
	 *
	 * @param accountID
	 * @param transactionAmount
	 */
	public Transaction(final Long accountID, final double transactionAmount) {
		this.accountID = accountID;
		this.amount = transactionAmount;
	}

}
