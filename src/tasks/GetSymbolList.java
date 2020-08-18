package tasks;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import http.CacheConnections;
import http.Connection;
import http.ResponseObject;
import objects.Symbol;

/**
 * Get the list of symbols in the respository.
 */
public class GetSymbolList {

	public ArrayList<Symbol> getSymbols() throws Exception {
		ArrayList<Symbol> symbols = new ArrayList<Symbol>();

		/* 1) Get a connection object from the cache */
		Connection connection = CacheConnections.get();

		/* 2) Make a REST call to obtain all available symbols */
		/* NOTE: replace demo with your private key */
		ResponseObject response = connection.GETResponse("http://www.cheapstockquotes.com/rest/symbols?key=demo");
		CacheConnections.put(connection);

		String data = response.getPageData();

		/* 3) Use gson to parse the JSON */
		JsonParser parser = new JsonParser();
		JsonArray jsonArray = (JsonArray) parser.parse(data);

		/* 4) Store symbols in an ArrayList for return */
		for (JsonElement jsonElement : jsonArray) {
			JsonObject jsonObject = (JsonObject) jsonElement;
			symbols.add(new Symbol(jsonObject.get("symbol").getAsString(), jsonObject.get("type").getAsString(), jsonObject.get("name").getAsString()));
		}
		return symbols;
	}
}
