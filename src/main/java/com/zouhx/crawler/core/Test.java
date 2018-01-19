package com.zouhx.crawler.core;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zouhx.crawler.bean.DaZhongModel;
import com.zouhx.crawler.db.DaZhongDao;
import com.zouhx.crawler.db.impl.DaZhongDaoImpl;
import com.zouhx.crawler.util.Utils;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) throws Exception {
		Document document = Jsoup.connect("http://www.dianping.com/beijing/ch10/r1465").get();
				
		Element dataList = document.getElementById("shop-all-list");
		
		if(dataList!=null){
			parseDate(dataList);
		}
		
	}
	
	
	public static void parseDate(Element dataList) throws Exception{
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
		DaZhongModel model = new DaZhongModel();
		
		
		
		Elements lis = dataList.getElementsByTag("li");
		if(lis.size()>0){
			for (Element li : lis) {
				
				Elements txts = li.getElementsByClass("txt");
				
				if(txts==null||txts.size()==0)
					return;
				
				Element txt = txts.get(0);
				
				
				// tit
				Elements tit = txt.getElementsByClass("tit");
				if(tit.size()>0){
					Elements elementsByTag = tit.get(0).getElementsByTag("a").get(0).getElementsByTag("h4");
					String title = elementsByTag.html();
					model.setTit(title);
				}

				
				Elements comment = txt.getElementsByClass("comment");
				if(comment.size()>0){
					// starRank
					Elements e1 = comment.get(0).getElementsByTag("span");
					if(e1.size()>0){
						String starRank = e1.get(0).attr("title");
						model.setStarRank(starRank);
					}

					//commentNum
					Elements e2 = comment.get(0).getElementsByClass("review-num");
					if(e2.size()>0){
						String commentNum = e2.get(0).html();            // <b> 没去  ，空格没去
						model.setCommentNum(commentNum.replace("条点评", "").replace("<b>", "").replace("</b>", "").replace(" ", ""));
					}
					//cost
					Elements e3 = comment.get(0).getElementsByClass("mean-price");
					if(e3.size()>0){
						String cost = e3.get(0).html();
						model.setCost(cost.replace("￥","").replace("<b>", "").replace("</b>", "").replace(" ", ""));
					}
				}



				
				Elements tag_addr = txt.getElementsByClass("tag-addr");
				
				if(tag_addr.size()>0){
					Elements elementsByTag2 = tag_addr.get(0).getElementsByTag("a");
					for(int i=0;i<elementsByTag2.size();i++){
						if(i==0){
							//type
							Element a1 = elementsByTag2.get(0);
							String type = a1.getElementsByTag("span").get(0).html();
							model.setType(type);
						}
						if(i==1){
							//region
							Element a2 = elementsByTag2.get(1);
							String region = a2.getElementsByTag("span").get(0).html();
							model.setRegion(region);
						}
						
					}
					
					String site = tag_addr.get(0).getElementsByClass("addr").get(0).html();
					model.setSite(site);
				}
				
				
				
				//recommend
				Elements recommend = txt.getElementsByClass("recommend");
				if(recommend.size()>0){
					Elements as = recommend.get(0).getElementsByTag("a");
					String recommendFood = "";
					int size = as.size();
					for(int i=0;i<size;i++){
						Element a = as.get(i);
						recommendFood+=a.html();
						if(i+1!=size){
							recommendFood+=",";
						}
					}
					model.setRecommend(recommendFood);
				}
				
				//comment-list
				Elements comment_list = txt.getElementsByClass("comment-list");
				
				if(comment_list.size()>0){
					Elements spans = comment_list.get(0).getElementsByTag("span");
					String grade = "";
					int s = spans.size();
					
					for(int i=1;i<s;i++){
						Element element = spans.get(i);
						String html = element.html();
						
						html = html.replace("<span>", "").replace("</span>", "").replace("<b>", "").replace("</b>", "").replace(" ", "");
						
						grade+=html;
						
						if(i+1!=s){
							grade+=",";
						}
						
					}
					System.out.println(grade);
					model.setGrade(grade);
				}
				
				
				//svr-info
				
				
				
				Elements svrInfo = li.getElementsByClass("svr-info");
				
				if(svrInfo.size()>0){
					Elements ts = svrInfo.get(0).getElementsByTag("a");
					String discounts = "";
					int tSize = ts.size();
					
					for(int i=0;i<tSize;i++){
						String attr = ts.get(i).attr("title");
						discounts+=attr;
						if(i+1!=tSize){
							discounts+=",";
						}
					}
					model.setDiscounts(discounts);
				}
				String md5 = Utils.MD5(model.getTit()+model.getSite());
				
				System.out.println(md5);
				
				model.setMd5(md5);
				
				//DaZhongDao dao = new DaZhongDaoImpl();
				//dao.add(model);
				//SpiderManager.total.incrementAndGet();
			}
		}
	}
	
}
