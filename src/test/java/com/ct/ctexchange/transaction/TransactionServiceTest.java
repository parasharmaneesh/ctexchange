package com.ct.ctexchange.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ct.ctexchange.currency.BitcoinPricingAlgo;
import com.ct.ctexchange.currency.Currency;
import com.ct.ctexchange.user.User;
import com.ct.ctexchange.user.balance.BalanceService;
import com.ct.ctexchange.user.transaction.Transaction;
import com.ct.ctexchange.user.transaction.TransactionRepository;
import com.ct.ctexchange.user.transaction.TransactionService;
import com.ct.ctexchange.user.transaction.TransactionType;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

	@InjectMocks
	private TransactionService transactionService;

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private BitcoinPricingAlgo bitcoinPricingAlgo;

	@Mock
	private BalanceService balanceService;

	@Test
	void shouldCreateBuyTransaction() {
		User user = User.builder().build();

		Transaction transaction = transactionService.createTransaction(user, 10, Currency.BTC, TransactionType.BUY);

		verify(transactionRepository, times(1)).save(transaction);
		assertThat(transaction.getAmount()).isEqualTo(10.0d);

	}
	
	@Test
	void shouldCreateSellTransaction() {
		User user = User.builder().build();

		Transaction transaction = transactionService.createTransaction(user, 10, Currency.BTC, TransactionType.SELL);

		verify(transactionRepository, times(1)).save(transaction);
		assertThat(transaction.getAmount()).isEqualTo(10.0d);

	}
}
