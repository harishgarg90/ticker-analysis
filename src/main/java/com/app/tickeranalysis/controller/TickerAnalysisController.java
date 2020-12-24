package com.app.tickeranalysis.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.app.tickeranalysis.model.Range;
import com.app.tickeranalysis.model.TickerAnanlysisResult;
import com.app.tickeranalysis.services.TickerAnalysisService;

@RestController
public class TickerAnalysisController {
	
	private final TickerAnalysisService tickerAnalysisService;
	
	public TickerAnalysisController(final TickerAnalysisService tickerAnalysisService) {
		this.tickerAnalysisService = tickerAnalysisService;
	}
	
	@GetMapping("/sampleAPI/analysis")
	public ResponseEntity<List<TickerAnanlysisResult>> getData(@RequestParam("tickers") @NotEmpty final String tickers,
			@RequestParam("range") @NotEmpty final String range) {

		final String[] tickerArr = StringUtils.split(tickers, ",");
		Range rangeEnum = Range.get(range);

		if (rangeEnum == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Not a valid range. Value should be one of : " + Range.getValidValues());

		try {
			List<TickerAnanlysisResult> outputResult = tickerAnalysisService.getData(tickerArr, rangeEnum);
			return new ResponseEntity<>(outputResult, HttpStatus.OK);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Not able to parse response from yahoo finance api due to :" + e.getMessage());
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Something went wrong while processing the request :  " + ex.getMessage());
		}
	}
	
}
