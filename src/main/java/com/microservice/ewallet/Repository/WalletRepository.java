package com.microservice.ewallet.Repository;

import org.springframework.data.repository.CrudRepository;

import com.microservice.ewallet.Entity.Wallet;

public interface WalletRepository extends CrudRepository<Wallet ,String>{
	
	Wallet findByUserId(String id);
}
