package com.zouhx.crawler.util;

import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import com.zouhx.crawler.main.MccCfg;

public class ReportUtil {

	public ReportUtil() {
		// TODO Auto-generated constructor stub
	}
	
	
	@SuppressWarnings("deprecation")
	public static boolean sendMessage(String str,String type ,String path){
		if(MccCfg.MASTER_IP.equals("")||MccCfg.MASTER_PORT==0){
			MccCfg.log.oWarning("send report fiald ,Master ip or Masert prot null", 1);
			return false;
		}
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = null;
		try{
		
		// 设置超时时间
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);
        
        post = new HttpPost("http://"+MccCfg.MASTER_IP+":"+MccCfg.MASTER_PORT+path);
        
        post.setHeader("type", type);
        
        StringEntity entity = new StringEntity(str, Charset.defaultCharset());
        entity.setContentType("application/json");
        
        post.setEntity(entity);
        
        HttpResponse response = httpClient.execute(post);
        
        int statusCode = response.getStatusLine().getStatusCode();
        
        if(statusCode!=200){
        	MccCfg.log.oWarning("send report fiald ,statusCode="+statusCode, 1);
        	return false;
        }
        return false;
        
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			if(post != null){
	            post.releaseConnection();
	        }
		}
	}
}
