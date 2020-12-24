package com.app.tickeranalysis.model;

import java.math.BigDecimal;

public class TickerMoveData{

	private String date;
	private BigDecimal move;

	public TickerMoveData(final String date, final BigDecimal move) {
		this.date = date;
		this.move = move;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public BigDecimal getMove() {
		return move;
	}

	public void setMove(BigDecimal move) {
		this.move = move;
	}

}
