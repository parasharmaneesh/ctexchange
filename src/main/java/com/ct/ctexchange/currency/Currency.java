package com.ct.ctexchange.currency;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Currency {

	USD, BTC, NONE;

	@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
	public static Currency fromText(String text) {
		for (Currency c : Currency.values()) {
			if (c.name().equals(text)) {
				return c;
			}
		}
		throw new IllegalArgumentException();
	}

	@Override
	public String toString() {
		return this.name();
	}
}
