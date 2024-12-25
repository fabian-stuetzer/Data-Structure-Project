package com.datastructure.travelsearch;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class Utilities {
	private static HashMap<String, String> contents = new HashMap<String, String>();
	
	public static String fetchContent(String urlStr) {
		if(!contents.containsKey(urlStr)) {
			contents.put(urlStr, fetchFromWeb(urlStr));
		}
		return contents.get(urlStr);
	}
	
	private static String fetchFromWeb(String urlStr) {
		if (urlStr.endsWith(".pdf") || !(urlStr.startsWith("http://") || urlStr.startsWith("https://") || urlStr.startsWith("www."))) {
			return "";
		}
		System.out.println("Fetching " + urlStr + "...");
		try {
	    	 String retVal = "";

	    	 URL url = URI.create(urlStr).toURL();
	    	 URLConnection conn = url.openConnection();
	    	 conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107");
	    	 conn.setConnectTimeout(3000);
	    	 conn.setReadTimeout(10000);
	    	 InputStream in = conn.getInputStream();

	    	 InputStreamReader inReader = new InputStreamReader(in, "utf-8");
	    	 BufferedReader bufReader = new BufferedReader(inReader);
	    	 String line = null;

	    	 while((line = bufReader.readLine()) != null) {
	    		 retVal += line;
	    	 }
	    	 return retVal;
		} catch(Exception e) {
			return "";
		}
	}
}
