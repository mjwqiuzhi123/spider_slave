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
	 * ��¡���������Ԫ��
	 * @param bytes
	 * @return
	 */
	public boolean add(byte[] bytes);
	
	/**
	 * ��ӣ�������Զ���Ľ�����
	 * @param value
	 * @return
	 */
	public boolean add(T value);
	
	/**
	 * ������Ԫ��ȫ����ӵ���¡������
	 * @param cns
	 * @return
	 */
	public boolean add(Collection<T> cns);
	
	/**
	 * �Ƿ����
	 * @param bytes
	 * @return
	 */
	public boolean exists(byte[] bytes);
	
	/**
	 * �Ƿ���ڣ�������Զ��������
	 * @param value
	 * @return
	 */
	public boolean exists(T value);
	
	
	public void close();
	
}
