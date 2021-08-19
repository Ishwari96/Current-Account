package com.ishwari.accountapi.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ishwari.accountapi.entity.Account;
import com.ishwari.accountapi.entity.Transaction;

@DataJpaTest
public class TransactionRepositoryTest {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("Testing the findByAccount with results")
    public void findByAccountFoundTest() {
        Account account1 = accountRepository.save(new Account(1L));
        Account account2 = accountRepository.save(new Account(2L));

        transactionRepository.save(new Transaction(account1.getId(),0));
        List<Transaction> lstFoundTransaction1 = transactionRepository.findByAccount(account1.getId());
        Assertions.assertEquals(1,lstFoundTransaction1.size());
        Assertions.assertEquals(account1.getId(),lstFoundTransaction1.get(0).getAccountID());

		transactionRepository.save(new Transaction(account2.getId(), 4));
		List<Transaction> lstFoundTransaction2 = transactionRepository.findByAccount(account2.getId());
		Assertions.assertEquals(1, lstFoundTransaction2.size());
		Assertions.assertEquals(account2.getId(), lstFoundTransaction2.get(0).getAccountID());
		Assertions.assertEquals(2, lstFoundTransaction2.get(0).getAccountID());
    }

	@Test
	@DisplayName("Testing the findByAccount no results")
	public void findByAccountNoResultTest() {
		Assertions.assertEquals(0, transactionRepository.findByAccount(1L).size());
		Assertions.assertNotEquals(22, transactionRepository.findByAccount(4L).size());
	}
}
