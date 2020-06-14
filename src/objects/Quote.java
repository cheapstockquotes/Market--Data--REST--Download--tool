
package objects;

import java.util.Date;

public class Quote {
	private Symbol symbol;
	private long dateTime;
	private double high;
	private double low;
	private double open;
	private double close;
	private double prevClose;
	private long volume;

	public Quote(Symbol symbol, long date, double high, double low, double open, double close, double prevClose, long volume) {
		this.symbol = symbol;
		this.dateTime = date;
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.volume = volume;
		this.prevClose = prevClose;
	}

	public String getSymbol() {
		return symbol.getSymbol();
	}

	public long getDate() {
		return dateTime;
	}

	public double getOpen() {
		return open;
	}

	public double getLow() {
		return low;
	}

	public double getHigh() {
		return high;
	}

	public double getClose() {
		return close;
	}

	public long getVolume() {
		return volume;
	}

	@Override
	public String toString() {
		return symbol.getSymbol() + " " + new Date(dateTime) + " " + high + " " + low + " " + open + " " + close + " " + " " + prevClose + " " + volume
				+ System.lineSeparator();
	}

}
