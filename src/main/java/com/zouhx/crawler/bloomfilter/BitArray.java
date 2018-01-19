package com.zouhx.crawler.bloomfilter;

import java.io.Closeable;

public interface BitArray extends Closeable {
	
	/**
	 * 获取给定位置的值
	 * @param index
	 * @return
	 */
	public boolean getBit(int index);
	
	/**
	 * 设置指定索引位置的值
	 * @param index
	 * @return
	 */
	public boolean setBit(int index);
	
	/**
	 * 全部清空
	 */
	public void clear();
	
	/**
	 * 清除指定索引位置的数据
	 * @param index
	 */
	public void clearByIndex(int index);
	
	
	
}
