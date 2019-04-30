package com.wxh.bootMybatis.serviceimpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wxh.bootMybatis.model.MyString;
import com.wxh.bootMybatis.service.ISendHttp;
import com.wxh.bootMybatis.utils.FileIO;
import com.wxh.bootMybatis.utils.HttpClient;
import com.wxh.bootMybatis.utils.Reflect;

@Service("httpService")
public class SendHttpImpl implements ISendHttp {
	@Autowired
	private HttpClient client;

	@Override
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
					return MyString.ENTER + "Error type!";
				}
			}
			if (StringUtils.isBlank(methodName)) {
				Object[] args = new Object[] { filePath, type };
				return Reflect.invokeMethod(this, "execute", args);
			} else {
				Object[] args = new Object[] { filePath, type, methodName };
				return Reflect.invokeMethod(this, "execute", args);
			}
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
		result = postRequest(requestsMap.get(funcName), type, funcName, filePath);
		if (result.equals(MyString.FAILED)) {
			return MyString.FAILED;
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
		String result = MyString.SUCCEED;
		for (String key : requestsMap.keySet()) {
			result = postRequest(requestsMap.get(key), type, key, filePath);
			if (result.equals(MyString.FAILED)) {
				return MyString.FAILED;
			}
		}
		return result;
	}

	/**
	 * Get the request parameters from the file.
	 * 
	 * @param filePath Full path
	 */
	private Map<String, Map<String, Object>> getRequest(String filePath) {
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
			String data = client.post(request);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			data = df.format(new Date()) + MyString.SPACE + funcName + MyString.ENTER + data + MyString.ENTER;
			FileIO.insertFile(MyString.BLANK, filePath.replace(".txt", "_result.txt"), data + MyString.ENTER);
			return "request: " + request.toString() + MyString.ENTER + "response: " + data;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MyString.FAILED;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MyString.FAILED;
		}
	}

}
