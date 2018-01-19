package com.zouhx.crawler.db;

import java.util.List;

import com.zouhx.crawler.bean.HouseModel;

public interface HouseDao {
	public void add(HouseModel model);
	public List<HouseModel> getAll();
}
