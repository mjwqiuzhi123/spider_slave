package com.zouhx.crawler.task;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import com.zouhx.crawler.main.MccCfg;
import com.zouhx.crawler.util.ReportUtil;

public class SimpleHeartbeatTask extends TimerTask{
	public static final String REPORT_URI = "/report";
	public SimpleHeartbeatTask() {
		// TODO Auto-generated constructor stub
	}
	
	public static void start(){
		SimpleHeartbeatTask task = new SimpleHeartbeatTask();
		Timer timer = new Timer();  
		long intevalPeriod = 1000*60*3; 
        timer.schedule(task, new Date(), intevalPeriod);
	}
	
	@Override
	public void run() {
		ReportUtil.sendMessage(MccCfg.NODE_NAME, "heartbeat", REPORT_URI);
	}
	
	
	
	public static void main(String[] args) throws Exception {
		MccCfg.InitApp();
		SimpleHeartbeatTask task = new SimpleHeartbeatTask();
		task.run();
	}
	
	
}
