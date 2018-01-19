package com.zouhx.crawler.bloomfilter;

import java.io.IOException;
import java.io.Serializable;
import java.util.BitSet;

public class JBitSetArray implements BitArray,Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private BitSet bitSet = null;
	
	
	public JBitSetArray(int maxSize) {
		bitSet= new BitSet(maxSize);
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getBit(int index) {
		return bitSet.get(index);
	}

	@Override
	public boolean setBit(int index) {
		this.bitSet.set(index);
		return true;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		this.bitSet.clear();
	}

	@Override
	public void clearByIndex(int index) {
		// TODO Auto-generated method stub
		this.bitSet.clear(index);
	}

}
