package com.zouhx.crawler.db.impl;

import java.sql.Timestamp;
import java.util.Date;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.zouhx.crawler.bean.DaZhongModel;
import com.zouhx.crawler.core.SpiderManager;
import com.zouhx.crawler.db.DaZhongDao;

public class DaZhongDaoImpl extends BaseDaoImpl implements DaZhongDao {

	public DaZhongDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void add(DaZhongModel model) {
		try{
			/**
			 *  private String tit;              //餐厅名称
				private String starRank;         //星级
				private String commentNum;       //点评条数
				private String cost;             //人均消费
				private String type;             //种类：西餐，中餐。。。
				private String region;           //区域
				private String site;             //地址
				private String grade;            //评分
				private String recommend;        //推荐
				private String discounts;        //团购
			 */
			conn =getConnection("webServer");
			String sql = "";
			StringBuffer bf = new StringBuffer("insert into dazhong_food (title,starRank,commentNum,cost,type,region,site,grade,recommend,discounts,md5,scantime) values(?,?,?,?,?,?,?,?,?,?,?,?)");
			sql = bf.toString();
			
			if(conn!=null){
				prepStmt = conn.prepareStatement(sql);
				prepStmt.setString(1, model.getTit());
				prepStmt.setString(2, model.getStarRank());
				prepStmt.setString(3, model.getCommentNum());
				prepStmt.setString(4, model.getCost());
				prepStmt.setString(5, model.getType());
				prepStmt.setString(6, model.getRegion());
				prepStmt.setString(7, model.getSite());
				prepStmt.setString(8, model.getGrade());
				prepStmt.setString(9, model.getRecommend());
				prepStmt.setString(10, model.getDiscounts());
				prepStmt.setString(11, model.getMd5());
				prepStmt.setTimestamp(12, new Timestamp(new Date().getTime()));
				prepStmt.executeUpdate();
			}
			SpiderManager.valid.incrementAndGet();
		}catch(MySQLIntegrityConstraintViolationException e){
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(rs, prepStmt, conn);
		}
	}

}
