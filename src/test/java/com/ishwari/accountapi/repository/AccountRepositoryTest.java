package com.ishwari.accountapi.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ishwari.accountapi.entity.Account;

@DataJpaTest
class AccountRepositoryTest {
	@Autowired
	AccountRepository accountRepository;

	@Test
	@DisplayName("Testing the findByCustomer")
	void findByCustomerFoundTest() {
		accountRepository.save(new Account(4L));
		List<Account> lstAccounts = accountRepository.findByCustomer(4L);
		Assertions.assertEquals(1, lstAccounts.size());
		Assertions.assertEquals(4L, lstAccounts.get(0).getCustomerID());
		Assertions.assertEquals(0, lstAccounts.get(0).getBalance());
		Assertions.assertNotEquals(null, lstAccounts.get(0).getId());
	}

	@Test
	@DisplayName("Testing the findByCustomer no result")
	void findByCustomerNoResultTest() {
		Assertions.assertEquals(0, accountRepository.findByCustomer(1L).size());
		Assertions.assertNotEquals(22, accountRepository.findByCustomer(1L).size());
	}

}
