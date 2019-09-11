package com.microservice.ewallet.Entity;


import java.math.BigDecimal;
import java.util.Date;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
@Table(name = "transactions")
public class Transaction {
	
	@Id
	@NotBlank(message = "Transaction globalId must not be empty")
    @NotNull(message = "Transaction globalId must be provided")
    @Column(name = "id", unique = true, nullable = false)
	 private String id;
	 private String type;
	 private BigDecimal amount;
	 private String currency;
	// @NotNull(message = "Transaction wallet must be provided")
	    @ManyToOne(targetEntity=Wallet.class, fetch = FetchType.LAZY)
	    //@JoinColumn(name = "wallet_id")
	    @JsonIgnore
	    private Wallet wallet;
	 private String  description;
	 private String createdBy;
	 @UpdateTimestamp
		@Temporal(TemporalType.TIMESTAMP)
	 private Date dateCreated; 
	
	 
		public Transaction() {
		
		}
	 
	public Transaction( String id, String type, BigDecimal amount, String currency, String  walletId,
			String description, String createdBy) {
		super();
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.currency = currency;
		this.description = description;
		this.createdBy = createdBy;
		this.dateCreated =new Date();
		this.wallet = new Wallet(walletId);

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@JsonIgnore
	  public Wallet getWallet() {
	        return wallet;
	    }

	    public void setWallet(Wallet destWallet) {

this.wallet = new Wallet(destWallet.getId(),destWallet.getUserId()
		,destWallet.getBalance(),destWallet.getCurrency(),destWallet.getLastUpdatedBy());
	       
	        
	    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
}
	
	

