package com.ct.ctexchange.user.balance;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface BalanceRepository extends CrudRepository<Balance, BalanceId> {

	List<Balance> findAllByIdUserId(long id);
	
}
