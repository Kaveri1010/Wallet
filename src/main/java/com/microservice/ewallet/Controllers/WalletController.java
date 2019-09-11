package com.microservice.ewallet.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.ewallet.exceptions.WalletException;
import com.microservice.ewallet.Entity.Wallet;
import com.microservice.ewallet.Service.WalletService;

@RestController
public class WalletController {
	@Autowired
	WalletService walletService;

	
	@RequestMapping("/wallets")
	public List<Wallet> getAllWallet() {
		return walletService.getAllWallet();
	}
	
	@RequestMapping("/wallets/{id}")
	public Wallet getwallet(@PathVariable String id) throws WalletException{
		return walletService.getWallet(id);
	}
	
	@RequestMapping("/wallets/{userid}/balance")
	public String getwalletBalancebyuser(@PathVariable String userid) throws WalletException{
		return walletService.getWalletByUserId(userid);
	}
	
	@RequestMapping(method=RequestMethod.POST, value ="/wallets")
	public Wallet addWallet(@RequestBody Wallet wallet) throws WalletException{
		return(walletService.addWallet(wallet));
	}
	
	@RequestMapping(method=RequestMethod.PUT, value ="/wallets/{id}")
	public String updateWallet(@RequestBody Wallet wallet,@PathVariable String id) {
		//walletService.updateWallet(wallet,id);
		return("Oops Not Allowed");
	}
	@RequestMapping(method=RequestMethod.DELETE, value ="/wallets/{id}")
	public String deletewallet(@PathVariable String id) {
		//walletService.deleteWallet(id);
		return("Oops Not Allowed");
	}

}
