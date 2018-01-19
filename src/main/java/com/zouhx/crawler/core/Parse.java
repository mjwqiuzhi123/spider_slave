package com.zouhx.crawler.core;

public interface Parse {
	/**
	 * 解析入口
	 */
	public void parse(Page page);
	/**
	 * 启动
	 * @param rootUrl
	 */
	public void start(String rootUrl);
	/**
	 * 加入待爬取URL
	 * @param url
	 */
	public void addNext(String url);
	
	public void addNext(String... url);
	/**
	 * 关闭资源
	 */
	public void close();
	
}
