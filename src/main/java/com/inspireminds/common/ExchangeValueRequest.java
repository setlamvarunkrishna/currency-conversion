package com.inspireminds.common;

import com.inspireminds.entity.ExchangeValue;

public class ExchangeValueRequest {

	private ExchangeValue exchangeValue;
	private RequestType type;

	private enum RequestType {
		ADD, REPLACE, REMOVE;
	}

	public ExchangeValue getExchangeValue() {
		return exchangeValue;
	}

	public void setExchangeValue(ExchangeValue exchangeValue) {
		this.exchangeValue = exchangeValue;
	}

	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}

}
