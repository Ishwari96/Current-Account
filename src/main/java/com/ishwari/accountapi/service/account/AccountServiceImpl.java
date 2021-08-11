package com.ishwari.accountapi.service.account;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ishwari.accountapi.entity.Account;
import com.ishwari.accountapi.entity.Transaction;
import com.ishwari.accountapi.model.AccountInformation;
import com.ishwari.accountapi.repository.AccountRepository;
import com.ishwari.accountapi.service.transaction.TransactionService;

@Service
public class AccountServiceImpl implements AccountService {
	
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    @Override
    public List<Account> findByCustomer(@NotNull Long customerID) {
        return accountRepository.findByCustomer(customerID);
    }

    @Override
    public boolean checkAccountExists(@NotNull Long accountID) {
        return accountRepository.existsById(accountID);
    }

    @Override
    @Transactional
    public Account saveAccount(@NotNull Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Optional<AccountInformation> findInformationByID(@NotNull Long accountID) {
        logger.info("Account-findInformationByID: check if the account exists");
        Optional<Account> accountOptional = accountRepository.findById(accountID);
        logger.info("Account-findInformationByID: Create AccountInformation with the account informations");
        var accountInformation = new AccountInformation();
        accountInformation.setBalance(accountOptional.get().getBalance());
        accountInformation.setAccountNumber(accountOptional.get().getId());

        logger.info("Account-findInformationByID: load all the transactions of the account");
        accountInformation.setTransactionList(transactionService.findByAccount(accountID));
        return Optional.of(accountInformation);
    }

    @Override
    @Transactional
    public Optional<Account> createNewAccountByCustomer(@NotNull Long customerID, double initialCredit) {
        logger.info("Account-createNewAccountByCustomer: create and save new account for the given customer");
        var newAccount = new Account(customerID);
        newAccount = accountRepository.save(newAccount);
        logger.info("Account-createNewAccountByCustomer: check if initialCredit is not 0, if true create the first transaction on the account");
        if(initialCredit != 0){
            Optional<Transaction> firstTransaction = transactionService.createTransaction(newAccount.getId(), initialCredit);
            logger.info("Account-createNewAccountByCustomer: first transaction created, update balance on account");
            newAccount.setBalance(firstTransaction.get().getAmount());
            newAccount = accountRepository.save(newAccount);
        }
        return Optional.of(newAccount);
    }

}
