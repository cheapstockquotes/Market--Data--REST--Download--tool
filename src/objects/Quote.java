
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

	private double prehigh;
	private double prelow;
	private long prevolume;

	private double posthigh;
	private double postlow;
	private long postvolume;

	/**
	 * This constructor is used for a single quote. A single quote has a previous
	 * price included as, unlike historical quotes, the previous price cannot be
	 * interpolated from a previous record.
	 */
	public Quote(Symbol symbol, long date, double high, double low, double open, double close, double prevClose, long volume, double exthigh,
			double extlow, long extvolume, double prehigh, double prelow, long prevolume, double posthigh, double postlow, long postvolume) {
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
		this.prehigh = prehigh;
		this.prelow = prelow;
		this.prevolume = prevolume;
		this.posthigh = posthigh;
		this.postlow = postlow;
		this.postvolume = postvolume;
	}

	/**
	 * This constructor is used for historical quotes listing. There are not
	 * previous closing prices in historical quotes. Previous price can be
	 * interpolated by the previous record.
	 */
	public Quote(Symbol symbol, long date, double high, double low, double open, double close, long volume, double exthigh, double extlow, long extvolume,
			double prehigh, double prelow, long prevolume, double posthigh, double postlow, long postvolume) {
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
		this.prehigh = prehigh;
		this.prelow = prelow;
		this.prevolume = prevolume;
		this.posthigh = posthigh;
		this.postlow = postlow;
		this.postvolume = postvolume;
	}

	@Override
	public String toString() {
		if (prevClose == 0)
			return symbol.getSymbol() + " " + new Date(dateTime) + " " + high + " " + low + " " + open + " " + close + " " + " " + " " + volume + " "
					+ exthigh + " " + extlow + " " + extvolume + " " + prehigh + " " + prelow + " " + prevolume + " " + posthigh + " " + postlow + " "
					+ postvolume + " " + System.lineSeparator();
		else
			return symbol.getSymbol() + " " + new Date(dateTime) + " " + high + " " + low + " " + open + " " + close + " " + " " + prevClose + " " + volume
					+ " " + exthigh + " " + extlow + " " + extvolume + " " + prehigh + " " + prelow + " " + prevolume + " " + posthigh + " " + postlow
					+ " " + postvolume + " " + System.lineSeparator();
	}

}
