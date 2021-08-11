package com.ishwari.accountapi.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.ishwari.accountapi.entity.Account;
import com.ishwari.accountapi.entity.Transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountInformation {

    /**
     * Account number
     */
    private Long accountNumber;

    /**
     * Balance of the account
     */
    private double balance;

    /**
     * Account transaction list
     */
    private List<Transaction> transactionList;

    public AccountInformation(){}

    public AccountInformation(@NotNull Account account) {
        this.accountNumber = account.getId();
        this.balance = account.getBalance();
    }
}
