package com.microservice.ewallet.Service;
import com.microservice.ewallet.Entity.Transaction;
import com.microservice.ewallet.exceptions.WalletException;

import java.util.List;
public interface TransactionService {

	public List<Transaction> getAllTransaction();
	//public List<Transaction> getTransactionsByWallet(String walletId);
	public List<Transaction> getTransactionsByWalletUserId(String walletId);
	public Transaction getTransaction(String id);
	public Transaction addTransaction(Transaction transaction,String walletId)  throws WalletException;
	public void updateTransaction(Transaction Transaction, String globalId);
	public void deleteTransaction(String id);

}
