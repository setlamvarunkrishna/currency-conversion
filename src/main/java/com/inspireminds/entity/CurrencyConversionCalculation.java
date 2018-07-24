package com.inspireminds.entity;

import java.math.BigDecimal;

public class CurrencyConversionCalculation {

	// @Id
	private Long id;

	private ExchangeValue exchangeValue;
	private int amount;
	private BigDecimal calculatedAmount;
	private String status;
	
	public CurrencyConversionCalculation(Long id) {
		this.id = id;
	}

	public CurrencyConversionCalculation(Long id, int amount, ExchangeValue exchangeValue) {
		super();
		this.id = id;
		this.amount = amount;
		this.exchangeValue = exchangeValue;

	}

	public Long getId() {
		return id;
	}

	public BigDecimal getCalculatedAmount() {
		return calculatedAmount;
	}

	public void setCalculatedAmount(BigDecimal calculatedAmount) {
		this.calculatedAmount = calculatedAmount;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ExchangeValue getExchangeValue() {
		return exchangeValue;
	}

	public void setExchangeValue(ExchangeValue exchangeValue) {
		this.exchangeValue = exchangeValue;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void calculateTo() {

		calculatedAmount = new BigDecimal(
				new Double(this.amount * exchangeValue.getConversionMultiple().doubleValue() +  + CurrencyCommission.getCurrentCommission()));
		
	}

	public void calculateFrom() {

		calculatedAmount = new BigDecimal(
				new Double(this.amount / exchangeValue.getConversionMultiple().doubleValue() + CurrencyCommission.getCurrentCommission()));
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		return String.format("Id:%s amount:%s exchageValue:%s calculatedAmount:%s status:%s", this.getId().toString(),
				this.exchangeValue, this.amount, calculatedAmount, this.status);
	}



	
}
