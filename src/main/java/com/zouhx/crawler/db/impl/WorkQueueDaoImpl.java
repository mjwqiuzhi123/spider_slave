package com.zouhx.crawler.db.impl;

import java.sql.Timestamp;
import java.util.Date;

import com.zouhx.crawler.db.WorkQueueDao;

public class WorkQueueDaoImpl extends BaseDaoImpl implements WorkQueueDao {

	public WorkQueueDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void add(String threadNum, String queueNum) {
		// TODO Auto-generated method stub
		try{

			conn =getConnection("webServer");
			String sql = "";
			StringBuffer bf = new StringBuffer("insert into worker_log (scantime,threadnum,queuenum) values(?,?,?)");
			sql = bf.toString();
			
			if(conn!=null){
				prepStmt = conn.prepareStatement(sql);
				prepStmt.setTimestamp(1,new Timestamp(new Date().getTime()));
				prepStmt.setInt(2, Integer.valueOf(threadNum));
				prepStmt.setInt(3, Integer.valueOf(queueNum));
				prepStmt.executeUpdate();
			}
			
			
		}catch(Exception e){
			//e.printStackTrace();
		}finally{
			closeAll(rs, prepStmt, conn);
		}
	}

}
