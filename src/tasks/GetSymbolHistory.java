package tasks;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import concurrent.ThreadPool;
import http.CacheConnections;
import http.Connection;
import http.ResponseObject;
import objects.Quote;
import objects.Symbol;

/**
 * Get a complete daily history for a specific symbol
 */
public class GetSymbolHistory implements Runnable {
	private Symbol symbol;

	public GetSymbolHistory(Symbol symbol) {
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
			ResponseObject response = connection.GETResponse("http://www.cheapstockquotes.com/rest/history/" + symbol + "?key=demo");
			CacheConnections.put(connection);

			String data = response.getPageData();

			/* 3) Use gson to parse the JSON */
			JsonParser parser = new JsonParser();
			JsonArray jsonArray = (JsonArray) parser.parse(data);
			ArrayList<Quote> symbolHistoricalQuotes = new ArrayList<Quote>();

			/*
			 * 4) Iterate each record to obtain each daily HLOCV and date and store in an
			 * ArrayList<Quote>
			 */
			for (JsonElement jsonElement : jsonArray) {

				JsonObject jsonObject = (JsonObject) jsonElement;

				String symbol = jsonObject.get("symbol").getAsString();
				long date = jsonObject.get("datetime").getAsLong();
				double high = jsonObject.get("high").getAsDouble();
				double low = jsonObject.get("low").getAsDouble();
				double open = jsonObject.get("open").getAsDouble();
				double close = jsonObject.get("close").getAsDouble();
				long volume = jsonObject.get("volume").getAsLong();
				symbolHistoricalQuotes.add(new Quote(new Symbol(symbol), date, high, low, open, close, volume));
			}

			/* 5) could store or update history database here or write to a file */
			if (symbolHistoricalQuotes.size() > 0)
				symbolHistoricalQuotes.forEach(quote -> System.out.println(quote.toString()));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ThreadPool.countDown();
		}
	}

}