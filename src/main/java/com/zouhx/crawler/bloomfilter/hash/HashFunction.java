package com.zouhx.crawler.bloomfilter.hash;

import java.io.Serializable;

import com.sangupta.murmur.Murmur3;
/**
 * 
 * @author zouhx
 *
 */
public class HashFunction implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final long SEED = 0x7f3a21eal;
	
	public HashFunction(){
		
	}
	public boolean isSingleValued() {
		return false;
	}

	public long hash(byte[] bytes) {
		return Murmur3.hash_x86_32(bytes, 0, SEED);
	}

	public long[] hashMultiple(byte[] bytes) {
		return Murmur3.hash_x64_128(bytes, 0, SEED);
	}
}
