package com.wxh.bootMybatis.utils;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.wxh.bootMybatis.model.MyString;

import net.sf.json.JSONObject;

@Component
public class HttpClient {
	private CloseableHttpClient client;
	private String url;

	public HttpClient() {
		client = HttpClients.createDefault();
		url = "http://localhost:10243/CSSMIS/api/buss/Login";
	}

	public String get() throws ClientProtocolException, IOException {
		String result = MyString.BLANK;
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;

		response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			result = EntityUtils.toString(entity, "utf-8");
		}
		EntityUtils.consume(entity);

		if (response != null) {
			response.close();
		}

		return result;
	}

	public String post(Map<String, Object> data) throws ClientProtocolException, IOException {
		String result = MyString.BLANK;
		CloseableHttpResponse response = null;

//		ObjectMapper objectMapper = new ObjectMapper();

		HttpPost httpPost = new HttpPost(url);

		// 头部
		httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
		// body
//		httpPost.setEntity(
//				new StringEntity(objectMapper.writeValueAsString(data), ContentType.create("text/json", "UTF-8")));
		httpPost.setEntity(
				new StringEntity(JSONObject.fromObject(data).toString()));

		// 执行请求，获取响应
		response = client.execute(httpPost);

		/*
		 * 看请求是否成功，这儿打印的是http状态码
		 * System.out.println(response.getStatusLine().getStatusCode());
		 */

		// 4.获取响应的实体内容，就是我们所要抓取得网页内容
		HttpEntity entity = response.getEntity();

		// 5.获取放回结果
		if (entity != null) {
			result = EntityUtils.toString(entity, "utf-8");
		}
		EntityUtils.consume(entity);

		if (response != null) {
			response.close();
		}

		return result;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
