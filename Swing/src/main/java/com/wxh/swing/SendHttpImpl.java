package com.wxh.swing;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;

import com.wxh.swing.common.MyError;
import com.wxh.swing.common.MyException;

public class SendHttpImpl {

	private HttpClient client;

	{
		client = new HttpClient();
	}

	/**
	 * @param filePath   Full file path which including the requests.
	 * @param type       1:buss 2:docif 3.workflow.
	 * @param methodName If methodName is null,do all methods.
	 */
	public Object cssmis(String filePath, Integer type, String methodName) {
		//
		try {
			if (StringUtils.isBlank(filePath)) {
				switch (type) {
				case 1:
					filePath = MyString.PATH_REQUEST_BUSS;
					break;
				case 2:
					filePath = MyString.PATH_REQUEST_DOCIF;
					break;
				case 3:
					filePath = MyString.PATH_REQUEST_WORKFLOW;
					break;
				default:
					return "Error type: 1.buss 2.docif 3.workflow";
				}
			}
			if (StringUtils.isBlank(methodName)) {
				Object[] args = new Object[] { filePath, type };
				return Reflect.invokeMethod(this, "execute", args);
			} else {
				Object[] args = new Object[] { filePath, type, methodName };
				return Reflect.invokeMethod(this, "execute", args);
			}
		} catch (NullPointerException e) {
			return "Error type: 1.buss 2.docif 3.workflow";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MyString.FAILED;
		}
	}

	/**
	 * Single Interface
	 * 
	 * @param type     1:buss 2:docif 3.workflow
	 * @param funcName Method Name
	 */
	public String execute(String filePath, Integer type, String funcName) {
		// TODO Auto-generated method stub
		Map<String, Map<String, Object>> requestsMap = getRequest(filePath);
		String result = MyString.SUCCEED;
		try {
			result = postRequest(requestsMap.get(funcName), type, funcName, filePath);
		} catch (MyException e) {
			return e.getMessage();
		} catch (Exception e) {
			return e.getMessage();
		}
		return result;
	}

	/**
	 * All Interface
	 * 
	 * @param type 1:buss 2:docif 3:workflow
	 */
	public String execute(String filePath, Integer type) {
		// TODO Auto-generated method stub
		Map<String, Map<String, Object>> requestsMap = getRequest(filePath);
		String result = MyString.BLANK;
		String errorResult = MyString.BLANK;
		for (String key : requestsMap.keySet()) {
			try {
				result += postRequest(requestsMap.get(key), type, key, filePath) + MyString.ENTER;
			} catch (MyException e) {
				errorResult += e.getMessage() + MyString.ENTER;
			} catch (Exception e) {
				return key + ": " + e.getMessage();
			}
			if (result.contains(MyString.FAILED)) {
				return errorResult + result;
			}
		}
		return errorResult + result;
	}

	/**
	 * Get the request parameters from the file.
	 * 
	 * @param filePath Full path
	 */
	public Map<String, Map<String, Object>> getRequest(String filePath) {
		Map<String, Map<String, Object>> requestsMap = new LinkedHashMap<String, Map<String, Object>>();
		String data = FileIO.readFile(filePath);
		data = data.replace(MyString.TABLE, MyString.BLANK);
		String[] requests = data.split(MyString.ENTER + MyString.ENTER);
		for (String request : requests) {
			String[] params = request.split(MyString.ENTER);
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			for (String param : params) {
				try {
					String key = param.split(MyString.EQUALS)[0];
					String value = param.split(MyString.EQUALS)[1];
					paramsMap.put(key, value);
				} catch (ArrayIndexOutOfBoundsException e) {
				}
			}
			requestsMap.put(params[0], paramsMap);
		}
		return requestsMap;
	}

	/**
	 * @param request  请求参数
	 * @param type     接口类型 1:buss 2:docif 3:workflow
	 * @param funcName 测试接口名
	 * @Date 2019-04-29 pm
	 */
	private String postRequest(Map<String, Object> request, Integer type, String funcName, String filePath) {
		String data = MyString.BLANK;
		try {
			// 拼接字符串，得到完整的URL
			switch (type) {
			case 1:
				client.setUrl(MyString.URL_BUSS + funcName);
				break;
			case 2:
				client.setUrl(MyString.URL_DOCIF + funcName);
				break;
			case 3:
				client.setUrl(MyString.URL_WORKFLOW + funcName);
				break;
			default:
				return MyString.FAILED;
			}

			if (funcName.contains("Del")) {
				data = client.delete(request);
			} else
				data = client.post(request);

			String status = data.substring(data.lastIndexOf("\"status\":"), data.lastIndexOf("}"));
			System.out.println(status);
			if (!status.equals("\"status\":0")) {
				throw new MyException(MyError.RemoteError,
						"request: " + request.toString() + MyString.ENTER + funcName + MyString.BLANK + data);
			}

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			data = df.format(new Date()) + MyString.SPACE + funcName + MyString.ENTER + data + MyString.ENTER;
			FileIO.insertFile(MyString.BLANK, filePath.replace(".txt", "_result.txt"), data + MyString.ENTER);
			return "request: " + request.toString() + MyString.ENTER + "response: " + data;
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return funcName + " " + MyString.FAILED + MyString.ENTER + data;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return e.getMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return e.getMessage();
		}
	}

}
