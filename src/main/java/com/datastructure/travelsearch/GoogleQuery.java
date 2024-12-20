package com.datastructure.travelsearch;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleQuery {
     public String searchKeyword;
     public String url;
     public String content;

     public GoogleQuery(String searchKeyword) {
         this.searchKeyword = searchKeyword;
         try {
             // This part has been specially handled for Chinese keyword processing. 
        	 // You can comment out the following two lines 
        	 // and use the line of code in the lower section. 
        	 // Also, consider why the results might be incorrect 
        	 // when entering Chinese keywords.
        	 String encodeKeyword=java.net.URLEncoder.encode(searchKeyword,"utf-8");
        	 this.url = "https://www.google.com/search?q="+encodeKeyword+"&oe=utf8&num=15";
        	 System.out.println(this.url);

        	 // this.url = "https://www.google.com/search?q="+searchKeyword+"&oe=utf8&num=20";
         }
         catch (Exception e) {
        	 System.out.println(e.getMessage());
         }
     }

     public ArrayList<WebPage> query() throws IOException {
    	 if(content == null) {
    		 content = Utilities.fetchContent(url);
    	 }

    	 ArrayList<WebPage> retVal = new ArrayList<WebPage>();

		 /* 
		 * some Jsoup source
		 * https://jsoup.org/apidocs/org/jsoup/nodes/package-summary.html
		 * https://www.1ju.org/jsoup/jsoup-quick-start
		 */

    	 //using Jsoup analyze html string
    	 Document doc = Jsoup.parse(content);

    	 //select particular element(tag) which you want 
    	 Elements lis = doc.select("div");
    	 lis = lis.select(".kCrYT");

    	 for(Element li : lis) {
    		 try {
    			 String citeUrl = li.select("a").get(0).attr("href").replace("/url?q=", "");
    			 citeUrl = citeUrl.substring(0, citeUrl.indexOf("&sa="));
    			 String title = li.select("a").get(0).select(".vvjwJb").text();

    			 if(title.equals("")) {
    				 continue;
    			 }

    			 //put title and pair into HashMap
                 retVal.add(new WebPage(citeUrl, title));

             } catch (IndexOutOfBoundsException e) {
            	 // e.printStackTrace();
             }
         }
         return retVal;
     }
 }