package com.zouhx.crawler.bloomfilter;

import java.io.Serializable;
import java.nio.charset.Charset;

public class DefaultDecomposer<T> implements Decomposer<T>,Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();
	
	public DefaultDecomposer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void decompose(T obj, ByteSink sink) {
		// TODO Auto-generated method stub
		if(obj == null) {
			return;
		}
		
		if(obj instanceof String) {
			sink.putBytes(((String) obj).getBytes(DEFAULT_CHARSET));
			return;
		}
		
		sink.putBytes(obj.toString().getBytes(DEFAULT_CHARSET));
	}

}
