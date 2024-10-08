package data_structure_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class WordCounter {
	private String urlStr;
    private String content;
    
    public WordCounter(String urlStr){
    	this.urlStr = urlStr;
    }
    
    private String fetchContent() throws IOException{
		URL url = URI.create(this.urlStr).toURL();
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
	
		String retVal = "";
	
		String line = null;
		
		while ((line = br.readLine()) != null){
		    retVal = retVal + line + "\n";
		}
	
		return retVal;
    }
    
    private int BoyerMoore(String T, String P){
		int n = T.length();
		int m = P.length();
		for (int i = 0; i <= n-m; i++) {
			int j = m-1;
			while (j >= 0) {
				if (T.charAt(i + j) != P.charAt(j)) {
					i = i + j - last(P.charAt(j), P);
					break;
				}
				j--;
			}
			if (j == -1) {
				return i;
			}
		}
		return -1;
    }

    private int last(char c, String P){
		for (int i = P.length() - 1; i >= 0; i--) {
			if (P.charAt(i) == c) {
				return i;
			}
		}
		return -1;
    }
    
    public int countKeyword(String keyword) throws IOException {
		if (content == null){
		    content = fetchContent();
		}
		
		content = content.toUpperCase();
		keyword = keyword.toUpperCase();
	
		int retVal = 0; 
		int index = BoyerMoore(content, keyword);
		
		while (index != -1) {
			content = content.substring(index+1);
			retVal++;
			index = BoyerMoore(content, keyword);
		}
	
		return retVal;
    }
}
