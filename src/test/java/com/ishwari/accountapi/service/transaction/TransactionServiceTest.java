package com.ishwari.accountapi.service.transaction;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.ishwari.accountapi.entity.Transaction;
import com.ishwari.accountapi.repository.TransactionRepository;


class TransactionServiceTest {

    private final TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);

    TransactionServiceImpl transactionService;

    @BeforeEach
    void setup() {
        transactionService = new TransactionServiceImpl(transactionRepository);
    }

    @Test
    @DisplayName("createTransaction test OK")
    void createTransactionOK() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(100);
        transaction.setAccountID(3L);
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(transaction);

        Optional<Transaction> optionalTransaction = transactionService.createTransaction(3L,100);

		Assertions.assertTrue(optionalTransaction.isPresent());
		Assertions.assertEquals(100, optionalTransaction.get().getAmount());
		Assertions.assertEquals(3L, optionalTransaction.get().getAccountID());
		Assertions.assertEquals(1L, optionalTransaction.get().getId());
    }
}
