package com.zouhx.crawler.bean;

public class HouseModel {
	private int id;
	private String title;            //����
	private String housing;          //С��
	private String shape;            //����
	private String size;             //ƽ��
	private String orientation;      //����
	private String price;            //�۸�
	private String updateTime;       //����ʱ��
	private String seeNum;           //��������
	private String md5;
	public HouseModel() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHousing() {
		return housing;
	}
	public void setHousing(String housing) {
		this.housing = housing;
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getSeeNum() {
		return seeNum;
	}
	public void setSeeNum(String seeNum) {
		this.seeNum = seeNum;
	}
	
}
