package com.inspireminds.common;

import com.inspireminds.entity.ExchangeValue;

public class ExchangeValueResponse {

	private ExchangeValue exchangeValue;
	private Status status;

	public enum Status {
		SUCCESSFULLY_COMPLETED, FAILED;
	}

	public ExchangeValue getExchangeValue() {
		return exchangeValue;
	}

	public void setExchangeValue(ExchangeValue exchangeValue) {
		this.exchangeValue = exchangeValue;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
