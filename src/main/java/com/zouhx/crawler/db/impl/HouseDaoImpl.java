package com.zouhx.crawler.db.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.zouhx.crawler.bean.HouseModel;
import com.zouhx.crawler.core.SpiderManager;
import com.zouhx.crawler.db.HouseDao;
import com.zouhx.crawler.main.MccCfg;

public class HouseDaoImpl extends BaseDaoImpl implements HouseDao {

	public HouseDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void add(HouseModel model) {
		try{
			/**
			 *  private String title;            //标题
				private String housing;          //小区
				private String shape;            //房型
				private String size;             //平方
				private String orientation;      //朝向
				private String price;            //价格
				private String updateTime;       //更新时间
				private String seeNum;           //看房人数
			 */
			conn =getConnection("webServer");
			String sql = "";
			StringBuffer bf = new StringBuffer("insert into lianjia_house (title,housing,shape,size,orientation,price,updateTime,seeNum,md5) values(?,?,?,?,?,?,?,?,?)");
			sql = bf.toString();
			
			if(conn!=null){
				prepStmt = conn.prepareStatement(sql);
				prepStmt.setString(1, model.getTitle());
				prepStmt.setString(2, model.getHousing());
				prepStmt.setString(3, model.getShape());
				prepStmt.setString(4, model.getSize());
				prepStmt.setString(5, model.getOrientation());
				prepStmt.setString(6, model.getPrice());
				prepStmt.setString(7, model.getUpdateTime());
				prepStmt.setString(8, model.getSeeNum());
				prepStmt.setString(9, model.getMd5());
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

	@Override
	public List<HouseModel> getAll() {
		List<HouseModel> mds = new ArrayList<HouseModel>(); 
		try{
			conn =getConnection("webServer");
			String sql = "";
			StringBuffer bf = new StringBuffer("SELECT id,title,housing,shape,size,orientation,price,seeNum,md5 FROM  lianjia_house");
			sql = bf.toString();
			if(conn!=null){
				prepStmt = conn.prepareStatement(sql);
				rs = prepStmt.executeQuery();
				while(rs.next()){
					HouseModel house = new HouseModel();
					house.setId(rs.getInt(1));
					house.setTitle(rs.getString(2));
					house.setHousing(rs.getString(3));
					house.setShape(rs.getString(4));
					house.setSize(rs.getString(5));
					house.setOrientation(rs.getString(6));
					house.setPrice(rs.getString(7));
					house.setSeeNum(rs.getInt(8)+"");
					house.setMd5(rs.getString(9));
					mds.add(house);
				}
				
			}
			MccCfg.repetition_num.incrementAndGet();
		}catch(Exception e){
			e.printStackTrace(); 
		}finally{
			closeAll(rs, prepStmt, conn);
		}
		return mds;
	}

}
