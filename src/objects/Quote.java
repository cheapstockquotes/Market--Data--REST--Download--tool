
package objects;

import java.util.Date;

public class Quote {
	private Symbol symbol;
	private long dateTime;
	private double high;
	private double low;
	private double open;
	private double close;
	private double prevClose = 0;
	private long volume;
	private double exthigh;
	private double extlow;
	private long extvolume;

	/**
	 * This constructor is used for a single quote. A single quote has a previous
	 * price included as, unlike historical quotes, the previous price cannot be
	 * interpolated from a previous record.
	 */
	public Quote(Symbol symbol, long date, double high, double low, double open, double close, double prevClose, long volume, double exthigh,
			double extlow, long extvolume) {
		this.symbol = symbol;
		this.dateTime = date;
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.volume = volume;
		this.prevClose = prevClose;
		this.exthigh = exthigh;
		this.extlow = extlow;
		this.extvolume = extvolume;
	}

	/**
	 * This constructor is used for historical quotes listing. There are not
	 * previous closing prices in historical quotes. Previous price can be
	 * interpolated by the previous record.
	 */
	public Quote(Symbol symbol, long date, double high, double low, double open, double close, long volume, double exthigh, double extlow,
			long extvolume) {
		this.symbol = symbol;
		this.dateTime = date;
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.volume = volume;
		this.exthigh = exthigh;
		this.extlow = extlow;
		this.extvolume = extvolume;
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

	public double getExthigh() {
		return exthigh;
	}

	public double getExtlow() {
		return extlow;
	}

	public long getExtvolume() {
		return extvolume;
	}

	@Override
	public String toString() {
		if (prevClose == 0)
			return symbol.getSymbol() + " " + new Date(dateTime) + " " + high + " " + low + " " + open + " " + close + " " + " " + " " + volume + " "
					+ exthigh + " " + extlow + " " + extvolume + " " + System.lineSeparator();
		else
			return symbol.getSymbol() + " " + new Date(dateTime) + " " + high + " " + low + " " + open + " " + close + " " + " " + prevClose + " " + volume
					+ " " + exthigh + " " + extlow + " " + extvolume + " " + System.lineSeparator();
	}

}
