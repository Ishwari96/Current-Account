package com.ishwari.accountapi.service.account;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ishwari.accountapi.entity.Account;
import com.ishwari.accountapi.entity.Customer;
import com.ishwari.accountapi.entity.Transaction;
import com.ishwari.accountapi.exception.AccountException;
import com.ishwari.accountapi.exception.AccountNotFoundException;
import com.ishwari.accountapi.exception.CustomerNotFoundException;
import com.ishwari.accountapi.model.AccountInformation;
import com.ishwari.accountapi.model.CustomerAccountInformation;
import com.ishwari.accountapi.repository.AccountRepository;
import com.ishwari.accountapi.service.customer.CustomerService;
import com.ishwari.accountapi.service.customer.CustomerServiceImpl;
import com.ishwari.accountapi.service.transaction.TransactionService;
import com.ishwari.accountapi.service.transaction.TransactionServiceImpl;

@Service
public class AccountServiceImpl implements AccountService {
	
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerService customerService;
    
    public AccountServiceImpl(TransactionServiceImpl transactionService2, CustomerServiceImpl customerService2,
			AccountRepository accountRepository2) {
    	 this.transactionService = transactionService2;
         this.customerService = customerService2;
         this.accountRepository = accountRepository2;
	}

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
        if(!accountOptional.isPresent()){
            throw new AccountNotFoundException();
        }
        
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
        logger.info("Account-createNewAccountByCustomer: check if customer exists");
        if(!customerService.checkCustomerExists(customerID)){
            throw new CustomerNotFoundException();
        }

        logger.info("Account-createNewAccountByCustomer: create and save new account for the given customer");
        var newAccount = new Account(customerID);
        newAccount = accountRepository.save(newAccount);
        if(null == newAccount) {
            throw new AccountException("Error while saving the new account");
        }
        logger.info("Account-createNewAccountByCustomer: check if initialCredit is not 0, if true create the first transaction on the account");
        if(initialCredit != 0){
            Optional<Transaction> firstTransaction = transactionService.createTransaction(newAccount.getId(), initialCredit);
            logger.info("Account-createNewAccountByCustomer: first transaction created, update balance on account");
            newAccount.setBalance(firstTransaction.get().getAmount());
            newAccount = accountRepository.save(newAccount);
        }
        return Optional.of(newAccount);
    }

    @Override
    public Optional<CustomerAccountInformation> getCustomerAccountInfo(@NotNull Long customerID) {
        logger.info("Account-getCustomerAccountInfo: get customer informations");
        Optional<Customer> customerOptional = customerService.findByID(customerID);
        if(!customerOptional.isPresent()) {
            throw new CustomerNotFoundException();
        }
        var customerAccountInformation = new CustomerAccountInformation();
        customerAccountInformation.setCustomerName(customerOptional.get().getName());
        customerAccountInformation.setCustomerSurname(customerOptional.get().getSurname());

        logger.info("Account-getCustomerAccountInfo: get all the customer's account");
        List<Account> lstAccount = findByCustomer(customerID);

        if(!lstAccount.isEmpty()) {
            logger.info("Account-getCustomerAccountInfo: found account's customer, retrieve account information");
            List<AccountInformation> lstAccInformation = lstAccount.stream()
                      .map(account -> new AccountInformation(account))
                      .collect(Collectors.toList());
            lstAccInformation.stream().forEach(accInfo -> accInfo.setTransactionList(transactionService.findByAccount(accInfo.getAccountNumber())));
            customerAccountInformation.setAccounts(lstAccInformation);
        }else{
            logger.info("Account-getCustomerAccountInfo: no accounts found for the customer");
            customerAccountInformation.setAccounts(Collections.emptyList());
        }

        return Optional.of(customerAccountInformation);
    }
}
