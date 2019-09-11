
package com.microservice.ewallet.Entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;



@Entity
@Table(name = "wallet")
public class Wallet {

	@Id
	private String id; //ById
	private String userId;//ByUserId
	private BigDecimal balance;
	private String currency;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;

	@Column(name = "last_updated_by")
	private String lastUpdatedBy;
	@OneToMany(targetEntity=Transaction.class, fetch = FetchType.LAZY)
    private List<Transaction> transactions;
	

	
	public Wallet() {
		
	}
	
	public Wallet(String id) {
		super();
		this.id = id;	
	}

	public Wallet(String id, String userId, BigDecimal balance, String currency,
			String lastUpdatedBy) {
		super();
		this.id = id;
		this.userId = userId;
		this.balance = balance;
		this.currency = currency;
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	

}
