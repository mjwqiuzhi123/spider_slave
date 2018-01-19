package com.zouhx.crawler.bean;

import java.io.Serializable;

public class IpAgentModel implements Serializable{

	private static final long serialVersionUID = 1L;
	private String ip;
	private int port;
	private boolean available;
	public IpAgentModel() {
		// TODO Auto-generated constructor stub
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}

	

}
