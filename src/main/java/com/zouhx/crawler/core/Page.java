package com.zouhx.crawler.core;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import redis.clients.jedis.Jedis;

import com.zouhx.crawler.main.MccCfg;
import com.zouhx.crawler.task.RemoveAgentTask;
import com.zouhx.crawler.util.RedisUtil;



public class Page {
	
	private Connection connection;
	
	private Map<String,String> headers = new HashMap<String,String>();

	private Map<String,String> cookies = new HashMap<String,String>();
	
	private String url;
	
	private Parse parse;
	
	public Page(Parse parse) {
		// TODO Auto-generated constructor stub
		this.parse = parse;
	}
	
	public void close(){
		this.parse.close();
	}
	
	public void addNext(String url){
		this.parse.addNext(url);
	}
	
	public void addNext(String... urls){
		this.parse.addNext(urls);
	}
	
	public String nextProxy(){
		Jedis jedis = RedisUtil.getJedis();
		Boolean exists = jedis.exists(MccCfg.REDIS_AGENT_NAME);
		if(!exists)
			return null;
		String agent = jedis.srandmember(MccCfg.REDIS_AGENT_NAME);
		
		String[] split = agent.split(",");
		if(split.length!=2){
			System.out.println("agent error ");
			jedis.srem(MccCfg.REDIS_AGENT_NAME, agent);
			RedisUtil.returnResource(jedis);
			return nextProxy();
		}else{
			String time =split[1];
			if(new Date().getTime()>new Long(time)){
				System.out.println(Thread.currentThread().getName()+" agent lose efficacy .");
				jedis.srem(MccCfg.REDIS_AGENT_NAME, agent);
				RedisUtil.returnResource(jedis);
				return nextProxy();
			}else{
				RedisUtil.returnResource(jedis);
				return split[0];
			}
		}

	}
	
	public Document getDocument(Integer num){
		
		String nextProxy = this.nextProxy();
		if(nextProxy==null)
			return null;
		String ip = nextProxy.split(":")[0];
		int port = Integer.valueOf(nextProxy.split(":")[1]);
		System.out.println(Thread.currentThread().getName()+"  agent,ip="+ip+",port="+port);
		try {
			
			connection =Jsoup.connect(this.url).proxy(ip, port);
			
			if(headers!=null&&headers.size()>0){
				connection.headers(headers);
			}
			if(cookies!=null&&cookies.size()>0){
				connection.cookies(cookies);
			}
			return connection.get();
		} catch (Exception e) {
			System.out.println("error times:"+num);
			RemoveAgentTask.put(ip+":"+port);
			if(++num>5){
				return null;
			}else{
				return getDocument(num);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Document getDocument() {
		return getDocument(0);
	}


	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	
	public void setHeaders(String key,String value) {
		this.headers.put(key, value);
	}
	
	public Map<String, String> getCookies() {
		return cookies;
	}

	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}
	
	public void setCookies(String key,String value) {
		this.cookies.put(key, value);
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Parse getParse() {
		return parse;
	}

	public void setParse(Parse parse) {
		this.parse = parse;
	}
	
	
	
}
