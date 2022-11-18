package com.ct.ctexchange.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.ct.ctexchange.user.balance.Balance;
import com.ct.ctexchange.user.transaction.Transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
	private Long id;

	@NotNull
	private String name;

	@NotNull
	@Email
	private String email;

	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "user")
	List<Balance> balances;
	
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "user")
	List<Transaction> transactions;

}
