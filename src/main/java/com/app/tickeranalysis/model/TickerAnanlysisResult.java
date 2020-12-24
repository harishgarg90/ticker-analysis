package com.app.tickeranalysis.model;

import java.util.List;

public class TickerAnanlysisResult {

	private String ticker;
	private List<TickerMoveData> data;

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public List<TickerMoveData> getData() {
		return data;
	}

	public void setData(List<TickerMoveData> data) {
		this.data = data;
	}

}

