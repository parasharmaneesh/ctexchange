package com.ct.ctexchange.user.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionModel {

	private Long id;

	private String currency;

	private double price;

	private double amount;

	private String type;
}
