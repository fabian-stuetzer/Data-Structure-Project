package com.datastructure.travelsearch;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class Utilities {
	public static String fetchContent(String urlStr) {
		System.out.println("Fetching " + urlStr + "...");
		try {
	    	 String retVal = "";

	    	 URL url = URI.create(urlStr).toURL();
	    	 URLConnection conn = url.openConnection();
	    	 conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107");
	    	 conn.setConnectTimeout(3000);
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
