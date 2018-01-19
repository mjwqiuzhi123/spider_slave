package com.zouhx.crawler.bloomfilter;

import java.io.Serializable;
import java.util.Collection;

import com.zouhx.crawler.bloomfilter.hash.HashFunction;

public class InMemoryBloomFilter<T> implements BloomFilter<T>,Serializable {
	
	private static final long serialVersionUID = 1L;

	protected HashFunction hash = new HashFunction();
	
	protected Decomposer<T> decomposer  = new DefaultDecomposer<T>();
	
	protected final int HASH_SIZE = 8;
	
	private final int ARRAY_SIZE = 1000*1000*100;
	
	protected BitArray bitArray = new JBitSetArray(ARRAY_SIZE);
	
	public InMemoryBloomFilter() {
		// TODO Auto-generated constructor stub
		this(new HashFunction(),new DefaultDecomposer<T>());
	}
	
	public InMemoryBloomFilter(HashFunction hash,Decomposer<T> decomposer) {
		// TODO Auto-generated constructor stub
		this.hash = hash;
		this.decomposer = decomposer;
	}

	@Override
	public boolean add(byte[] bytes) {
		long hash64 = getLongHash64(bytes);
		
		// apply the less hashing technique
		int hash1 = (int) hash64;
		int hash2 = (int) (hash64 >>> 32);
		
		boolean bitsChanged = false;
		for (int i = 1; i <= this.HASH_SIZE; i++) {
			int nextHash = hash1 + i * hash2;
			if (nextHash < 0) {
				nextHash = ~nextHash;
			}
			bitsChanged |= this.bitArray.setBit(nextHash % ARRAY_SIZE);
		}
		
		return bitsChanged;
	}
	protected long getLongHash64(byte[] bytes) {
		if(bytes == null) {
			throw new IllegalArgumentException("Bytes to add to bloom filter cannot be null");
		}
		
		if(this.hash.isSingleValued()) {
			return this.hash.hash(bytes);
		}
		
		return this.hash.hashMultiple(bytes)[0];
	}
	@Override
	public boolean add(T value) {
		// TODO Auto-generated method stub
		
		ByteSink sink = new ByteSink();
		
		this.decomposer.decompose(value, sink);
		
		byte[] bytes = sink.getByteArray();
		
		return add(bytes);
	}

	@Override
	public boolean add(Collection<T> cns) {
		// TODO Auto-generated method stub
		boolean flg  = true;
		for (T t : cns) {
			flg &=add(t);
		}
		return flg;
	}

	@Override
	public boolean exists(byte[] bytes) {
		// TODO Auto-generated method stub
		long hash64 = getLongHash64(bytes);
		
		int hash1 = (int) hash64;
		int hash2 = (int) (hash64 >>> 32);
		for (int i = 1; i <= this.HASH_SIZE; i++) {
			int nextHash = hash1 + i * hash2;
			if (nextHash < 0) {
				nextHash = ~nextHash;
			}
			if (!this.bitArray.getBit(nextHash % ARRAY_SIZE)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean exists(T value) {
		// TODO Auto-generated method stub
		
		ByteSink sink = new ByteSink();
		
		this.decomposer.decompose(value, sink);
		
		byte[] bytes = sink.getByteArray();
		
		return exists(bytes);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	

}
