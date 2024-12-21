package com.datastructure.travelsearch;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleQuery {
	private static final int NUM_RESULTS = 2;
	
     public String searchKeyword;
     public String url;
     public String content;

     public GoogleQuery(String searchKeyword) {
         this.searchKeyword = searchKeyword;
         try {
        	 String encodeKeyword=java.net.URLEncoder.encode(searchKeyword,"utf-8");
        	 this.url = "https://www.google.com/search?q="+encodeKeyword+"&oe=utf8&num=" + NUM_RESULTS;
        	 System.out.println(this.url);
         } catch (Exception e) {
        	 System.out.println(e.getMessage());
         }
     }

     public ArrayList<WebPage> query() throws IOException {
    	 if(content == null) {
    		 content = Utilities.fetchContent(url);
    	 }

    	 ArrayList<WebPage> retVal = new ArrayList<WebPage>();

    	 Document doc = Jsoup.parse(content);

    	 //select particular element(tag) which you want 
    	 Elements lis = doc.select("div");
    	 lis = lis.select(".Gx5Zad.xpd.EtOod.pkphOe");

    	 for(Element li : lis) {
    		 try {
    			 String citeUrl = li.selectFirst("a").attr("href").replace("/url?q=", "");
    			 citeUrl = citeUrl.substring(0, citeUrl.indexOf("&sa="));
    			 String title = li.selectFirst("a").select(".vvjwJb").text();
    			 String snippet = li.select(".BNeawe.s3v9rd.AP7Wnd > div > div > div").text();
    			 
    			 if(title.equals("")) {
    				 continue;
    			 }

    			 //put title and pair into HashMap
                 retVal.add(new WebPage(citeUrl, title, snippet));

             } catch (IndexOutOfBoundsException e) {
            	 // e.printStackTrace();
             }
         }
         return retVal;
     }
 }