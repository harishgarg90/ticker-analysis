package com.app.tickeranalysis.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.tickeranalysis.model.Range;
import com.app.tickeranalysis.model.TickerAnanlysisResult;
import com.app.tickeranalysis.model.TickerMoveData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DataService{
	
	private final BigDecimal MULTIPLICATION_FACTOR = new BigDecimal(100.0);
	private final BigDecimal SUBSTRACTION_FACTOR = new BigDecimal(1.0);
	private final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
	private final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
	
	private final FinancialService financialService;
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	public DataService(final FinancialService financialService) {
		this.financialService = financialService;
	}

	public List<TickerAnanlysisResult> getData(final String[] tickers, final Range range) throws IOException{
		List<TickerAnanlysisResult> output = new ArrayList<TickerAnanlysisResult>();
		for(String ticker : tickers) {
			output.add(this.getResult(ticker, range));
		}
		return output;
	}

	private TickerAnanlysisResult getResult(final String ticker, final Range range) throws IOException {
		TickerAnanlysisResult result = new TickerAnanlysisResult();
		final String json = financialService.getFinancialData(ticker, range);
		JsonNode resultNode = objectMapper.readTree(json).get("chart").get("result").get(0);
		JsonNode timestamps = resultNode.get("timestamp");
		JsonNode indicators = resultNode.get("indicators");
		JsonNode adjCloses = indicators.get("adjclose").get(0).get("adjclose");

		List<TickerMoveData> data = new ArrayList<TickerMoveData>();

		for (int i = 0; i < timestamps.size(); i++) {

			int j = i + 1;
			if (j >= timestamps.size())
				break;

			long timestamp = timestamps.get(i).asLong();
			Date date = new Date(timestamp * 1000);
			BigDecimal adjClose = adjCloses.get(i).decimalValue();
			BigDecimal adjCloseForNextDay = adjCloses.get(j).decimalValue();
			BigDecimal percentMove = adjCloseForNextDay.divide(adjClose, RoundingMode.HALF_UP)
					.subtract(SUBSTRACTION_FACTOR).multiply(MULTIPLICATION_FACTOR);

			// collect all the differences
			data.add(new TickerMoveData(dateFormat.format(date), percentMove));

		}

		// sort data based on percent move absolute value
		Collections.sort(data, (day1Val, day2Val) -> {
			BigDecimal percentMove1 = day1Val.getMove().abs();
			BigDecimal percentMove2 = day2Val.getMove().abs();
			return percentMove1.compareTo(percentMove2);
		});

		// collect top 5 percent move
		List<TickerMoveData> filteredData = data.stream().limit(5).collect(Collectors.toList());

		result.setData(filteredData);
		result.setTicker(ticker);

		return result;
	}
	
}
