package http;

import java.util.List;
import java.util.Map;

public class ResponseObject {
	private RequestObject requestObject;
	private int returnCode;
	private boolean isReturnCodeSet;

	public ResponseObject(RequestObject requestObject) {
		this.requestObject = requestObject;
	}

	public int getReturnCode() {
		if (isReturnCodeSet) {
			return returnCode;
		}
		Map<String, List<String>> map = null;
		try {
			map = requestObject.getHeaderFields();
		} catch (Exception e) {
			return returnCode;// if there is an error return code of 0 should prevail.
		}
		if (map.size() != 0) {
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				if (entry.getKey() == null && entry.getValue() != null) {
					if (entry.getValue().toString().contains("HTTP/1.1")) {
						String[] responseString = entry.getValue().toString().split(" ");
						if (responseString.length == 3) {
							try {
								isReturnCodeSet = true;// we tried. May not have succeeded but we tried.
								returnCode = Integer.valueOf(responseString[1]);
								break;
							} catch (Exception e) {
								// do nothing. return code of 0 should prevail.
							}
						} else {
							System.out.print("for testing");
							break;
						}
					}
				}
			}
		}
		return returnCode;
	}

	/*
	 * we should only need to retrieve this once so storing a boolean and creating a
	 * string would be overhead
	 */
	public String getPageData() {
		if (requestObject == null) {
			return "";
		}
		return requestObject.getPageData();
	}

	public RequestObject getRequestObject() {
		return requestObject;
	}

}
