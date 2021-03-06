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
			 * aapl symbol regardless of the request symbol. A subscribed key will return
			 * the actual symbol data.
			 */
			ResponseObject response = connection.GETResponse("http://www.cheapstockquotes.com/rest/quote/" + symbol.getSymbol() + "?key=demo");
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
			double prevClose = jsonObject.get("previousclose").getAsDouble();
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

			/* 4) Store the HLOCV and Extended hours in a Quote object for return */
			Quote quote = new Quote(new Symbol(symbol), date, high, low, open, close, prevClose, volume, exthigh, extlow, extvolume, prehigh, prelow,
					prevolume, posthigh, postlow, postvolume);

			/* 5) Could store symbol quote data here in a database or file */
			System.out.println(quote);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ThreadPool.countDown();
		}
	}

}
