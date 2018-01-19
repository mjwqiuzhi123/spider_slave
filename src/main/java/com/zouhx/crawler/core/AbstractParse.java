package com.zouhx.crawler.core;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import cn.edu.hfut.dmic.webcollector.util.MD5Utils;

import com.zouhx.crawler.bloomfilter.BloomFilter;
import com.zouhx.crawler.bloomfilter.InRedisBloomFilter;
import com.zouhx.crawler.main.MccCfg;
import com.zouhx.crawler.util.RedisUtil;

public abstract class AbstractParse implements Parse{
	
	private BloomFilter<String> filter = new InRedisBloomFilter<String>();
	
	private Page page = new Page(this);
	
	
	
	@Override
	public void addNext(String url){
		//判断bloomfilter
		boolean exists = filter.exists(url);
		
		if(exists)
			return;
		
		
		Jedis jedis = null;
		try{
			jedis = RedisUtil.getJedis();
			jedis.rpush(MccCfg.REDIS_TASK_NAME, url);
			filter.add(url);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null)
				RedisUtil.returnResource(jedis);
		}
	}
	// 批量 --防止redis连接过多
	@Override
	public void addNext(String... urls){
		Jedis jedis = null;
		
		try{
			List<String> unExists = new ArrayList<String>();
			for (String url : urls) {
				boolean exists = filter.exists(url);
				if(!exists)
					unExists.add(url);
			}
			if(unExists.size()==0)
				return;
			jedis = RedisUtil.getJedis();
			int size = unExists.size();
			String[] array = unExists.toArray(new String[size]);
			jedis.rpush(MccCfg.REDIS_TASK_NAME, array);
			filter.add(unExists);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null)
				RedisUtil.returnResource(jedis);
		}
	}
	
	
	@Override
	public void start(String rootUrl) {
		try{
			String md5 = MD5Utils.md5(rootUrl.getBytes());
			
			MccCfg.REDIS_TASK_NAME+=md5;
			System.out.println("task name="+MccCfg.REDIS_TASK_NAME);
			MccCfg.REDIS_BLOOMFILTER_NAME+=md5;
			System.out.println("bloomfilter name="+MccCfg.REDIS_BLOOMFILTER_NAME);
			
			SpiderWorkerThread thread = new SpiderWorkerThread(rootUrl);
			thread.setParse(this);
			SpiderManager.pool.execute(thread);
			Thread.sleep(20*1000);
			//启动添加线程
			Class<? extends AbstractParse> instanceClass = this.getClass();
			System.out.println("class name:"+instanceClass.getName());
			new Thread(new ThreadPush(instanceClass)).start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	@Override
	public void close(){
		this.filter.close();
		System.out.println("redis conection close");
	}
	

	


	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}





	//static class yu class qubie
	static class ThreadPush implements Runnable{
		private Class<? extends AbstractParse> instanceClass;
		
		public ThreadPush(Class<? extends AbstractParse> instanceClass) {
			super();
			this.instanceClass = instanceClass;
		}



		@Override
		public void run() {
			while(true){
				try{
					
					int queueSize = SpiderManager.workQueue.size();
					if(queueSize*3<SpiderManager.QUEUE_SIZE){
						//可用代理过少，表明代理都被屏蔽，且已经删除。暂停抓取
						Jedis jedis = RedisUtil.getJedis();
						
						Long scard = jedis.scard(MccCfg.REDIS_AGENT_NAME);
						
						RedisUtil.returnResource(jedis);
						
						if(scard<10){
							
							Thread.sleep(1000*60);
							
						}else{
							//if(queueSize<1){
							//System.out.println("queueSize="+queueSize);
							//Jedis jedis = RedisUtil.getJedis();
							//String lpop = jedis.lpop(MccCfg.REDIS_TASK_NAME);
							SpiderWorkerThread thread = new SpiderWorkerThread(null);
							AbstractParse parse = instanceClass.newInstance();
							thread.setParse(parse);
							SpiderManager.pool.execute(thread);
						}
						
						
						
					}else{
						Thread.sleep(1000*2);
					}
				
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		}
		
	}
	
}
