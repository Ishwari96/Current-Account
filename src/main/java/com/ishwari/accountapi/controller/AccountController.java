package com.ishwari.accountapi.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ishwari.accountapi.entity.Account;
import com.ishwari.accountapi.model.AccountInformation;
import com.ishwari.accountapi.service.account.AccountService;


@RestController
@RequestMapping("api/v1/account")
public class AccountController {
	
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;


    /**
     * Web service endpoint to create a new account for the given customer.
     * If initial credit is not 0 an transaction will be sent to the new account.
     * Account balance will be updated to initialCredit.
     *
     * @param customerID
     * @param initialCredit
     * @return AccountInformationDTO and an HttpStatus code CREATED if successful
     */
    @PostMapping("/create-account")
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<AccountInformation> createAccount(@RequestParam(value = "customerID") final Long customerID,
                                               @RequestParam(value = "initialCredit") final double initialCredit) {
        logger.info("AccountController-createAccount: create new account for the given customer id {}",customerID);
        Optional<Account> account = accountService.createNewAccountByCustomer(customerID, initialCredit);
        logger.info("AccountController-createAccount: retrieve the account information class for the account created {}",account.get().getId());
        Optional<AccountInformation> accountInformationOpt = accountService.findInformationByID(account.get().getId());
        return accountInformationOpt;
    }
    
    
}
