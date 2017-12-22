package com.as.biz;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.as.biz.MyHttpClient;

public class MyHttpClientTest {
	
	@Test
	public void testDoPost() {
		MyHttpClient myHttpClient = new MyHttpClient();
		String path="http://113.57.201.34:9130/WebContent/main/jceqpmgr/qreq.jsp?t=jcmgr_list";
		int pageIndex=0;
		int pageSize=100;
		for(int i=0;i<(3099/pageSize+1);i++){
			String param="_QUERY_TYPE=1&SMART_KEY_OIDNO=&SMART_KEY_UNT_NAME=&IF_CONFIRM_q=&IS_LICENSE_q=&EQP_TYPE_q=&EQP_SORT_q=&EQP_VART_q=&CERT_UNT_NAME=&RATED_CAPACITY_FROM=&RATED_CAPACITY_TO=&BURNINGTYPE=&EQP_NAME_q=&EQP_MOD_q=&FACTORY_COD_q=&EQP_USE_STA_q=2%2C3&BUILD_NAME=&EQP_USECERT_COD_q=&EQP_REG_COD_q=&EQP_AREA_COD_q=&UNT_AREA_NAME_q=&REG_DATE_BEG=&REG_DATE_END=&EQP_REG_STA_q=&EQP_LEVEL_q=&NEXT_ISP_DATE_FROM=&NEXT_ISP_DATE_TO=&EQP_USE_ADDR_q=&MAKE_UNT_NAME_q=&USESTA_DATE_BEG=&USESTA_DATE_END=&USE_UNT_NAME_q=&MANT_UNT_NAME_q=&SECUDEPT_NAME_q=&INSP_UNT_ID_q=&IF_OTHERISP_q=0&IF_CQWZG=0&_COL_HEARD_DATA=&_CLASS_NAME=b3JnLmZqc2VpLm1nci5lcXBtZ3IuRXFwTWdyX3F1ZXJ5&_METHOD_NAME=cXVlcnlCeVJlcXVlc3Q%3D"
					+ "&pageIndex="+pageIndex+"&pageSize="+pageSize+"&sortField=&sortOrder=";
			
//			Map<String, String> cookie=new LinkedHashMap<>();
//			cookie.put("JSESSIONID", "xbElo6JWSCcnJj7omYcPkPzLcn90k9XTkjZdH6jNkIFigR577n_6!-24914213!-1491452852");
			Map<String, String> hader =new LinkedHashMap<>();
			hader.put("Cookie", "JSESSIONID=xbElo6JWSCcnJj7omYcPkPzLcn90k9XTkjZdH6jNkIFigR577n_6!-24914213!-1491452852");
			String re = myHttpClient.doPost(path, null , param);
			System.out.println(re);
			pageIndex=i;
		}
	}

}
