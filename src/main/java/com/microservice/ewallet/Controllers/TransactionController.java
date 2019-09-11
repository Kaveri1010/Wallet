package com.microservice.ewallet.Controllers;



import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.microservice.ewallet.Entity.Transaction;
import com.microservice.ewallet.Service.TransactionService;
import com.microservice.ewallet.exceptions.WalletException;


@RestController
public class TransactionController {
	@Autowired
	TransactionService transactionService;

	@RequestMapping("/test")
	public String sayHi() {
		return ("<h2>Hello</h2>");
	}
	
/* this is usecase 4 transaction history per player/wallet ,assuming the one player has one wallet */
	
	@RequestMapping("/transactions")
	public List<Transaction>  getAllTransaction() {

	return transactionService.getAllTransaction();
	}
	@RequestMapping("/wallets/{userid}/transactions")
	public List<Transaction>  getTransactionsByWallet(@PathVariable String userid) {
	return transactionService.getTransactionsByWalletUserId(userid);
	}
	
	@RequestMapping("/wallets/{walletId}/transactions/{TransactionId}")
	public Transaction getTransaction(@PathVariable String TransactionId){
		return transactionService.getTransaction(TransactionId);
	}
	

	/* this will be usecase 2 and 3 */
	@RequestMapping(method=RequestMethod.POST, value ="/wallets/{walletId}/transactions")
	public Transaction addTransaction(@RequestBody Transaction transaction,@PathVariable String walletId)  throws WalletException{
		return(transactionService.addTransaction(transaction,walletId));
	}
	
	@RequestMapping(method=RequestMethod.PUT, value ="/wallets/{id}/transactions/{id}")
	public String updateTransaction(@RequestBody Transaction transaction,@PathVariable String id) {
		//transactionService.updateTransaction(transaction,id);
		return("Oops Not Allowed");
	}
	@RequestMapping(method=RequestMethod.DELETE, value ="/wallets/{id}/transactions/{id}")
	public String deleteTransaction(@PathVariable String id) {
		//transactionService.deleteTransaction(id);
		return("Oops Not Allowed");
	}
} 
