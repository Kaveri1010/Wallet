package com.microservice.ewallet;

import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;


import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.google.gson.Gson;

import com.microservice.ewallet.Entity.Transaction;
import com.microservice.ewallet.Entity.Wallet;
import com.microservice.ewallet.Repository.TransactionRepository;
import com.microservice.ewallet.Repository.WalletRepository;
import com.microservice.ewallet.exceptionHandler.ErrorDetails;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestingWalletApplication {
	 @LocalServerPort
	    private int port;
	@Autowired
	WalletRepository repository;
	@Autowired
	TransactionRepository tRepository;
	@Autowired
	TestRestTemplate testRestTemplate ;
	

	Wallet w1;
	Transaction t1;
	Transaction t2;

	@Before
	public void setUp() {
		// 7
		w1 = new Wallet("W-1", "Kaveri", new BigDecimal(0), "SEK", "Kaveri");
		t1 = new Transaction("T-1", "Debit", new BigDecimal(100), "SEK", "W-1", "Testing Credit/Debit transaction",
				"Kaveri");
		t2 = new Transaction("T-2", "Debit", new BigDecimal(20), "SEK", "W-1", "Testing Credit/Debit transaction",
				"Kaveri");
	}


	@Test
	public void test_1_addWallet() {
	
		ResponseEntity<String> response = testRestTemplate.postForEntity("http://localhost:"+ port+"/wallets", w1,
				String.class);
		String jsonString = response.getBody();
		Gson g = new Gson();
		Wallet w = g.fromJson(jsonString, Wallet.class);
		assertEquals(w1.getUserId(), w.getUserId());
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void test_2_getWallets() {
		String walletId = w1.getId();

		ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:"+port+"/wallets/" + walletId,
				String.class);
		String jsonString = response.getBody();
		Gson g = new Gson();
		Wallet w = g.fromJson(jsonString, Wallet.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(w1.getUserId(), w.getUserId());

	}

	@Test // Use case 1 Current balance per player
	public void test_3_getWalletsBalance() {
		String walletUserId = w1.getUserId();
		ResponseEntity<String> response = testRestTemplate
				.getForEntity("http://localhost:"+port+"/wallets/" + walletUserId + "/balance", String.class);
		String string = response.getBody();// balance
		BigDecimal balance = new BigDecimal(string);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(w1.getBalance().compareTo(balance), 0);

	}

	@Test // Use case 2 ,debit transaction will only suceed if debit transaction is
			// greater than wallet balance
	public void test_4_CreateTransactionDebitFail() {
		String walletId = w1.getId();

		ResponseEntity<String> response = testRestTemplate
				.postForEntity("http://localhost:"+port+"/wallets/" + walletId + "/transactions", t1, String.class);
		String jsonString = response.getBody();
		Gson g = new Gson();
		ErrorDetails e = g.fromJson(jsonString, ErrorDetails.class);
		assertEquals(e.getMessage().equalsIgnoreCase("Not sufficent Funds."), true);

	}

	@Test // Usecase 2 part2Credit
	public void test_5_CreateTransactionCredit() {
		String walletId = w1.getId();
		t1.setType("Credit");
		;
		
		ResponseEntity<String> response = testRestTemplate
				.postForEntity("http://localhost:"+port+"/wallets/" + walletId + "/transactions", t1, String.class);
		String jsonString = response.getBody();
		Gson g = new Gson();
		Transaction t = g.fromJson(jsonString, Transaction.class);
		response = testRestTemplate.getForEntity("http://localhost:"+port+"/wallets/" + walletId, String.class);
		jsonString = response.getBody();
		Wallet w = g.fromJson(jsonString, Wallet.class);

		assertEquals(w.getBalance().compareTo(t.getAmount()), 0);

	}

	@Test // Usecase 2 Transaction id must be unique
	public void test_6_CreateTransactionnotUnique() {
		String walletId = w1.getId();
		t1.setType("Credit");
		;
		ResponseEntity<String> response = testRestTemplate
				.postForEntity("http://localhost:"+port+"/wallets/" + walletId + "/transactions", t1, String.class);
		String jsonString = response.getBody();
		Gson g = new Gson();
		ErrorDetails e = g.fromJson(jsonString, ErrorDetails.class);
		assertEquals(e.getMessage().equalsIgnoreCase("Duplicate Transaction"), true);

	}

	@Test // Use case 2 ,debit transaction will only suceed if debit transaction is
	// greater than wallet balance
	public void test_7_CreateTransactionDebitFail() {
		String walletId = w1.getId();

		ResponseEntity<String> response = testRestTemplate
				.postForEntity("http://localhost:"+port+"/wallets/" + walletId + "/transactions", t2, String.class);
		String jsonString = response.getBody();
		Gson g = new Gson();
		response = testRestTemplate.getForEntity("http://localhost:"+port+"/wallets/" + walletId, String.class);
		jsonString = response.getBody();
		Wallet w = g.fromJson(jsonString, Wallet.class);
		BigDecimal balance = new BigDecimal("80");
		assertEquals(w.getBalance().compareTo(balance), 0);

	}
	
	@Test // Use case 2 ,debit transaction will only suceed if debit transaction is
	// greater than wallet balance
	public void test_8_GetTransactionPerPlayer() {
		String walletUserId = w1.getUserId();
		ResponseEntity<String> response = testRestTemplate
				.getForEntity("http://localhost:"+port+"/wallets/" + walletUserId + "/transactions", String.class);
		String s = response.getBody();
		try {
		JSONArray arr = new JSONArray(s);
		
		assertEquals(arr.length(), 2);}
		catch(Exception e) {
			throw new RuntimeException(e);
		}

	}
}
