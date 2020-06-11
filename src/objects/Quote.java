
package objects;

public class Quote {
	private Symbol symbol;
	private long date;
	private double high;
	private double low;
	private double open;
	private double close;
	private long volume;

	public Quote(Symbol symbol, long date, double high, double low, double open, double close, long volume) {
		this.symbol = symbol;
		this.date = date;
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.volume = volume;
	}

	public String getSymbol() {
		return symbol.getSymbol();
	}

	public long getDate() {
		return date;
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
		return symbol + " " + date + " " + high + " " + low + " " + open + " " + close + " " + volume;
	}

}
