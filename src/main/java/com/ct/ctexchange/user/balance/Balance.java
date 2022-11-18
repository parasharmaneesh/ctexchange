package com.ct.ctexchange.user.balance;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.ct.ctexchange.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Balance {

	@EmbeddedId
	private BalanceId id;

	@MapsId("userId")
	@JoinColumn(name = "userId", referencedColumnName = "id")
	@ManyToOne(cascade = CascadeType.REMOVE)
	private User user;

	private double amount;

}
