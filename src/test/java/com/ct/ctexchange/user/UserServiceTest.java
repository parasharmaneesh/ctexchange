package com.ct.ctexchange.user;

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
import org.mockito.junit.jupiter.MockitoExtension;

import com.ct.ctexchange.currency.Currency;
import com.ct.ctexchange.exception.ErrorMessage;
import com.ct.ctexchange.exception.UserException;
import com.ct.ctexchange.user.transaction.TransactionService;
import com.ct.ctexchange.user.transaction.TransactionType;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private TransactionService transactionService;

	@Test
	void shouldCreateUser() {
		User user = User.builder().build();
		User savedUser = userService.createUser(user);

		verify(userRepository, times(1)).save(user);
		verify(transactionService, times(1)).createTransaction(savedUser, 1000.0, Currency.USD,
				TransactionType.DEPOSIT);
	}

	@Test
	void shouldDeleteUser() {
		User user = User.builder().id(1l).build();

		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		userService.deleteUser(user.getId());
		verify(userRepository, times(1)).delete(user);
	}

	@Test
	void shouldThrowUserDoesNotExistException() {
		long id = 1;

		Throwable thrown = catchThrowable(() -> {
			userService.deleteUser(id);
		});

		assertThat(thrown).isInstanceOf(UserException.class).hasMessage(ErrorMessage.USER_NOT_FOUND.getMsg());
	}

}
