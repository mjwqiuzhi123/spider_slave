package com.zouhx.crawler.core;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SpiderManager {
	//�̳߳�
	public static ZSpiderThreadPoolExecutor pool = null;
	
	public static final int QUEUE_SIZE = 1000;
	//�������ܽ�������
	public static AtomicInteger total = new AtomicInteger(0);
	//���������������
	public static AtomicInteger valid = new AtomicInteger(0);
	
	//�������ܴ�����
	public static AtomicInteger taskNum = new AtomicInteger(0);
	
	
	public static LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(QUEUE_SIZE);
	
	public SpiderManager() {
		// TODO Auto-generated constructor stub
	}
	public static void init(){
		
		pool = new ZSpiderThreadPoolExecutor(2,2, 3, TimeUnit.SECONDS, workQueue);
	}
	
}
