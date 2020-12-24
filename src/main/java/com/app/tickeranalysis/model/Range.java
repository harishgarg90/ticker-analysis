package com.app.tickeranalysis.model;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public enum Range {

	ONE_MONTH("1mo"), THREE_MONTH("3mo"), SIX_MONTH("6mo"), ONE_YEAR("1y"), TWO_YEAR("2y");
	

	Range(final String value) {
		this.value = value;
	}

	private String value;

	private static final Map<String, Range> ENUM_MAP;
	
	static {
		Map<String, Range> map = new ConcurrentHashMap<String, Range>();
		for (Range instance : Range.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static Range get(String name) {
		return ENUM_MAP.get(name);
	}
	
	public static String getValidValues(){
		return ENUM_MAP.keySet().stream().collect(Collectors.joining(","));
	}

}
