package com.zouhx.crawler.bloomfilter;


/**
 * 
 * @author zouhx
 * time 2017.12.28
 * @param <T>
 */
public interface Decomposer<T> {
	
	public void decompose(T obj, ByteSink sink);

}
