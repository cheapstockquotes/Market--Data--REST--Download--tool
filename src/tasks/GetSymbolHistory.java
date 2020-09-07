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
			 * aapl symbol regardless of the request symbol. A subscribed key will return
			 * the actual symbol data.
			 */
			ResponseObject response = connection.GETResponse("http://www.cheapstockquotes.com/rest/history/" + symbol.getSymbol() + "?key=demo");
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

				double exthigh = jsonObject.get("exthigh").getAsDouble();
				double extlow = jsonObject.get("extlow").getAsDouble();
				long extvolume = jsonObject.get("extvolume").getAsLong();

				double prehigh = jsonObject.get("prehigh").getAsDouble();
				double prelow = jsonObject.get("prelow").getAsDouble();
				long prevolume = jsonObject.get("prevolume").getAsLong();

				double posthigh = jsonObject.get("posthigh").getAsDouble();
				double postlow = jsonObject.get("postlow").getAsDouble();
				long postvolume = jsonObject.get("postvolume").getAsLong();

				symbolHistoricalQuotes.add(new Quote(new Symbol(symbol), date, high, low, open, close, volume, exthigh, extlow, extvolume, prehigh, prelow,
						prevolume, posthigh, postlow, postvolume));
			}

			/* 5) could store or update history database here or write to a file */
			if (symbolHistoricalQuotes.size() > 0)
				symbolHistoricalQuotes.forEach(quote -> System.out.println(quote));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ThreadPool.countDown();
		}
	}

}
