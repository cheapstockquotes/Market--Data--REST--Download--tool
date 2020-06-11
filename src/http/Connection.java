package http;

public class Connection {
	private RequestObject request;

	public ResponseObject GETResponse(String URL) throws Exception {
		request = new RequestObject(URL);
		try {
			return request.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Connection Error: " + URL);
		} finally {
			request = null;
		}
	}

}
