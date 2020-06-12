
package objects;

public class Symbol {
	private String symbol;
	private String type;
	private String country;
	private String sector;
	private String name;

	public Symbol(String symbol) {
		this.symbol = symbol;
	}

	public Symbol(String symbol, String name, String type, String sector, String country) {
		this.symbol = symbol;
		this.name = name;
		this.type = type;
		this.sector = sector;
		this.country = country;
	}

	public String getSymbol() {
		return symbol;
	}

	@Override
	public String toString() {
		return symbol + " " + name + " " + type + " " + sector + " " + country;
	}

	public String getType() {
		return type;
	}

	public String getCountry() {
		return country;
	}

	public String getSector() {
		return sector;
	}

	public String getName() {
		return name;
	}

}
