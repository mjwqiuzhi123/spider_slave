package com.zouhx.crawler.bloomfilter;



import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;


public class ByteSink implements Serializable {
	
	private static final long serialVersionUID = 1L;


	protected ByteArrayOutputStream stream = new ByteArrayOutputStream();
	

	protected DataOutputStream dataStream = new DataOutputStream(stream);
	

	public byte[] getByteArray() {
		return stream.toByteArray();
	}
	

	public ByteSink putByte(byte b) {
		this.stream.write(b);
		return this;
	}
	

	public ByteSink putBytes(byte[] bytes) {
		try {
			stream.write(bytes);
		} catch (IOException e) {
			throw new RuntimeException("Unable to store bytes inside the sink", e);
		}
		
		return this;
	}
	

	public ByteSink putBytes(byte[] bytes, int offset, int length) {
		this.stream.write(bytes, offset, length);
		return this;
	}
	
	public ByteSink putChar(char c) {
		try {
			this.dataStream.writeChar(c);
		} catch (IOException e) {
			throw new RuntimeException("Unable to store char inside the sink", e);
		}
		
		return this;
	}
	
	public ByteSink putShort(short s) {
		try {
			this.dataStream.writeShort(s);
		} catch (IOException e) {
			throw new RuntimeException("Unable to store short inside the sink", e);
		}
		
		return this;
	}
	
	public ByteSink putInt(int i) {
		try {
			this.dataStream.writeInt(i);
		} catch (IOException e) {
			throw new RuntimeException("Unable to store int inside the sink", e);
		}
		
		return this;
	}
	
	public ByteSink putLong(long l) {
		try {
			this.dataStream.writeLong(l);
		} catch (IOException e) {
			throw new RuntimeException("Unable to store long inside the sink", e);
		}
		
		return this;
	}
	
	public ByteSink putFloat(float f) {
		try {
			this.dataStream.writeFloat(f);
		} catch (IOException e) {
			throw new RuntimeException("Unable to store float  inside the sink", e);
		}
		
		return this;
	}
	
	public ByteSink putDouble(double d) {
		try {
			this.dataStream.writeDouble(d);
		} catch (IOException e) {
			throw new RuntimeException("Unable to store double inside the sink", e);
		}
		
		return this;
	}

	public ByteSink putBoolean(boolean b) {
		try {
			this.dataStream.writeBoolean(b);
		} catch (IOException e) {
			throw new RuntimeException("Unable to store boolean inside the sink", e);
		}
		
		return this;
	}
	
	public ByteSink putChars(CharSequence charSequence) {
		try {
			this.dataStream.writeBytes(charSequence.toString());
		} catch (IOException e) {
			throw new RuntimeException("Unable to store charSequence inside the sink", e);
		}
		
		return this;
	}
	
}