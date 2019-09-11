package com.microservice.ewallet.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.microservice.ewallet.exceptions.*;

import com.microservice.ewallet.Entity.Wallet;
import com.microservice.ewallet.Repository.WalletRepository;

@Validated
@Service
public class WalletServiceImpl implements WalletService {
	@Autowired
	private WalletRepository walletRepository;

	@Autowired

	public List<Wallet> getAllWallet() {
		List<Wallet> Wallet = new ArrayList<>();
		walletRepository.findAll().forEach(Wallet::add);
		return Wallet;
	}

	public Wallet getWallet(String id) throws WalletException {
		try {
			return walletRepository.findById(id).get();
		} catch (Exception e) {
			throw new WalletException("No Wallet Found", 500);
		}

	}

	public String getWalletByUserId(String id) throws WalletException {
		try {
			return walletRepository.findByUserId(id).getBalance().toString();

		} catch (Exception e) {
			throw new WalletException("No Wallet Found", 500);
		}

	}

	public Wallet addWallet(Wallet wallet) throws WalletException {

		if (wallet.getBalance().compareTo(BigDecimal.ZERO) >= 0) {
			walletRepository.save(wallet);
			return (walletRepository.findById(wallet.getId()).get());
		} else {
			throw new WalletException("Not Enough Funds", 500);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = WalletException.class)
	@Override
	public void updateWallet(Wallet wallet, String Id) throws WalletException {

		if (wallet.getBalance().compareTo(BigDecimal.ZERO) >= 0) {
			walletRepository.save(wallet);
		} else {
			throw new WalletException("Not sufficent Funds.", 200);
		}

	}

	public void deleteWallet(String id) {
		walletRepository.deleteById(id);
		;

	}

}
