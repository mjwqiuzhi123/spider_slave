package com.zouhx.crawler.task;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import com.zouhx.crawler.bean.ReportModel;
import com.zouhx.crawler.core.SpiderManager;
import com.zouhx.crawler.main.MccCfg;
import com.zouhx.crawler.util.ReportUtil;

public class ReportTask extends TimerTask{
	public static final String REPORT_URI = "/report";
	public ReportTask() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static void start(){
		ReportTask task = new ReportTask();
		Timer timer = new Timer();  
		long intevalPeriod = 1000*60*15; 
        timer.schedule(task, new Date(), intevalPeriod);
	}
	
	
	@Override
	public void run() {
		try{
			ReportModel model = new ReportModel();
			model.setNodeName(MccCfg.NODE_NAME);
			model.setTotal(SpiderManager.total.get());
			SpiderManager.total.set(0);
			model.setValid(SpiderManager.valid.get());
			SpiderManager.valid.set(0);
			model.setTaskNum(SpiderManager.taskNum.get());
			SpiderManager.taskNum.set(0);
			JSONObject fromObject = JSONObject.fromObject(model);
			String jsonStr = fromObject.toString();
			ReportUtil.sendMessage(jsonStr, "report",REPORT_URI);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		MccCfg.InitApp();
		ReportTask task = new ReportTask();
		ReportUtil.sendMessage("111", "report",REPORT_URI);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
