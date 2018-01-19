package com.zouhx.crawler.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import redis.clients.jedis.Jedis;

import com.zouhx.crawler.main.MccCfg;
import com.zouhx.crawler.util.RedisUtil;
import com.zouhx.crawler.util.ReportUtil;

public class RemoveAgentTask extends TimerTask {
	
	private static Map<String,Integer> agents = new ConcurrentHashMap<String,Integer>();
	public  static final String REPORT_URI = "/report";
	public RemoveAgentTask() {
	}
	
	public static void start(){
		RemoveAgentTask task = new RemoveAgentTask();
		Timer timer = new Timer();  
		long intevalPeriod = 1000*30; 
        timer.schedule(task, new Date(), intevalPeriod);
	}
	
	@Override
	public void run() {
		System.out.println("rem");
		Jedis jedis = RedisUtil.getJedis();
		try{
			Set<Entry<String, Integer>> entrySet = agents.entrySet();
			for (Entry<String, Integer> entry : entrySet) {
				if(entry.getValue()>1){
					String agent = entry.getKey();
					
					if(jedis.sismember(MccCfg.REDIS_AGENT_NAME, agent)){
						jedis.srem(MccCfg.REDIS_AGENT_NAME, agent);
						System.out.println("remove agent : "+agent);
					}
					
					agents.remove(entry);
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null)
				RedisUtil.returnResource(jedis);
		}
		
	}
	
	public static void put(String agent){
		Integer num = agents.get(agent);
		if(num==null){
			agents.put(agent, 1);
			System.out.println("put");
		}else{
			agents.put(agent, ++num);
			System.out.println("add");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
