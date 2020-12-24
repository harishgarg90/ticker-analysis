package com.app.tickeranalysis.services;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.tickeranalysis.model.Range;
import com.app.tickeranalysis.model.TickerAnanlysisResult;

@RunWith(SpringRunner.class)
public class TickerAnalysisServiceTest {
	
	private final String SAMPLE_RESPONSE = "{\"chart\":{\"result\":[{\"timestamp\":[1600954200,1601040600,1601299800,1601386200,1601472600,1601559000,1601645400,1601904600,1601991000,1602077400,1602163800,1602250200,1602509400,1602595800,1602682200,1602768600,1602855000,1603114200,1603200600,1603287000,1603373400,1603459800,1603719000,1603805400,1603891800,1603978200,1604064600,1604327400,1604413800,1604500200,1604586600,1604673000,1604932200,1605018600,1605105000,1605191400,1605277800,1605537000,1605623400,1605709800,1605796200,1605882600,1606141800,1606228200,1606314600,1606487400,1606746600,1606833000,1606919400,1607005800,1607092200,1607351400,1607437800,1607524200,1607610600,1607697000,1607956200,1608042600,1608129000,1608215400,1608301800,1608561000,1608647400,1608757201],\"indicators\":{\"adjclose\":[{\"adjclose\":[202.659423828125,207.27734375,208.8931121826172,206.7187957763672,209.78077697753906,211.9052276611328,205.65159606933594,209.83065795898438,205.37232971191406,209.28208923339844,210.03013610839844,215.24647521972656,220.82186889648438,222.27806091308594,220.2832794189453,219.08642578125,219.08642578125,213.66062927246094,214.08949279785156,214.23912048339844,214.32887268066406,215.66537475585938,209.53143310546875,212.6931610107422,202.1507568359375,204.1854248046875,201.94131469726562,201.80166625976562,205.89096069335938,215.824951171875,222.70693969726562,223.1358184814453,217.81973266601562,210.45899963378906,215.9845428466797,214.87744140625,215.94464111328125,216.6627655029297,213.90000915527344,211.0800018310547,212.4199981689453,210.38999938964844,210.11000061035156,213.86000061035156,213.8699951171875,215.22999572753906,214.07000732421875,216.2100067138672,215.3699951171875,214.24000549316406,214.36000061035156,214.2899932861328,216.00999450683594,211.8000030517578,210.52000427246094,213.25999450683594,214.1999969482422,214.1300048828125,219.27999877929688,219.4199981689453,218.58999633789062,222.58999633789062,223.94000244140625,221.02000427246094]}]}}],\"error\":null}}";

	@Mock
	private YahooFinancialAPIService financialService;
	
	private TickerAnalysisService dataService;

	@Before
	public void setup() {
		dataService = new TickerAnalysisService(financialService);
	}

	@Test
	public void test_getData() throws IOException {
		// for single ticker
		Mockito.when(financialService.getFinancialData("MSFT", Range.THREE_MONTH)).thenReturn(SAMPLE_RESPONSE);
		List<TickerAnanlysisResult> output = dataService.getData(new String[] { "MSFT" }, Range.THREE_MONTH);
		Assert.assertEquals(5, output.get(0).getData().size());
		Assert.assertEquals("MSFT", output.get(0).getTicker());
		
		Mockito.when(financialService.getFinancialData("C", Range.THREE_MONTH)).thenReturn(SAMPLE_RESPONSE);
		Mockito.when(financialService.getFinancialData("T", Range.THREE_MONTH)).thenReturn(SAMPLE_RESPONSE);
		List<TickerAnanlysisResult> output2 = dataService.getData(new String[] { "C","T" }, Range.THREE_MONTH);
		Assert.assertEquals(5, output2.get(0).getData().size());
		Assert.assertEquals("C", output2.get(0).getTicker());
		Assert.assertEquals(5, output2.get(1).getData().size());
		Assert.assertEquals("T", output2.get(1).getTicker());
	}
	
	@Test(expected=IOException.class)
	public void test_getData_ForException() throws IOException {
		Mockito.when(financialService.getFinancialData("MSFT", Range.THREE_MONTH)).thenReturn("{\"chart\":{\"result\":[],\"error\":null}");
		dataService.getData(new String[] { "MSFT" }, Range.THREE_MONTH);
	}

	@After
	public void cleanup() {
		dataService = null;
		financialService = null;
	}

}
