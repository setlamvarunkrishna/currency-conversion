package com.inspireminds.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.inspireminds.entity.CurrencyConversionCalculation;
import com.inspireminds.entity.ExchangeValue;

@RestController
public class CurrencyConverterController {
	
	@Autowired
	private Environment environment;

	private RestTemplate restTemplate = new RestTemplate();

	// private Random

	static {
		System.out.println("Currency Controller ---------------");
	}

	@RequestMapping("/ccs")
	public String doTest() {
		System.out.println(String.format("Environment is :%s", environment.getProperty("forex.url")));
		return "welcome to CCS Controller";
		
	}

	@GetMapping("/currency-exchage/from/{from}/to/{to}/amount/{amount}")
	public CurrencyConversionCalculation calculateCcsConversion(@PathVariable String from, @PathVariable String to,
			@PathVariable int amount) {
		System.out.println(String.format("Input from:%s to:%s amount:%s", from, to, amount));

		CurrencyConversionCalculation rtnValue = new CurrencyConversionCalculation(new Long(100));
		try {
			// call forex micro service
			String forexMicroServiceEndpoint = String.format(environment.getProperty("forex.url.from.and.to"),
					//"http://localhost:8080/currency-exchage/from/%s/to/%s",
					from, to);
			System.out.println(String.format("Forex endpoint:%s", forexMicroServiceEndpoint));

			ExchangeValue exchangeValue = restTemplate.getForObject(forexMicroServiceEndpoint, ExchangeValue.class);
			System.out.println(String.format("Forex Mircro service output:%s", exchangeValue));
			
			
			if (exchangeValue != null) {
				rtnValue.setExchangeValue(exchangeValue);
				rtnValue.setAmount(amount);
				rtnValue.calculateTo();
			}else {
				rtnValue.setStatus("Forex service is down");
			}
			// rtnValue = .findByFromAndTo(from, to);
		} catch (Throwable t) {
			System.out.println(String.format("error%s cause%s", t.getMessage(), t.getCause()));
		}
		return rtnValue;
	}
	
	@GetMapping("/currency-exchage/from/{from}/amount/{amount}")
	public List<CurrencyConversionCalculation> calculateCcsConversion(@PathVariable String from,
			@PathVariable int amount) {
		System.out.println(String.format("Input from:%s amount:%s", from, amount));

		List<CurrencyConversionCalculation> resultsList = new ArrayList<CurrencyConversionCalculation>();
		try {
			// call forex micro service
			String forexMicroServiceEndpoint = String.format(environment.getProperty("forex.url.from"),
					from);
			System.out.println(String.format("Forex endpoint:%s", forexMicroServiceEndpoint));

			 
			ResponseEntity<ExchangeValue[]> response = restTemplate.getForEntity(forexMicroServiceEndpoint, ExchangeValue[].class);
			System.out.println(String.format("Forex Mircro service output:%s", response));
			
			List<ExchangeValue> exchangeValue = Arrays.asList(response.getBody());
			
			if(exchangeValue == null || exchangeValue.isEmpty()) {
				return null;
			}
			
			for (ExchangeValue value: exchangeValue) {
				CurrencyConversionCalculation rtnValue = new CurrencyConversionCalculation(new Long(100));
				rtnValue.setExchangeValue(value);
				rtnValue.setAmount(amount);
				rtnValue.calculateTo();
				resultsList.add(rtnValue);
			}
			// rtnValue = .findByFromAndTo(from, to);
		} catch (Throwable t) {
			System.out.println(String.format("error%s cause%s", t.getMessage(), t.getCause()));
		}
		return resultsList;
	}
	
	@GetMapping("/currency-exchage/to/{to}/amount/{amount}")
	public List<CurrencyConversionCalculation> calculateCcsConversionTo(@PathVariable String to,
			@PathVariable int amount) {
		System.out.println(String.format("Input from:%s amount:%s", to, amount));

		List<CurrencyConversionCalculation> resultsList = new ArrayList<CurrencyConversionCalculation>();
		try {
			// call forex micro service
			String forexMicroServiceEndpoint = String.format(environment.getProperty("forex.url.to"),
					to);
			System.out.println(String.format("Forex endpoint:%s", forexMicroServiceEndpoint));

			 
			ResponseEntity<ExchangeValue[]> response = restTemplate.getForEntity(forexMicroServiceEndpoint, ExchangeValue[].class);
			System.out.println(String.format("Forex Mircro service output:%s", response));
			
			List<ExchangeValue> exchangeValue = Arrays.asList(response.getBody());
			
			if(exchangeValue == null || exchangeValue.isEmpty()) {
				return null;
			}
			
			for (ExchangeValue value: exchangeValue) {
				CurrencyConversionCalculation rtnValue = new CurrencyConversionCalculation(new Long(100));
				rtnValue.setExchangeValue(value);
				rtnValue.setAmount(amount);
				rtnValue.calculateFrom();
				resultsList.add(rtnValue);
			}
			// rtnValue = .findByFromAndTo(from, to);
		} catch (Throwable t) {
			System.out.println(String.format("error%s cause%s", t.getMessage(), t.getCause()));
		}
		return resultsList;
	}
}
