package com.zouhx.crawler.db.impl;

import java.sql.Timestamp;
import java.util.Date;

import com.zouhx.crawler.db.DownloadNumDao;

public class DownloadNumDaoImpl extends BaseDaoImpl implements DownloadNumDao {

	public DownloadNumDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void add(int num, int use) {
		// TODO Auto-generated method stub
		try{

			conn =getConnection("webServer");
			String sql = "";
			StringBuffer bf = new StringBuffer("insert into capturerecorde (scantime,total,usenum) values(?,?,?)");
			sql = bf.toString();
			
			if(conn!=null){
				prepStmt = conn.prepareStatement(sql);
				prepStmt.setTimestamp(1,new Timestamp(new Date().getTime()));
				prepStmt.setInt(2, num);
				prepStmt.setInt(3, use);
				prepStmt.executeUpdate();
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(rs, prepStmt, conn);
		}
	}

}
