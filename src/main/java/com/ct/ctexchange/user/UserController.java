package com.ct.ctexchange.user;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.ctexchange.user.balance.BalanceService;
import com.ct.ctexchange.user.model.BalanceModel;
import com.ct.ctexchange.user.model.CreateTransactionRequest;
import com.ct.ctexchange.user.model.CreateUserRequest;
import com.ct.ctexchange.user.model.CreateUserResponse;
import com.ct.ctexchange.user.model.GetTransactionResponse;
import com.ct.ctexchange.user.model.TransactionModel;
import com.ct.ctexchange.user.transaction.Transaction;
import com.ct.ctexchange.user.transaction.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	private final ModelMapper modelMapper;
	private final UserService userService;
	private final TransactionService transactionService;
	private final BalanceService balanceService;

	@PostMapping
	public CreateUserResponse createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
		User user = modelMapper.map(createUserRequest, User.class);
		user = userService.createUser(user);
		log.info("User created: {}", user.getId());
		return modelMapper.map(user, CreateUserResponse.class);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable long id) {
		userService.deleteUser(id);
		log.info("User deleted: {}", id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("{id}/transaction")
	public ResponseEntity<Long> createTransaction(@PathVariable long id,
			@Valid @RequestBody CreateTransactionRequest createTransactionRequest) {

		log.info("{} transaction of {} for amount {} {}.", createTransactionRequest.getType().name(), id,
				createTransactionRequest.getAmount(), createTransactionRequest.getCurrency().name());
		Transaction transaction = transactionService.createTransaction(User.builder().id(id).build(),
				createTransactionRequest.getAmount(), createTransactionRequest.getCurrency(),
				createTransactionRequest.getType());

		return new ResponseEntity<>(transaction.getId(), HttpStatus.OK);
	}

	@GetMapping("{id}/transactions")
	public GetTransactionResponse getTransactions(@PathVariable long id) {
		List<TransactionModel> transactionModels = new ArrayList<>();
		transactionService.getTransactionsByUserId(id)
				.forEach(t -> transactionModels.add(TransactionModel.builder().amount(t.getAmount()).price(t.getPrice())
						.id(t.getId()).currency(t.getCurrency().name()).type(t.getType().name()).build()));

		List<BalanceModel> balanceModels = new ArrayList<>();
		balanceService.getBalancesByUserId(id).forEach(b -> balanceModels
				.add(BalanceModel.builder().amount(b.getAmount()).currency(b.getId().getCurrency().name()).build()));

		return GetTransactionResponse.builder().transactions(transactionModels).balances(balanceModels).build();
	}

}
