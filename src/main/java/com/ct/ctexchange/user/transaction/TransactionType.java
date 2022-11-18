package com.ct.ctexchange.user.transaction;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TransactionType {
	BUY, SELL, DEPOSIT, WITHDRAW;

	@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
	public static TransactionType fromText(String text) {
		for (TransactionType t : TransactionType.values()) {
			if (t.name().equals(text)) {
				return t;
			}
		}
		throw new IllegalArgumentException();
	}

	@Override
	public String toString() {
		return this.name();
	}
}
