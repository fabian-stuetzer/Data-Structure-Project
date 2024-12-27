package com.datastructure.travelsearch;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleQuery {
	private static final int NUM_RESULTS = 30;
	
     public String searchKeyword;
     public String url;
     public String content;

     public GoogleQuery(String searchKeyword) {
         this.searchKeyword = searchKeyword;
         try {
        	 String encodeKeyword=java.net.URLEncoder.encode(searchKeyword,"utf-8");
        	 this.url = "https://www.google.com/search?q="+encodeKeyword+"&oe=utf8&num=" + NUM_RESULTS;
         } catch (Exception e) {
        	 System.out.println(e.getMessage());
         }
     }
     
     public Pair<ArrayList<WebPage>, ArrayList<String>> query() throws IOException {
    	 if(content == null) {
    		 content = Utilities.fetchContent(url);
    	 }

    	 ArrayList<WebPage> retVal = new ArrayList<WebPage>();

    	 Document doc = Jsoup.parse(content);

    	 Elements lis = doc.select("div[class='Gx5Zad xpd EtOod pkphOe']");
    	 lis = lis.select("div:has(div.egMi0)");

    	 for(Element li : lis) {
    		 try {
    			 String citeUrl = li.selectFirst("a").attr("href").replace("/url?q=", "");
    			 citeUrl = citeUrl.substring(0, citeUrl.indexOf("&sa="));
    			 String title = li.selectFirst("a").select(".vvjwJb").text();
    			 String snippet = li.select(".BNeawe.s3v9rd.AP7Wnd > div > div > div").text();
    			 
    			 if(title.equals("")) {
    				 continue;
    			 }

                 retVal.add(new WebPage(citeUrl, title, snippet));

             } catch (IndexOutOfBoundsException e) {
            	 continue;
             }
         }
    	 
    	 Elements rel = doc.select("a[class='Q71vJc']");
    	 ArrayList<String> related = new ArrayList<String>();
    	 for(Element related_element : rel) {
    		 related.add(related_element.text());
    	 }
    	 
         return new Pair<ArrayList<WebPage>, ArrayList<String>>(retVal, related);
     }
 }