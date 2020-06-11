package http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestObject {
	private InputStreamReader inputStreamReader = null;
	private BufferedReader bufferedReader = null;
	private HttpURLConnection connection = null;
	private URL url = null;
	private StringBuilder builder;
	private Map<String, String> requestProperties = new HashMap<String, String>();

	public RequestObject(String URL) throws Exception {
		this.url = new URL(URL);
	}

	public void addRequestProperty(String key, String value) {
		requestProperties.put(key, value);
	}

	public String getPageData() {
		if (builder == null)
			return "";
		return builder.toString();
	}

	public ResponseObject execute() throws Exception {
		createConnection();
		String line = null;
		line = bufferedReader.readLine();
		builder = new StringBuilder();
		while (line != null) {
			builder.append(line);
			line = bufferedReader.readLine();
		}
		closeConnection();
		return new ResponseObject(this);
	}

	private void createConnection() throws Exception {
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(240000);
		connection.setReadTimeout(240000);
		connection.setDefaultUseCaches(false);
		connection.setUseCaches(false);
		for (Map.Entry<String, String> entry : requestProperties.entrySet())
			connection.addRequestProperty(entry.getKey(), entry.getValue());
		inputStreamReader = new InputStreamReader(connection.getInputStream());
		bufferedReader = new BufferedReader(inputStreamReader);
	}

	private void closeConnection() {
		try {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		} catch (Exception e) {
		}
		try {
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
		} catch (Exception e) {
		}
		try {
			if (connection != null) {
				connection.disconnect();
			}
		} catch (Exception e) {
		}
	}

	public Map<String, List<String>> getHeaderFields() {
		return connection.getHeaderFields();
	}

}
