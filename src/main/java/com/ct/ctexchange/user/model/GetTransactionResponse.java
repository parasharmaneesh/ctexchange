package com.ct.ctexchange.user.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetTransactionResponse {

	List<TransactionModel> transactions;

	List<BalanceModel> balances;

}
