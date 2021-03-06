package com.ishwari.accountapi.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ishwari.accountapi.entity.Account;
import com.ishwari.accountapi.model.AccountInformation;
import com.ishwari.accountapi.model.CustomerAccountInformation;
import com.ishwari.accountapi.service.account.AccountService;
import com.ishwari.accountapi.utility.Constants;
import com.ishwari.accountapi.utility.ObjectMapperUtils;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(Constants.ACCOUNT_BASE_URL)
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
    @PostMapping(Constants.CREATE_ACCOUNT)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create Account for the given customer id")
    public AccountInformation createAccount(@RequestParam(value = "customerID") final Long customerID,
			@RequestParam(value = "initialCredit") final double initialCredit) {
		logger.info("AccountController-createAccount: create new account for the given customer id {}", customerID);
		Optional<Account> account = accountService.createNewAccountByCustomer(customerID, initialCredit);
		Long accountId = account.isPresent() ? account.get().getId() : 0L;
		logger.info(
				"AccountController-createAccount: retrieve the account information class for which account created {}",
				accountId);
		Optional<AccountInformation> acctInforOpt = accountService.findInformationByID(accountId);
		return ObjectMapperUtils.map(acctInforOpt.get(), AccountInformation.class);
	}
    
    /**
     * Web service endpoint to retrieve the full information about a customer's accounts
     * and all transactions related.
     *
     * @param customerId
     * @return CustomerAccountInformationDTO
     */
    @GetMapping(Constants.ACCOUNT_GET_URL)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "information about a customer's accounts")
    public CustomerAccountInformation getCustomerAccountInfo(@PathVariable final Long customerId) {
        logger.info("AccountController-getCustomerAccountInfo: retrieve the customer's account informations for customer id {}",customerId);
        Optional<CustomerAccountInformation> custAccInforOpt = accountService.getCustomerAccountInfo(customerId);
        return ObjectMapperUtils.map( custAccInforOpt.get(), CustomerAccountInformation.class);
    }
    
    
}
