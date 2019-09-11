package com.microservice.ewallet.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.microservice.ewallet.Entity.Transaction;


public interface TransactionRepository extends CrudRepository<Transaction ,String> {
	List<Transaction> findByWalletId(String id);
	List<Transaction> findByWalletUserId(String id);
	//getAllMonetoryTransactions
	//getMonetoryTransaction(String globalId);
// Update(MonetoryTransaction monetoryTransaction);
	//deleteMonetoryTransaction(String globalId);
}
