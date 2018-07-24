package com.inspireminds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inspireminds.common.ExchangeValueRequest;
import com.inspireminds.common.ExchangeValueResponse;
import com.inspireminds.service.jms.CurrencyConversionJMSPubSub;

@RestController
public class JMSCurrencyConverterController {

	@Autowired
	private CurrencyConversionJMSPubSub jmsPubSub;
	
	@Autowired
	private Environment environment;
	
	@RequestMapping("/ccsjms")
	public String doTest() {
		System.out.println(String.format("Environment is :%s", environment.getProperty("forex.url")));
		return "welcome to CCS Controller";
		
	}

	
	@RequestMapping(value = "/ccsjms/publish", method = RequestMethod.POST)
	public ExchangeValueResponse jmsPublish(@RequestBody ExchangeValueRequest request) {
		ExchangeValueResponse response = new ExchangeValueResponse();
		try {

			jmsPubSub.publish(request.getExchangeValue());
			response.setStatus(ExchangeValueResponse.Status.SUCCESSFULLY_COMPLETED);
			//response.setExchangeValue(request);

		} catch (Throwable t) {
			System.out.println(String.format("Unable to process your request :%s, error:%s", request, t.getMessage()));
			response.setExchangeValue(request.getExchangeValue());
			response.setStatus(ExchangeValueResponse.Status.FAILED);

		}

		return response;
	}
	
}

