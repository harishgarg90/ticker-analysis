Assignment:

Create REST / JSON API to display top 5 day over day percent moves by absolute value for specified stocks.

Endpoint: <host>:<port>/sampleAPI/analysis
Parameters:  	tickers – comma delimited list of stock tickers (any valid stock ticker)
		range – time range (1 month to 2 years:  1mo,3mo,6mo,1y,2y)
Endpoint Example: http://localhost/sampleAPI/analysis?tickers=MSFT,C,F&range=3mo

Return data format:

[{ticker:"MSFT",data:[{date:"2020-10-15",move:0},{date:"2020-11-24",move:0.00467338764},{date:"2020-12-04",move:-0.03265876284},{date:"2020-12-14",move:-0.03267603475},{date:"2020-10-21",move:0.041893467945}]},{ticker:"C",data:[{date:"2020-10-22",move:0},{date:"2020-11-24",move:0},{date:"2020-12-09",move:0},{date:"2020-12-15",move:0},{date:"2020-11-09",move:0.0209952484438}]},{ticker:"T",data:[{date:"2020-09-24",move:0},{date:"2020-10-27",move:0},{date:"2020-10-02",move:-0.0348660346442},{date:"2020-09-30",move:-0.0701478705665},{date:"2020-12-14",move:0.0982019227323}]}]
 
URL to get prices / dates:    https://query1.finance.yahoo.com/v7/finance/chart/AAPL?range=3mo&interval=1d&indicators=quote&includeTimestamps=true

Please note:  the API provides prices (use adjQuote values).  To calculate day over day percent move, where day1$ -- day 1 price/ day2$ -- day2 price:  100* (day2$/day1$-1)  

Requirements:
1.	Create SpringBoot Application using Controller/Service pattern
2.	Use Maven pom configuration to generate WAR (web-archive) package for deployment on a J2EE server
3.	Use Spring Security to support Basic Authentication to access API Endpoint.  Use user.properties resource file to store username / passwords.  
4.	Commit your code/configuration to a new github project. Email the link to the project when completed.



