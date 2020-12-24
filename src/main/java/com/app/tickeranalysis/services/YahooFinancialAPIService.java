package com.app.tickeranalysis.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.tickeranalysis.config.FinancialApiConfiguration;
import com.app.tickeranalysis.model.Range;

@Service
public class YahooFinancialAPIService {
	
	private static final String FINANCIAL_API_CONS_PARAM = "&interval=1d&indicators=quote&includeTimestamps=true";

	private final RestTemplate restTemplate;
	
	private final FinancialApiConfiguration financialApiConfiguration;
	
	public YahooFinancialAPIService(final RestTemplate restTemplate, final FinancialApiConfiguration financialApiConfiguration) {
		this.restTemplate = restTemplate;
		this.financialApiConfiguration = financialApiConfiguration;
	}

	public String getFinancialData(final String ticker, final Range range) {
		StringBuilder url = new StringBuilder(financialApiConfiguration.getBaseUrl());
		url.append(ticker).append("?range=").append(range.getValue()).append(FINANCIAL_API_CONS_PARAM);
		
		return restTemplate.getForObject(url.toString(), String.class);
	}
	
}
