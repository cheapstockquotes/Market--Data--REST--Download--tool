package execute;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import concurrent.ThreadPool;
import objects.Symbol;
import tasks.GetSymbol;
import tasks.GetSymbolHistory;
import tasks.GetSymbolList;

public class ExecuteDownload {

	public static void main(String[] args) {

		try {
			/* get the symbol list */
			System.out.println("Get Symbol List");

			ArrayList<Symbol> symbolList = getSymbols();

			System.out.println("Got Symbol List");

			Thread.sleep(3000);

			System.out.println("The list contains " + symbolList.size() + " Symbols");

			Thread.sleep(3000);

			System.out.println("Here's the list");

			Thread.sleep(3000);

			symbolList.forEach(symbol -> System.out.println(symbol.toString()));

			/* get all quotes from the symbol list */
			System.out.println("Get All Last Trading Day Quotes for each symbol");
			Thread.sleep(3000);
			System.out.println("NOTE: replace demo with your private key.");
			System.out.println("The demo key will always return the aapl symbol regardless of the request symbol.");
			System.out.println("A subscribed key will return the actual symbol data.");

			Thread.sleep(3000);

			getQuotes(symbolList);

			System.out.println("Got All Last Trading Day Quotes for each symbol");

			Thread.sleep(3000);

			/* get all historical quotes from the symbol list */
			System.out.println("Get All Historical Quotes For Every Symbol");
			Thread.sleep(3000);
			System.out.println("NOTE: replace demo with your private key.");
			System.out.println("The demo key will always return the aapl symbol regardless of the request symbol.");
			System.out.println("A subscribed key will return the actual symbol data.");

			Thread.sleep(3000);

			getHistoricalQuotes(symbolList);

			System.out.println("Got All Historical Quotes For Every Symbol");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static ArrayList<Symbol> getSymbols() throws Exception {
		return new GetSymbolList().getSymbols();
	}

	private static void getQuotes(ArrayList<Symbol> symbolList) throws Exception {
		/* creating a pool of 10 connections with a delay of 25ms between execution */
		/* rate limiting for single quotes is 40 quotes/second */
		ThreadPool threadPool = new ThreadPool(10, 25, TimeUnit.MILLISECONDS);
		for (Symbol symbol : symbolList) {
			threadPool.execute(new GetSymbol(symbol));
		}
		threadPool.shutdown();
	}

	private static void getHistoricalQuotes(ArrayList<Symbol> symbolList) throws Exception {
		/* creating a pool of 10 connections with a delay of 50ms between execution */
		/* rate limiting for historical quotes is 20 historical quotes/second */
		ThreadPool threadPool = new ThreadPool(10, 50, TimeUnit.MILLISECONDS);
		for (Symbol symbol : symbolList) {
			threadPool.execute(new GetSymbolHistory(symbol));
		}
		threadPool.shutdown();
	}

}
