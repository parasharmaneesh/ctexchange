package com.ct.ctexchange.user.model;

import javax.validation.constraints.Positive;

import com.ct.ctexchange.currency.Currency;
import com.ct.ctexchange.user.transaction.TransactionType;

import lombok.Data;

@Data
public class CreateTransactionRequest {

	@Positive
	private double amount;
	private Currency currency;
	
	private TransactionType type;
	
}
