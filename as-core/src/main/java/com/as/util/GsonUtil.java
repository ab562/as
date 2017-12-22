package com.as.util;



import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
/**
 * Gson转换工具类
 * @author yang
 *
 */
public class GsonUtil {
	private static Logger log = LoggerFactory.getLogger(GsonUtil.class);
	public static <T> T Json2obj(String jsonData, Class<T> type) {
		Gson gson = new Gson();
		T result = gson.fromJson(jsonData, type);
		return result;
	}
	/**
	 * 日期格式转换错误时可以用这个
	 *用法： Gson gson =new GsonBuilder()  
					  .setDateFormat("yyyy-MM-dd HH:mm:ss") 
					  .create();  
			BOrderVo bOrderVo=GsonUtil.Json2Gson((String) request.getAttribute("params"), BOrderVo.class,gson);
	 * @param jsonData
	 * @param type
	 * @param gson
	 * @return
	 */
	public static <T> T Json2obj(String jsonData, Class<T> type,Gson gson) {
		T result = gson.fromJson(jsonData, type);
		return result;
	}
	public static <T> T Json2obj(HttpServletRequest request, Class<T> type) {
		String json=SysCode.SYS_C_BLANK;
			json = getRequestJsonString(request);
		Gson gson = new Gson();
		T result = gson.fromJson(json, type);
		return result;
	}
	
	/***
	 * 获取 request 中 json 字符串的内容
	 * 
	 * @param request
	 * @return : <code>byte[]</code>
	 * @throws IOException
	 */
	public static String getRequestJsonString(HttpServletRequest request)  {
		try {
			String submitMehtod = request.getMethod();
			// GET
			if (submitMehtod.equals(SysCode.SYS_C_GET)) {
				return new String(request.getQueryString().getBytes(SysCode.SYS_C_8859), SysCode.SYS_C_UTF8).replaceAll("%22", "\"");
				// POST
			} else {
				return getRequestPostStr(request);
			}
		} catch (Exception e) {
			
		} 
		return "";
	}

	/**
	 * 描述:获取 post 请求的 byte[] 数组
	 * 
	 * <pre>
	 * 举例：
	 * </pre>
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
		int contentLength = request.getContentLength();
		if (contentLength < 0) {
			return null;
		}
		byte buffer[] = new byte[contentLength];
		for (int i = 0; i < contentLength;) {
			int readlen = request.getInputStream().read(buffer, i, contentLength - i);
			if (readlen == -1) {
				break;
			}
			i += readlen;
		}
		return buffer;
	}

	/**
	 * 描述:获取 post 请求内容
	 * 
	 * <pre>
	 * 举例：
	 * </pre>
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getRequestPostStr(HttpServletRequest request) throws IOException {
		byte buffer[] = getRequestPostBytes(request);
		String charEncoding = request.getCharacterEncoding();
		if (charEncoding == null) {
			charEncoding =SysCode.SYS_C_UTF8;
		}
		return new String(buffer, charEncoding);
	}
}
