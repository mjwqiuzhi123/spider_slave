package com.zouhx.crawler.bean;

public class DaZhongModel {
	
	private String tit;              //餐厅名称
	private String starRank;         //星级
	private String commentNum;       //点评条数
	private String cost;             //人均消费
	private String type;             //种类：西餐，中餐。。。
	private String region;           //区域
	private String site;             //地址
	private String grade;            //评分
	private String recommend;        //推荐
	private String discounts;        //团购
	private String md5;
	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getTit() {
		return tit;
	}

	public void setTit(String tit) {
		this.tit = tit;
	}

	public String getStarRank() {
		return starRank;
	}

	public void setStarRank(String starRank) {
		this.starRank = starRank;
	}

	public String getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public String getDiscounts() {
		return discounts;
	}

	public void setDiscounts(String discounts) {
		this.discounts = discounts;
	}

	public DaZhongModel() {
		// TODO Auto-generated constructor stub
	}
	
}
