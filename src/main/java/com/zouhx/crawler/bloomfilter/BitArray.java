package com.zouhx.crawler.bloomfilter;

import java.io.Closeable;

public interface BitArray extends Closeable {
	
	/**
	 * ��ȡ����λ�õ�ֵ
	 * @param index
	 * @return
	 */
	public boolean getBit(int index);
	
	/**
	 * ����ָ������λ�õ�ֵ
	 * @param index
	 * @return
	 */
	public boolean setBit(int index);
	
	/**
	 * ȫ�����
	 */
	public void clear();
	
	/**
	 * ���ָ������λ�õ�����
	 * @param index
	 */
	public void clearByIndex(int index);
	
	
	
}
