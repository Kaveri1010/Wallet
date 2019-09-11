package com.microservice.ewallet.Service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.ewallet.Service.WalletService;
import com.microservice.ewallet.exceptions.WalletException;
import com.microservice.ewallet.Entity.Wallet;
import com.microservice.ewallet.Entity.Transaction;
import com.microservice.ewallet.Repository.TransactionRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private WalletService walletService;

	public List<Transaction> getAllTransaction() {
		List<Transaction> Transaction = new ArrayList<>();
		transactionRepository.findAll().forEach(Transaction::add);
		return Transaction;
	}

	public List<Transaction> getTransactionsByWalletUserId(String id) {
		List<Transaction> Transaction = new ArrayList<>();
		transactionRepository.findByWalletUserId(id).forEach(Transaction::add);
		return Transaction;
	}

	public Transaction getTransaction(String id) {
		return transactionRepository.findById(id).get();

	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = WalletException.class)
	@Override
	public Transaction addTransaction(Transaction transaction, String walletId) throws WalletException {
		Wallet wallet = walletService.getWallet(walletId);
		transaction.setWallet(wallet);
		if (transactionRepository.existsById(transaction.getId())) {
			throw new WalletException("Duplicate Transaction", 500);
		} else {
			if (wallet.getCurrency().equalsIgnoreCase(transaction.getCurrency()))
				if (transaction.getType().equalsIgnoreCase("CREDIT")) {
					transactionRepository.save(transaction);
					wallet.setBalance(wallet.getBalance().add(transaction.getAmount()));
					walletService.updateWallet(wallet, wallet.getId());
				} else if (transaction.getType().equalsIgnoreCase("DEBIT")) {
					transactionRepository.save(transaction);
					wallet.setBalance(wallet.getBalance().subtract(transaction.getAmount()));
					walletService.updateWallet(wallet, wallet.getId());
				}
		}
		// if wallet.getBalance()

		return (transactionRepository.findById(transaction.getId()).get());
	}

	public void updateTransaction(Transaction Transaction, String globalId) {
		transactionRepository.save(Transaction);

	}

	public void deleteTransaction(String id) {
		transactionRepository.deleteById(id);
		;

	}

}
