package com.ishwari.accountapi.service.transaction;

import static java.util.Optional.ofNullable;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.hibernate.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ishwari.accountapi.entity.Transaction;
import com.ishwari.accountapi.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public List<Transaction> findByAccount(@NotNull Long accountID) {
		return transactionRepository.findByAccount(accountID);
	}

	@Override
	@Transactional
	public Optional<Transaction> createTransaction(@NotNull Long accountID, double amount) {
		
		logger.info("Transaction-createTransaction: Create a transaction for the given account with credit passed");
		var transaction = new Transaction(accountID, amount);
		   ofNullable(transaction).ifPresentOrElse(trans -> transactionRepository.save(transaction),
		                () -> {
		                    throw new TransactionException("Error with transaction creation");
		                });
			
		return Optional.of(transaction);
	}

}
