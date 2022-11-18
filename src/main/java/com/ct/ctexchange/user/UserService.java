package com.ct.ctexchange.user;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ct.ctexchange.currency.Currency;
import com.ct.ctexchange.exception.ErrorMessage;
import com.ct.ctexchange.exception.UserException;
import com.ct.ctexchange.user.transaction.TransactionService;
import com.ct.ctexchange.user.transaction.TransactionType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final TransactionService transactionService;

	@Transactional
	public User createUser(User user) {
		user = userRepository.save(user);
		transactionService.createTransaction(user, 1000.0, Currency.USD, TransactionType.DEPOSIT);
		return user;
	}

	public void deleteUser(long id) {
		userRepository.findById(id).ifPresentOrElse(userRepository::delete, () -> {
			log.info("User {} does not exist.", id);
			throw new UserException(ErrorMessage.USER_NOT_FOUND.getMsg());
		});

	}

}
