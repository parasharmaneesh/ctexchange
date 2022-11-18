package com.ct.ctexchange.user.transaction;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ct.ctexchange.currency.Currency;
import com.ct.ctexchange.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_seq")
	private Long id;

	@JoinColumn(name = "userId", referencedColumnName = "id")
	@ManyToOne
	private User user;

	@Enumerated(EnumType.STRING)
	private Currency currency;

	private double price;

	private double amount;

	@Enumerated(EnumType.STRING)
	private TransactionType type;

}
