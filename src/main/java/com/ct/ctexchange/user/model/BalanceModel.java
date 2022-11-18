package com.ct.ctexchange.user.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BalanceModel {

	private String currency;

	private double amount;

}
