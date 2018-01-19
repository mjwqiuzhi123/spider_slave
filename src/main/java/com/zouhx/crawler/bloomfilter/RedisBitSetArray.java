package com.zouhx.crawler.bloomfilter;

import java.io.IOException;

import com.zouhx.crawler.main.MccCfg;
import com.zouhx.crawler.util.RedisUtil;

import redis.clients.jedis.Jedis;

public class RedisBitSetArray implements BitArray {
	
	private  Jedis jedis = null;
	
	public RedisBitSetArray(Jedis jedis) {
		this.jedis = jedis;
	}

	@Override
	public void close() throws IOException {
		RedisUtil.returnResource(jedis);
	}

	@Override
	public boolean getBit(int index) {
		return jedis.getbit(MccCfg.REDIS_BLOOMFILTER_NAME, index);
	}

	@Override
	public boolean setBit(int index) {
		return jedis.setbit(MccCfg.REDIS_BLOOMFILTER_NAME, index, true);
	}

	@Override
	public void clear() {
		jedis.del(MccCfg.REDIS_BLOOMFILTER_NAME);
	}

	@Override
	public void clearByIndex(int index) {
		jedis.setbit(MccCfg.REDIS_BLOOMFILTER_NAME, index, false);

	}
	
}
