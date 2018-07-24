package com.inspireminds.entity;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(objectName = "com.inspireminds.entity:name=CurrencyCommission", description = "CurrencyCommission.")
public class CurrencyCommission {

	private static int commission = 10;

	public int getCommission() {
		return commission;
	}

	@ManagedOperation(description = "Set new commission")
	@ManagedOperationParameters({
			@ManagedOperationParameter(name = "commission", description = "new commission value") })
	public void setCommission(int commission) {
		this.commission = commission;
	}

	public static int getCurrentCommission() {
		return commission;
	}
}
