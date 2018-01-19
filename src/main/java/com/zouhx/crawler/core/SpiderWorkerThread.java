package com.zouhx.crawler.core;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import redis.clients.jedis.Jedis;

import com.zouhx.crawler.main.MccCfg;
import com.zouhx.crawler.util.RedisUtil;

/**
 * 数据爬虫爬虫工作线程
 * @author zouhx
 *
 */
public class SpiderWorkerThread implements Runnable{
	//待爬取URL
	private String url ;
	private boolean isRootPath ;
	private AbstractParse parse;
	public SpiderWorkerThread(String url) {
		this.url = url;
	}

	@Override
	public void run() {
		try{
			if(this.url==null||ZSpiderThreadPoolExecutor.ER1.equals(this.url)){
				System.out.println("no url");
				return;
			}
			System.out.println(Thread.currentThread().getName()+" url:"+url);
			Page page = this.parse.getPage();
			page.setUrl(url);
			parse.parse(page);
			SpiderManager.taskNum.getAndIncrement();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public boolean isRootPath() {
		return isRootPath;
	}

	public void setRootPath(boolean isRootPath) {
		this.isRootPath = isRootPath;
	}

	
	
	public AbstractParse getParse() {
		return parse;
	}

	public void setParse(AbstractParse parse) {
		this.parse = parse;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
