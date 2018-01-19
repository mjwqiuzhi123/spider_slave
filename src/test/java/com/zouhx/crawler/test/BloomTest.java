package com.zouhx.crawler.test;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.Jedis;

import com.zouhx.crawler.main.MccCfg;
import com.zouhx.crawler.util.RedisUtil;

public class BloomTest {
	/**
	 * ͨ���鿴task�б���֤boolean�Ƿ����ظ����ݣ��Ӷ���֤bloomfilter�Ƿ���Ч
	 */
	public BloomTest() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) throws Exception {
		MccCfg.InitApp();
		Jedis jedis = RedisUtil.getJedis();
		Long llen = jedis.llen(MccCfg.REDIS_TASK_NAME);
		System.out.println("begin size:"+llen);
		Set<String> set = new HashSet<String>();
		for(int i=0;i<llen;i++){
			String lo = jedis.lpop(MccCfg.REDIS_TASK_NAME);
			System.out.println(lo);
			set.add(lo);
		}
		System.out.println("end size:"+set.size());
	}
}
