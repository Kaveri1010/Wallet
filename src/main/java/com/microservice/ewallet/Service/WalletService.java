package com.microservice.ewallet.Service;

import java.util.List;


import com.microservice.ewallet.Entity.Wallet;
import com.microservice.ewallet.exceptions.WalletException;

public interface WalletService {
	public List<Wallet> getAllWallet();

	public Wallet getWallet(String id) throws WalletException;
	public String getWalletByUserId(String userid) throws WalletException;

	public Wallet addWallet(Wallet Wallet) throws WalletException;

	public void updateWallet(Wallet Wallet, String globalId)  throws WalletException;

	//public Wallet updateWalletAmount(@NotNull Wallet wallet, @NotNull String amount, @NotNull Boolean isCredit)
		//	throws WalletException;

	public void deleteWallet(String id);

}
