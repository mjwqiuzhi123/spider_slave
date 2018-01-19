package com.zouhx.crawler.main;

import redis.clients.jedis.Jedis;

import com.zouhx.crawler.core.DaZhongDianPingSpider;
import com.zouhx.crawler.core.LianjiaSpider;
import com.zouhx.crawler.core.SpiderManager;
import com.zouhx.crawler.task.RemoveAgentTask;
import com.zouhx.crawler.task.ReportTask;
import com.zouhx.crawler.task.SimpleHeartbeatTask;
import com.zouhx.crawler.util.RedisUtil;
/**
 * 
 * @author zouhx
 *
 */
public class Application {

	public Application() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) throws Exception {
		/*MccCfg.InitApp();
		SpiderManager.init();
		LianjiaSpider spider = new LianjiaSpider();
		
		spider.start("https://bj.lianjia.com/zufang/");
		*/
		MccCfg.InitApp();
		SpiderManager.init();
		//ReportTask.start();
		//SimpleHeartbeatTask.start();
		RemoveAgentTask.start();
		DaZhongDianPingSpider spider = new DaZhongDianPingSpider();
		spider.start("http://www.dianping.com/beijing/food");
		
		
	}
}
