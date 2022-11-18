package com.ct.ctexchange.exception;

public enum ErrorMessage {
	USER_NOT_FOUND("User does not exist."),
	NOT_SUFFICIENT_BALANCE("Not sufficient balance to complete this transaction.");

	private String msg;

	private ErrorMessage(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
}
