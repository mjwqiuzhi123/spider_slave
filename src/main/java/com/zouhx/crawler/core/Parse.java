package com.zouhx.crawler.core;

public interface Parse {
	/**
	 * �������
	 */
	public void parse(Page page);
	/**
	 * ����
	 * @param rootUrl
	 */
	public void start(String rootUrl);
	/**
	 * �������ȡURL
	 * @param url
	 */
	public void addNext(String url);
	
	public void addNext(String... url);
	/**
	 * �ر���Դ
	 */
	public void close();
	
}
