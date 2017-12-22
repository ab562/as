package com.as.biz;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;


@Component("myHttpClient")
public class MyHttpClient {
	private RequestConfig requestConfig =RequestConfig.custom()
			.setSocketTimeout(15000)
			.setConnectTimeout(15000)
			.setConnectionRequestTimeout(15000)
			.build();
	public String doPost(String url,Map<String, String> hander,String param ){
		CloseableHttpClient httpClient=null;
		CloseableHttpResponse response=null;
		HttpEntity entity=null;
		String responseContent=null;
		HttpPost httpPost=null;
		try {
			httpPost = new HttpPost();
			URI uri=new URI(url);
			httpPost.setURI(uri);
			//设置请求头
			if(hander!=null){
				for(Entry<String, String> h:hander.entrySet()){
					httpPost.setHeader(h.getKey(), h.getValue());
				}
			}
			StringEntity stringEntity = new StringEntity(param, "UTF-8");  
            stringEntity.setContentType("application/x-www-form-urlencoded");  
            httpPost.setEntity(stringEntity);
        	httpClient=HttpClients.createDefault();
			httpPost.setConfig(requestConfig);
			//执行请求
			response=httpClient.execute(httpPost);
			//取出响应内容
			entity=response.getEntity();
			responseContent=EntityUtils.toString(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(response!=null){
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(httpClient!=null){
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return responseContent;
	}
	public String doGet(String url,Map<String, String> hander){
		CloseableHttpClient httpClient=null;
		CloseableHttpResponse response=null;
		HttpEntity entity=null;
		String responseContent=null;
		HttpGet httpGet=null;
		try {
			httpGet=new HttpGet();
			URI uri=new URI(url);
			httpGet.setURI(uri);
			//设置请求头
			if(hander!=null){
				for(Entry<String, String> h:hander.entrySet()){
					httpGet.setHeader(h.getKey(), h.getValue());
				}
			}
			httpClient=HttpClients.createDefault();
			httpGet.setConfig(requestConfig);
			//执行请求
			response=httpClient.execute(httpGet);
			//取出响应内容
			entity=response.getEntity();
			responseContent=EntityUtils.toString(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(response!=null){
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(httpClient!=null){
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return responseContent;
	}

	
}
