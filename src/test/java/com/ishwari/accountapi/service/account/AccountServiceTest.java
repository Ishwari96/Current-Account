package com.ishwari.accountapi.service.account;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.ishwari.accountapi.entity.Account;
import com.ishwari.accountapi.entity.Transaction;
import com.ishwari.accountapi.exception.AccountNotFoundException;
import com.ishwari.accountapi.exception.CustomerNotFoundException;
import com.ishwari.accountapi.exception.TransactionException;
import com.ishwari.accountapi.model.AccountInformation;
import com.ishwari.accountapi.repository.AccountRepository;
import com.ishwari.accountapi.service.customer.CustomerServiceImpl;
import com.ishwari.accountapi.service.transaction.TransactionServiceImpl;

class AccountServiceTest {

	AccountServiceImpl accountService;

	AccountRepository accountRepository = Mockito.mock(AccountRepository.class);

	TransactionServiceImpl transactionService = Mockito.mock(TransactionServiceImpl.class);

	CustomerServiceImpl customerService = Mockito.mock(CustomerServiceImpl.class);

	@BeforeEach
	void setup() {
		accountService = new AccountServiceImpl(transactionService, customerService, accountRepository);
	}

	@Test
	@DisplayName("findByCustomer with result")
	void findByCustomerOK() {
		Mockito.when(accountRepository.findByCustomer(1L)).thenReturn(Arrays.asList(new Account(1L)));
		List<Account> lstAccount = accountService.findByCustomer(1L);
		Assertions.assertEquals(1, lstAccount.size());
		Assertions.assertEquals(1L, lstAccount.get(0).getCustomerID());
	}

	@Test
	@DisplayName("findByCustomer with NO result")
	void findByCustomerNoResult() {
		Mockito.when(accountRepository.findByCustomer(1L)).thenReturn(Collections.emptyList());
		List<Account> lstAccount = accountService.findByCustomer(1L);
		Assertions.assertTrue(lstAccount.isEmpty());
	}

	@Test
	@DisplayName("findInformationByID OK test")
	void findInformationByIDOk() {
		Account acc = new Account(99L);
		acc.setId(1L);
		acc.setBalance(111);
		Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(acc));
		Mockito.when(transactionService.findByAccount(1L)).thenReturn(Arrays.asList(new Transaction(1L, 111)));
		Optional<AccountInformation> optionalAccountInformation = accountService.findInformationByID(1L);
		Assertions.assertTrue(optionalAccountInformation.isPresent());
		Assertions.assertEquals(111, optionalAccountInformation.get().getBalance());
		Assertions.assertEquals(1L, optionalAccountInformation.get().getAccountNumber());
		Assertions.assertEquals(1, optionalAccountInformation.get().getTransactionList().size());
		Assertions.assertEquals(1L, optionalAccountInformation.get().getTransactionList().get(0).getAccountID());
	}

	@Test
	@DisplayName("findInformationByID No account found")
	void findInformationByIDNoAccount() {
		Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.empty());
		Assertions.assertThrows(AccountNotFoundException.class, () -> {
			accountService.findInformationByID(1L);
		});
	}

	@Test
	@DisplayName("findInformationByID No account's transactions")
	void findInformationByIDNoTransactions() {
		Account acc = new Account(99L);
		acc.setId(1L);
		acc.setBalance(111);
		Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(acc));
		Mockito.when(transactionService.findByAccount(1L)).thenReturn(Collections.emptyList());
		Optional<AccountInformation> optionalAccountInformation = accountService.findInformationByID(1L);
		Assertions.assertTrue(optionalAccountInformation.isPresent());
		Assertions.assertEquals(111, optionalAccountInformation.get().getBalance());
		Assertions.assertEquals(1L, optionalAccountInformation.get().getAccountNumber());
		Assertions.assertTrue(optionalAccountInformation.get().getTransactionList().isEmpty());
	}

	@Test
	@DisplayName("findInformationByID No account ID provided")
	void findInformationByIDNoParams() {
		Assertions.assertThrows(AccountNotFoundException.class, () -> {
			accountService.findInformationByID(null);
		});
	}

	@Test
	@DisplayName("createNewAccountByCustomer Ok result")
	void createNewAccountByCustomerOK() {
		Mockito.when(customerService.checkCustomerExists(2L)).thenReturn(true);
		Account acc = new Account(99L);
		acc.setId(1L);
		acc.setBalance(100);
		acc.setCustomerID(2L);
		Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(acc);
		Mockito.when(transactionService.createTransaction(1L, 100)).thenReturn(Optional.of(new Transaction(1L, 100)));
		Optional<Account> optionalAccount = accountService.createNewAccountByCustomer(2L, 100);
		Assertions.assertTrue(optionalAccount.isPresent());
		Assertions.assertEquals(1L, optionalAccount.get().getId());
		Assertions.assertEquals(2L, optionalAccount.get().getCustomerID());
		Assertions.assertEquals(acc.getBalance(), optionalAccount.get().getBalance());
	}

	@Test
	@DisplayName("createNewAccountByCustomer null CustomerID or no Customer found")
	void createNewAccountByCustomer2() {
		Mockito.when(customerService.checkCustomerExists(2L)).thenReturn(false);
		Assertions.assertThrows(CustomerNotFoundException.class, () -> {
			accountService.createNewAccountByCustomer(2L, 0);
		});

		Mockito.when(customerService.checkCustomerExists(null)).thenReturn(false);
		Assertions.assertThrows(CustomerNotFoundException.class, () -> {
			accountService.createNewAccountByCustomer(null, 0);
		});
	}

	@Test
	@DisplayName("createNewAccountByCustomer creation of transaction exception")
	void createNewAccountByCustomer3() {
		Mockito.when(customerService.checkCustomerExists(2L)).thenReturn(true);
		Account acc = new Account(99L);
		acc.setId(1L);
		acc.setBalance(100);
		acc.setCustomerID(2L);
		Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(acc);
		Mockito.when(transactionService.createTransaction(1L, 100))
				.thenThrow(new TransactionException("Error with transaction creation"));

		TransactionException exception = Assertions.assertThrows(TransactionException.class,
				() -> accountService.createNewAccountByCustomer(2L, 100));

		String expectedMessage = "Error with transaction creation";
		Assertions.assertTrue(exception.getMessage().contains(expectedMessage));
	}

}
