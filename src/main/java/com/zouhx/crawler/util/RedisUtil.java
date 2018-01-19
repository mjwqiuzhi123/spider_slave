package com.zouhx.crawler.util;

import java.util.concurrent.atomic.AtomicInteger;

import com.zouhx.crawler.main.MccCfg;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * update jedispool ͬ����ȥ��
 * @author zouhaoxuan
 *
 */
public final class RedisUtil {
    
	public static AtomicInteger  count = new AtomicInteger(0);
    
    private static JedisPool jedisPool = null;
    
    /**
     * ��ʼ��Redis���ӳ�
     */
    static {
        
    }
    
    
    
    
    
    /**
     * ��ȡJedisʵ��
     * @return
     */
    public static Jedis getJedis() {
    	count.incrementAndGet();
    	if(jedisPool==null){
    		synchronized(RedisUtil.class){
	    		try {
	                JedisPoolConfig config = new JedisPoolConfig();
	                config.setMaxIdle(MccCfg.MAX_IDLE);
	                config.setMaxTotal(10000);
	                config.setMaxWaitMillis(MccCfg.MAX_WAIT);
	                config.setTestOnBorrow(MccCfg.TEST_ON_BORROW);
	                jedisPool = new JedisPool(config, MccCfg.ADDR, MccCfg.PORT, MccCfg.TIMEOUT,MccCfg.AUTH);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
    		}
    	}
    	try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * �ͷ�jedis��Դ
     * @param jedis
     */
    @SuppressWarnings("deprecation")
	public synchronized static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
            count.addAndGet(-1);
        }
    }
    
    public static void main(String[] args) throws Exception {
    	MccCfg.InitApp();
		Jedis jedis = RedisUtil.getJedis();
		jedis.append("key1", "1");
		returnResource(jedis);
		
	}
    
    
}
