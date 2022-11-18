package com.ct.ctexchange.balance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ct.ctexchange.currency.Currency;
import com.ct.ctexchange.exception.ErrorMessage;
import com.ct.ctexchange.exception.TransactionException;
import com.ct.ctexchange.user.User;
import com.ct.ctexchange.user.balance.Balance;
import com.ct.ctexchange.user.balance.BalanceId;
import com.ct.ctexchange.user.balance.BalanceRepository;
import com.ct.ctexchange.user.balance.BalanceService;
import com.ct.ctexchange.user.transaction.Transaction;
import com.ct.ctexchange.user.transaction.TransactionType;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

	@InjectMocks
	private BalanceService balanceService;

	@Mock
	private BalanceRepository balanceRepository;

	@Test
	void shouldUpdateBalance() {
		User user = User.builder().build();
		Transaction transaction = Transaction.builder().user(user).build();
		Balance balance = Balance.builder().amount(100.0).build();

		when(balanceRepository.findById(Mockito.any(BalanceId.class))).thenReturn(Optional.of(balance));
		when(balanceRepository.save(Mockito.any(Balance.class))).thenReturn(balance);

		Balance savedBalance = balanceService.updateBalance(transaction, Currency.USD, 100);
		assertThat(savedBalance.getAmount()).isEqualTo(200.0);
		verify(balanceRepository, times(1)).save(balance);
	}

	@Test
	void shouldThrowNoSufficientBalanceExceptionOnValidateBuyAndUpdateBalance() {
		User user = User.builder().build();
		Transaction transaction = Transaction.builder().amount(200.0).price(1.0).user(user).type(TransactionType.BUY)
				.build();
		Balance balance = Balance.builder().amount(100.0).build();

		when(balanceRepository.findById(Mockito.any(BalanceId.class))).thenReturn(Optional.of(balance));

		Throwable thrown = catchThrowable(() -> {
			balanceService.validateBuyAndUpdateBalance(transaction);
		});

		assertThat(thrown).isInstanceOf(TransactionException.class)
				.hasMessage(ErrorMessage.NOT_SUFFICIENT_BALANCE.getMsg());
	}

	@Test
	void shouldValidateBuyAndUpdateBalance() {
		User user = User.builder().build();
		Transaction transaction = Transaction.builder().amount(10.0).price(1.0).user(user).type(TransactionType.BUY)
				.build();
		Balance balance = Balance.builder().amount(100.0).build();

		when(balanceRepository.findById(Mockito.any(BalanceId.class))).thenReturn(Optional.of(balance));
		when(balanceRepository.save(Mockito.any(Balance.class))).thenReturn(balance);

		balanceService.validateBuyAndUpdateBalance(transaction);

		verify(balanceRepository, times(1)).save(balance);
	}

	@Test
	void shouldThrowNoSufficientBalanceExceptionOnValidateSellAndUpdateBalance() {
		User user = User.builder().build();
		Transaction transaction = Transaction.builder().amount(200.0).price(1.0).user(user).type(TransactionType.SELL)
				.build();
		Balance balance = Balance.builder().amount(100.0).build();

		when(balanceRepository.findById(Mockito.any(BalanceId.class))).thenReturn(Optional.of(balance));

		Throwable thrown = catchThrowable(() -> {
			balanceService.validateSellAndUpdateBalance(transaction);
		});

		assertThat(thrown).isInstanceOf(TransactionException.class)
				.hasMessage(ErrorMessage.NOT_SUFFICIENT_BALANCE.getMsg());
	}

	@Test
	void shouldValidateSellAndUpdateBalance() {
		User user = User.builder().build();
		Transaction transaction = Transaction.builder().amount(100.0).price(2.0).user(user).type(TransactionType.SELL)
				.build();
		Balance balance = Balance.builder().amount(100.0).build();

		when(balanceRepository.findById(Mockito.any(BalanceId.class))).thenReturn(Optional.of(balance));
		when(balanceRepository.save(Mockito.any(Balance.class))).thenReturn(balance);

		balanceService.validateSellAndUpdateBalance(transaction);

		verify(balanceRepository, times(1)).save(balance);
	}
}
