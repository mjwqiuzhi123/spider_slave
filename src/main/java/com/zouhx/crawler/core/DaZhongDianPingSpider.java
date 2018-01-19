package com.zouhx.crawler.core;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zouhx.crawler.bean.DaZhongModel;
import com.zouhx.crawler.db.DaZhongDao;
import com.zouhx.crawler.db.impl.DaZhongDaoImpl;
import com.zouhx.crawler.util.Utils;

public class DaZhongDianPingSpider extends AbstractParse{
	public static final String  ROOTURL = "http://www.dianping.com";
	public DaZhongDianPingSpider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void parse(Page page) {

		   
		   
		   page.setHeaders("Accept-Encoding", "gzip, deflate");
		   page.setHeaders("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		   page.setHeaders("Connection", "keep-alive");
		   page.setHeaders("Upgrade-Insecure-Requests", "1");
		   page.setHeaders("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:51.0) Gecko/20100101 Firefox/51.0");
		   //page.setHeaders("Referer", "http://www.dianping.com/");
		   
		   Document document = page.getDocument();
		   
		   
		   if(document==null){
			   page.addNext(page.getUrl());
			   return;
		   }
		   
		   
		   Elements elementsByClass = document.getElementsByClass("nc_list");       //nc_list
		   
		   if(elementsByClass.size()>0){
			   tier(elementsByClass,page);
		   }
		   
		   
		   // main侧面
		   Elements jsAuto = document.getElementsByClass("J_auto-load"); //script中
		   
		   if(jsAuto.size()>0){
			   jsAutoClassParse(jsAuto,page);
		   }
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   //以下主页内容
		   
		   
		   
		   
		   
		   //全部美食分类
		   Element all = document.getElementById("cate-index");
			
		   if(all!=null){
			   addAllParse(all, page);
		   }
		   
		   //底部			
		   //1city
		   Elements bottomCity = document.getElementsByClass("J-city-link");
			
		   if(bottomCity.size()>0){
				bottomCityParse(bottomCity, page);
		   }
		   //2 J-city-food
		   
		   Elements remFood = document.getElementsByClass("J-city-link");
			
		   if(remFood.size()>0){
				bottomCityParse(remFood, page);
		   }
		   
		   
		   
		   
		   //导航
		   Elements navigation = document.getElementsByClass("navigation");
		   
		   if(navigation.size()>0){
			   parseNavigation(navigation, page);
		   }
		   //添加下一页
		   Elements pages = document.getElementsByClass("page");
		   
		   if(pages.size()>0){
			   parsePage(pages, page);
		   }
		   //数据列
		   Element dataList = document.getElementById("shop-all-list");
		   
		   if(dataList!=null){
			   try {
				parseDate(dataList, page);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
		   
		   
	}
	
	public void bottomCityParse(Elements bottomCity ,Page page){
		Element cityFood = bottomCity.get(0);
		Elements as = cityFood.getElementsByTag("a");
		List<String> list = new ArrayList<String>();
		for (Element a : as) {
			String href = a.attr("href");
			if(href!=null&&!"".equals(href)){
				//System.out.println(href);
				list.add(href);
			}
		}
		int size = list.size();
		page.addNext(list.toArray(new String[size]));

	}
	
	
	public void addAllParse(Element all,Page page){
		Elements as = all.getElementsByTag("a");
		List<String> list = new ArrayList<String>();
		for (Element element : as) {
			String attr = element.attr("href");
			if(attr!=null&&!"".equals(attr)){
				//System.out.println(attr);
				list.add(attr);
			}
		}
		int size = list.size();
		page.addNext(list.toArray(new String[size]));
	}
	
	
	
	public void jsAutoClassParse(Elements elems,Page page){
		if(elems.size()>0){
			Element element = elems.get(0);
			
			//System.out.println(element.html());
			
			Document parse = Jsoup.parse(element.html());
			
			Elements elementsByClass4 = parse.getElementsByClass("f_pop_penel");
			
			//System.out.println("f_pop_penel.size="+elementsByClass4.size());
			
			List<String> list = new ArrayList<String>();
			for (Element element2 : elementsByClass4) {
				Elements elementsByTag = element2.getElementsByTag("a");
				for (Element element3 : elementsByTag) {
					String href = element3.attr("href");
					if(href!=null&&!"".equals(href)){
						list.add(ROOTURL+href);
					}
				}
			}
			int size = list.size();
			page.addNext(list.toArray(new String[size]));
			
			
			
		}else{
			System.out.println("class 2 not exists !");
		}
	}
	
	
	
	
	public void parseDate(Element dataList,Page page) throws Exception{
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
				
				System.out.println(Thread.currentThread().getName()+" "+md5);
				
				model.setMd5(md5);
				
				DaZhongDao dao = new DaZhongDaoImpl();
				dao.add(model);
				SpiderManager.total.incrementAndGet();
			}
		}
	}
	
	public void parsePage(Elements pages, Page page){
		Element p1 = pages.get(0);
		Elements as = p1.getElementsByTag("a");
		if(as.size()>0){
			List<String> list = new ArrayList<String>();
			for (Element a : as) {
				String href = a.attr("href");
				//System.out.println("next page add :"+href);
				list.add(href);
			}
			int size = list.size();
			page.addNext(list.toArray(new String[size]));
		}
	}
	
	public void tier(Elements elementsByClass,Page page){
		   List<String> list = new ArrayList<String>();
		   for (Element nc_list : elementsByClass) {
			   Elements lis = nc_list.getElementsByTag("li");
			   if(lis.size()>0){
				   for (Element ls : lis) {
						Elements as = ls.getElementsByTag("a");
						if(as.size()>0){
							Element a = as.get(0);
							String attr = a.attr("href");
							if(attr!=null&&!"".equals(attr)) {
								String url = ROOTURL+attr;
								//System.out.println(url);
								//page.addNext(url);
								list.add(url);
							}
						}
				   }
			   }
		   }
		   int size = list.size();
		   page.addNext(list.toArray(new String[size]));
	}
	
	public void parseNavigation(Elements navigation,Page page){
		Element element = navigation.get(0);
		Elements as = element.getElementsByTag("a");
		List<String> list = new ArrayList<String>();
		for (Element a : as) {
			String href = a.attr("href");
			boolean matches = href.matches(".*"+ROOTURL+".*");
			if(matches){
				//System.out.println(href);
				//page.addNext(href);
				list.add(href);
			}
		}
		int size = list.size();
		page.addNext(list.toArray(new String[size]));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws Exception {

	}
}
