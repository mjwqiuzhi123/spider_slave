package com.zouhx.crawler.core;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;

import com.zouhx.crawler.main.MccCfg;
import com.zouhx.crawler.util.RedisUtil;

public class ZSpiderThreadPoolExecutor extends ThreadPoolExecutor {
	public static final String ER1 = "NO_TASK";
	public static LinkedBlockingQueue<Runnable> queue = null;
	
	public ZSpiderThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, LinkedBlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		queue = workQueue;
	}

	public ZSpiderThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit,
			LinkedBlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				threadFactory);
		queue = workQueue;
	}

	public ZSpiderThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit,
			LinkedBlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				handler);
		queue = workQueue;
	}

	public ZSpiderThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit,
			LinkedBlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
			RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				threadFactory, handler);
		queue = workQueue;
	}
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		
		if(r instanceof SpiderWorkerThread){
			SpiderWorkerThread worker = (SpiderWorkerThread)r;
			
			if(worker.getUrl()!=null)
				return;
			Jedis jedis = null;
			//do something to get url
			try{
				jedis = RedisUtil.getJedis();
				Boolean exists = jedis.exists(MccCfg.REDIS_TASK_NAME);
				if(exists){
					String url = jedis.lpop(MccCfg.REDIS_TASK_NAME);
					if("".equals(url)){
						worker.setUrl(ER1);
					}else{
						worker.setUrl(url);
					}
				}else{
					System.out.println("redis_url_task not exists!");
					worker.setUrl(ER1);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(jedis!=null){
					RedisUtil.returnResource(jedis);
				}
			}
		}
		
	}
	
	/**
	 * parse需要释放资源
	 * 避免连接占用过高
	 */
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		if(r instanceof SpiderWorkerThread){
			SpiderWorkerThread workerThread = (SpiderWorkerThread)r;
			AbstractParse parse = workerThread.getParse();
			parse.close();
		}
	}
	
	
	
	
	
	
}
