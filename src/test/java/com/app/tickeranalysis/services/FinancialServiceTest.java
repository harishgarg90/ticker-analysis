package com.app.tickeranalysis.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.app.tickeranalysis.config.FinancialApiConfiguration;
import com.app.tickeranalysis.model.Range;

@RunWith(SpringRunner.class)
public class FinancialServiceTest {
	
	@MockBean
	private RestTemplate restTemplate;
	
	@MockBean
	private FinancialApiConfiguration financialApiConfiguration;

	private FinancialService financialService;
	
	@Before
	public void setup() {
		financialService = new FinancialService(restTemplate,financialApiConfiguration);
	}

	@Test
	public void test_getFinancialData() {
		Mockito.when(financialApiConfiguration.getBaseUrl()).thenReturn("TEST_URL");
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn("Financial api response.");
		String response = financialService.getFinancialData("ABC", Range.ONE_MONTH);
		Assert.assertEquals("Financial api response.", response);
	}
	
	@After
	public void cleanup() {
		restTemplate = null;
		financialApiConfiguration = null;
		financialService = null;
	}

}
