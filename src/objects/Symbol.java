
package objects;

public class Symbol {
	private String symbol;
	private String type;
	private String name;

	public Symbol(String symbol) {
		this.symbol = symbol;
	}

	public Symbol(String symbol, String type, String name) {
		this.symbol = symbol;
		this.name = name;
		this.type = type;
	}

	public String getSymbol() {
		return symbol;
	}

	@Override
	public String toString() {
		return symbol + " " + type + " " + name;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

}
