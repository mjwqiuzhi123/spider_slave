package com.zouhx.crawler.core;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zouhx.crawler.bean.HouseModel;
import com.zouhx.crawler.db.HouseDao;
import com.zouhx.crawler.db.impl.HouseDaoImpl;
import com.zouhx.crawler.main.MccCfg;
import com.zouhx.crawler.util.Utils;

public class LianjiaSpider extends AbstractParse{
	public static final String targetUrl ="https://bj.lianjia.com/zufang/";
	@Override
	public void parse(Page page) {
		// TODO Auto-generated method stub
		try {
			//System.out.println("in");
			
			
			Document document = page.getDocument();
			
			if(document==null){
				page.addNext(page.getUrl());
				return;
			}
			
			
			//System.out.println("document be all set!");
			
			//Document document = Jsoup.connect(targetUrl).get();
			
			this.add(document);
			
			//获取所有 a标签
			Elements elementsFora = document.getElementsByTag("a");
			//System.out.println("Tag <a> size :"+elementsFora.size());
			
			for (Element element : elementsFora) {
				String attr = element.attr("href");
				if(attr!=null){
					boolean matches = attr.matches("((?!html).)*");
					
					boolean matches2 = attr.matches("((?!http).)*");
					
					
					
					
					if(matches&&matches2){
						String url = targetUrl.substring(0,targetUrl.indexOf("/", 8))+attr;

						
						if(url.equals("https://bj.lianjia.com")){
							//System.out.println("Eror : https://bj.lianjia.com  false!!!!!!!!!!!!!!!!!!!!!!!!!");
							continue;
						}
						page.addNext(url);
						
					}
					
				}
			}
			
		}catch(IOException e){ 
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void add(Document document) throws Exception{
		String title = "";            
		String housing = "";          
		String shape = "";            
		String size = "";            
		String orientation = "";      
		String price = "";            
		String updateTime = "";       
		String seeNum = "";   
		Elements house_lst_class = document.getElementsByClass("house-lst");
		if(house_lst_class!=null&&house_lst_class.size()>0){
			Element houseList = house_lst_class.get(0);
			Elements elementsByTag = houseList.getElementsByTag("li");
			HouseDao dao = new HouseDaoImpl();
			HouseModel model = new HouseModel();
			for(Element ele : elementsByTag){
				String attr = ele.attr("data-el");
				if(attr!=null){
					Elements div1 = ele.getElementsByClass("info-panel");
					if(div1!=null&&div1.size()!=0){
						Element div = div1.get(0);
						Elements as = div.getElementsByTag("a");
						Element a = as.get(0);
						title = a.attr("title");
						//System.out.println("title="+title);
						Element col_1 = div.getElementsByClass("col-1").get(0);
						Element where_class = col_1.getElementsByClass("where").get(0);
						housing = where_class.getElementsByTag("a").get(0).getElementsByTag("span").html().replaceAll("&nbsp;", "");
						//System.out.println("housing="+housing);
						Elements spans = where_class.getElementsByTag("span");
						for(Element span : spans){
								if("zone".equals(span.attr("class"))){
									//shape = span.getElementsByTag("span").get(0).getElementsByTag("span").get(0).html().replaceAll("&nbsp;", "");
									shape = span.child(0).html().replaceAll("&nbsp;", "");
									//System.out.println("shape="+shape);
								}else if("meters".equals(span.attr("class"))){
									size = span.html().replaceAll("&nbsp;", "");
									//System.out.println("size="+size);
									Element nextElementSibling = span.nextElementSibling();
									orientation = nextElementSibling.html().replaceAll("&nbsp;", "");
									//System.out.println("orientation="+orientation);
								}
						}
						Element col_2s = div.getElementsByClass("col-2").get(0);
						seeNum = col_2s.getElementsByClass("square").get(0).getElementsByTag("div").get(0).getElementsByTag("span").get(0).html().replaceAll("&nbsp;", "");
						//System.out.println("seeNum="+seeNum);
						Element col_3s = div.getElementsByClass("col-3").get(0);
						Element price_div = col_3s.getElementsByClass("price").get(0);
						price = price_div.getElementsByTag("span").get(0).html();
						//System.out.println("price="+price);
						updateTime = col_3s.getElementsByClass("price-pre").get(0).html();
						//System.out.println("updateTime="+updateTime);
						model.setHousing(housing);
						model.setOrientation(orientation);
						model.setPrice(price);
						model.setSeeNum(seeNum);
						model.setShape(shape);
						model.setSize(size);
						model.setTitle(title);
						String trim = updateTime.trim();
						updateTime=trim.substring(0,trim.length()-2).trim();
						//System.out.println(updateTime);
						model.setUpdateTime(updateTime);
						String md5_org = housing+orientation+price+seeNum+shape+size+title;
						//System.out.println(md5_org);
						String md5 = Utils.MD5(md5_org);
						model.setMd5(md5);
						dao.add(model);
						System.out.println("抓取一条数据!");
						SpiderManager.total.incrementAndGet();
					}
				}
			}
		}
	}
	

}
