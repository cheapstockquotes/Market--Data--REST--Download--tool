package http;

import java.util.concurrent.ConcurrentLinkedQueue;

public class CacheConnections {
	private static ConcurrentLinkedQueue<Connection> connections = new ConcurrentLinkedQueue<Connection>();

	public CacheConnections() {
		for (int i = 0; i < 100; i++)
			connections.add(new Connection());
	}

	public static void put(Connection connection) {
		if (connection != null)
			connections.add(connection);
	}

	public static Connection get() {
		Connection t = connections.poll();
		if (t == null) {
			return new Connection();
		}
		return t;
	}

	public static void removeAll() {
		connections = new ConcurrentLinkedQueue<Connection>();
	}

}
