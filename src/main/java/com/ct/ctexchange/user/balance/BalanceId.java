package com.ct.ctexchange.user.balance;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.ct.ctexchange.currency.Currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class BalanceId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3696944894039469550L;

	@Getter
	private Long userId;

	@Enumerated(EnumType.STRING)
	private Currency currency;

}
