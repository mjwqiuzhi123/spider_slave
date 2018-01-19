package com.zouhx.crawler.bloomfilter;

import java.util.Collection;

/**
 * 
 * @author zouhx
 * @time 2017.12.28
 * @param <T>
 */
public interface BloomFilter<T> {
	/**
	 * 向布隆过滤器添加元素
	 * @param bytes
	 * @return
	 */
	public boolean add(byte[] bytes);
	
	/**
	 * 添加，需配合自定义的解析器
	 * @param value
	 * @return
	 */
	public boolean add(T value);
	
	/**
	 * 集合中元素全部添加到布隆过滤器
	 * @param cns
	 * @return
	 */
	public boolean add(Collection<T> cns);
	
	/**
	 * 是否存在
	 * @param bytes
	 * @return
	 */
	public boolean exists(byte[] bytes);
	
	/**
	 * 是否存在，需配合自定义解析器
	 * @param value
	 * @return
	 */
	public boolean exists(T value);
	
	
	public void close();
	
}
