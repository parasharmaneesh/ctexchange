package com.ct.ctexchange.user.balance;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ct.ctexchange.currency.Currency;
import com.ct.ctexchange.exception.ErrorMessage;
import com.ct.ctexchange.exception.TransactionException;
import com.ct.ctexchange.user.transaction.Transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BalanceService {

	private final BalanceRepository balanceRepository;

	@Transactional
	public Balance updateBalance(Transaction transaction, Currency currency, double amount) {
		BalanceId balanceId = BalanceId.builder().userId(transaction.getUser().getId()).currency(currency).build();
		Optional<Balance> balOptional = balanceRepository.findById(balanceId);

		return balOptional.map(b -> {
			b.setAmount(calculateNewBalanceAmount(b, amount));
			log.info("Updated balance {} {}", b.getId(), b.getAmount());
			return balanceRepository.save(b);
		}).orElseGet(() -> {
			log.info("Create balance {} {}", balanceId, amount);
			return balanceRepository
					.save(Balance.builder().id(balanceId).user(transaction.getUser()).amount(amount).build());
		});

	}

	private double calculateNewBalanceAmount(Balance b, double amount) {
		return b.getAmount() + amount;
	}

	@Transactional
	public Balance validateBuyAndUpdateBalance(Transaction transaction) {
		BalanceId balanceId = BalanceId.builder().userId(transaction.getUser().getId()).currency(Currency.USD).build();
		Optional<Balance> balOptional = balanceRepository.findById(balanceId);

		return balOptional.map(b -> {
			double amountRequired = transaction.getAmount() * transaction.getPrice();
			if (b.getAmount() >= amountRequired) {
				log.info("Balance before buy {} {}", b.getId(), b.getAmount());
				b.setAmount(calculateNewBalanceAmount(b, (-1 * amountRequired)));
				log.info("Balance after buy {} {}", b.getId(), b.getAmount());
				return balanceRepository.save(b);
			}
			log.info(ErrorMessage.NOT_SUFFICIENT_BALANCE.getMsg());
			throw new TransactionException(ErrorMessage.NOT_SUFFICIENT_BALANCE.getMsg());
		}).orElseThrow(() -> {
			log.info(ErrorMessage.NOT_SUFFICIENT_BALANCE.getMsg());
			throw new TransactionException(ErrorMessage.NOT_SUFFICIENT_BALANCE.getMsg());
		});
	}

	@Transactional
	public Balance validateSellAndUpdateBalance(Transaction transaction) {
		BalanceId balanceId = BalanceId.builder().userId(transaction.getUser().getId())
				.currency(transaction.getCurrency()).build();
		Optional<Balance> balOptional = balanceRepository.findById(balanceId);

		return balOptional.map(b -> {
			if (b.getAmount() <= transaction.getAmount()) {
				log.info("Balance before sell {} {}", b.getId(), b.getAmount());
				b.setAmount(calculateNewBalanceAmount(b, (-1 * transaction.getAmount())));
				log.info("Balance after sell {} {}", b.getId(), b.getAmount());
				return balanceRepository.save(b);
			}
			log.info(ErrorMessage.NOT_SUFFICIENT_BALANCE.getMsg());
			throw new TransactionException(ErrorMessage.NOT_SUFFICIENT_BALANCE.getMsg());
		}).orElseThrow(() -> {
			log.info(ErrorMessage.NOT_SUFFICIENT_BALANCE.getMsg());
			throw new TransactionException(ErrorMessage.NOT_SUFFICIENT_BALANCE.getMsg());
		});
	}

	public List<Balance> getBalancesByUserId(long id) {
		return balanceRepository.findAllByIdUserId(id);
	}

}
