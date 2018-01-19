package com.zouhx.crawler.test;

import redis.clients.jedis.Jedis;

import com.zouhx.crawler.main.MccCfg;
import com.zouhx.crawler.util.RedisUtil;

public class RedisTest {

	public RedisTest() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) throws Exception {
		MccCfg.InitApp();
		Jedis jedis = RedisUtil.getJedis();
		jedis.lpush("t1","1");
		String lpop = jedis.lpop("t1");
		System.out.println(lpop);
	}
}
