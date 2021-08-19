package com.ishwari.accountapi.controller;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishwari.accountapi.entity.Account;
import com.ishwari.accountapi.entity.Transaction;
import com.ishwari.accountapi.exception.AccountNotFoundException;
import com.ishwari.accountapi.exception.CustomerNotFoundException;
import com.ishwari.accountapi.model.AccountInformation;
import com.ishwari.accountapi.model.CustomerAccountInformation;
import com.ishwari.accountapi.service.account.AccountServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

	private static final String CREATE_ACCOUNT_URI = "/api/v1/account/create-account";

	private static final String GET_CUSTOMER_ACCOUNT_INFO_URI = "/api/v1/account/get-customer-account-info/{customerId}";

	@MockBean
	private AccountServiceImpl accountService;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("createAccount test OK")
	void createAccountOK() throws Exception {
		Account account = new Account(1L);
		account.setId(2L);
		AccountInformation accountInformation = new AccountInformation();
		accountInformation.setAccountNumber(2L);
		accountInformation.setBalance(100);
		var trans = new Transaction();
		trans.setAccountID(accountInformation.getAccountNumber());
		trans.setId(3L);
		trans.setAmount(100);
		accountInformation.setTransactionList(Arrays.asList(trans));

		Mockito.when(accountService.createNewAccountByCustomer(1L, 100)).thenReturn(Optional.of(account));

		Mockito.when(accountService.findInformationByID(account.getId())).thenReturn(Optional.of(accountInformation));

		final MvcResult result = mvc
				.perform(MockMvcRequestBuilders.post(CREATE_ACCOUNT_URI).param("customerID", "1").param("initialCredit",
						"100"))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

		Mockito.verify(accountService, Mockito.times(1)).createNewAccountByCustomer(1L, 100);

		String objectResult = result.getResponse().getContentAsString();
		accountInformation = objectMapper.readValue(objectResult, AccountInformation.class);

		Assertions.assertTrue(accountInformation.getAccountNumber().equals(accountInformation.getAccountNumber()));
		Assertions.assertTrue(accountInformation.getBalance() == accountInformation.getBalance());
		Assertions.assertTrue(accountInformation.getTransactionList().get(0).getAccountID().equals(account.getId()));
		Assertions.assertTrue(accountInformation.getTransactionList().get(0).getAmount() == trans.getAmount());
	}

	@Test
	@DisplayName("createAccount Customer not found")
	void createAccountKO1() throws Exception {

		Mockito.when(accountService.createNewAccountByCustomer(2L, 100)).thenThrow(CustomerNotFoundException.class);

		mvc.perform(
				MockMvcRequestBuilders.post(CREATE_ACCOUNT_URI).param("customerID", "2").param("initialCredit", "100"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
	}

	@Test
	@DisplayName("createAccount Account not found")
	void createAccountKO2() throws Exception {
		Account account = new Account(1L);
		account.setId(2L);

		Mockito.when(accountService.createNewAccountByCustomer(1L, 100)).thenReturn(Optional.of(account));

		Mockito.when(accountService.findInformationByID(account.getId())).thenThrow(AccountNotFoundException.class);

		mvc.perform(
				MockMvcRequestBuilders.post(CREATE_ACCOUNT_URI).param("customerID", "1").param("initialCredit", "100"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
	}

	@Test
	@DisplayName("getCustomerAccountInfo test OK")
	void getCustomerAccountInfoOK() throws Exception {
		CustomerAccountInformation customerAccountInformation = new CustomerAccountInformation();
		customerAccountInformation.setCustomerName("Scott");
		customerAccountInformation.setCustomerSurname("Ridley");
		final Long customerID = Long.valueOf(1);

		AccountInformation accountInformation = new AccountInformation();
		accountInformation.setAccountNumber(2L);
		accountInformation.setBalance(100);
		Transaction trans = new Transaction();
		trans.setAccountID(accountInformation.getAccountNumber());
		trans.setId(3L);
		trans.setAmount(100);
		accountInformation.setTransactionList(Arrays.asList(trans));

		customerAccountInformation.setAccounts(Arrays.asList(accountInformation));

		Mockito.when(accountService.getCustomerAccountInfo(1L)).thenReturn(Optional.of(customerAccountInformation));

		final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(GET_CUSTOMER_ACCOUNT_INFO_URI, customerID))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		String objectResult = result.getResponse().getContentAsString();
		customerAccountInformation = objectMapper.readValue(objectResult, CustomerAccountInformation.class);

		Assertions.assertTrue(
				customerAccountInformation.getCustomerName().equals(customerAccountInformation.getCustomerName()));
		Assertions.assertTrue(customerAccountInformation.getCustomerSurname()
				.equals(customerAccountInformation.getCustomerSurname()));
		Assertions.assertTrue(customerAccountInformation.getAccounts().size() == 1);
		Assertions.assertTrue(customerAccountInformation.getAccounts().get(0).getAccountNumber()
				.equals(accountInformation.getAccountNumber()));
		Assertions.assertTrue(
				customerAccountInformation.getAccounts().get(0).getBalance() == accountInformation.getBalance());
		Assertions.assertTrue(customerAccountInformation.getAccounts().get(0).getTransactionList().size() == 1);
		Assertions.assertTrue(customerAccountInformation.getAccounts().get(0).getTransactionList().get(0)
				.getAmount() == trans.getAmount());
		Assertions.assertTrue(customerAccountInformation.getAccounts().get(0).getTransactionList().get(0).getAccountID()
				.equals(accountInformation.getAccountNumber()));
	}

	@Test
	@DisplayName("getCustomerAccountInfo customer not found")
	void getCustomerAccountInfoKO1() throws Exception {
		Mockito.when(accountService.getCustomerAccountInfo(1L)).thenThrow(CustomerNotFoundException.class);

		final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(GET_CUSTOMER_ACCOUNT_INFO_URI, 1L))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value())).andReturn();
	}

}
