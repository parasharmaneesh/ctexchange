package com.ct.ctexchange.user.transaction;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ct.ctexchange.currency.BitcoinPricingAlgo;
import com.ct.ctexchange.currency.Currency;
import com.ct.ctexchange.user.User;
import com.ct.ctexchange.user.balance.BalanceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

	private final BitcoinPricingAlgo bitcoinPricingAlgo;
	private final TransactionRepository transactionRepository;
	private final BalanceService balanceService;

	@Transactional
	public Transaction createTransaction(User user, double transactionAmount, Currency currency, TransactionType type) {
		Transaction transaction = Transaction.builder().user(user).amount(transactionAmount).type(type)
				.currency(currency).build();
		Currency balanceCurrency = currency;
		double amount = transactionAmount;
		if (type.equals(TransactionType.BUY)) {
			transaction.setPrice(bitcoinPricingAlgo.getBtcPrice());
			balanceService.validateBuyAndUpdateBalance(transaction);
		} else if (type.equals(TransactionType.SELL)) {
			transaction.setPrice(bitcoinPricingAlgo.getBtcPrice());
			balanceService.validateSellAndUpdateBalance(transaction);
			balanceCurrency = Currency.USD;
			amount = transaction.getPrice() * transactionAmount;
		}

		transactionRepository.save(transaction);
		balanceService.updateBalance(transaction, balanceCurrency, amount);
		return transaction;
	}

	public List<Transaction> getTransactionsByUserId(long id) {
		return transactionRepository.findAllByUserId(id);
	}

}
