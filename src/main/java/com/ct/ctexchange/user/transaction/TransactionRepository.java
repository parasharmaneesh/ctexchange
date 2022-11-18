package com.ct.ctexchange.user.transaction;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

	List<Transaction> findAllByUserId(long id);

}
