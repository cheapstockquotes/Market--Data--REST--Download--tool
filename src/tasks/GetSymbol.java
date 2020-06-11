package tasks;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import concurrent.ThreadPool;
import http.CacheConnections;
import http.Connection;
import http.ResponseObject;
import objects.Quote;
import objects.Symbol;

/**
 * Runnable task to get a specific symbol
 */
public class GetSymbol implements Runnable {
	private Symbol symbol;

	public GetSymbol(Symbol symbol) {
		this.symbol = symbol;
	}

	@Override
	public void run() {

		try {
			/* 1) Get a connection object from the cache */
			Connection connection = CacheConnections.get();

			/* 2) Make a REST call to obtain the latest symbol quote data */
			/*
			 * NOTE: replace demo with your private key. The demo key will always return the
			 * aapl symbol regardless of the request symbol
			 */
			ResponseObject response = connection.GETResponse("http://www.cheapstockquotes.com/rest/quote/" + symbol + "?key=demo");
			CacheConnections.put(connection);

			String data = response.getPageData();

			/* 3) Use gson to parse the JSON */
			JsonParser parser = new JsonParser();
			JsonArray jsonArray = (JsonArray) parser.parse(data);

			if (jsonArray.size() == 0) {
				return;
			}
			JsonObject jsonObject = (JsonObject) jsonArray.get(0);

			String symbol = jsonObject.get("symbol").getAsString();
			long date = jsonObject.get("datetime").getAsLong();
			double high = jsonObject.get("high").getAsDouble();
			double low = jsonObject.get("low").getAsDouble();
			double open = jsonObject.get("open").getAsDouble();
			double close = jsonObject.get("close").getAsDouble();
			long volume = jsonObject.get("volume").getAsLong();

			/* 4) Store the HLOCV in a Quote object for return */
			Quote quote = new Quote(new Symbol(symbol), date, high, low, open, close, volume);

			/* 5) Could store symbol quote data here in a database or file */
			System.out.println(quote);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ThreadPool.countDown();
		}
	}

}
