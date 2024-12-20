package com.datastructure.travelsearch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TreeBuilder {
	private static final int MAX_DEPTH = 1;
	private static final int MAX_CHILDREN = 5;
	
	WebPage page;
	WebTree tree;
	
	TreeBuilder(WebPage page) {
		this.page = page;
	}
	
	public void initializeTree() {
		tree = new WebTree(page);
		createChildren(tree.root, 1);
	}
	
	private static void createChildren(WebNode node, int depth) {
	    if (depth > MAX_DEPTH) {
	        return; 
	    }
	    String content = Utilities.fetchContent(node.webPage.url);
	    Document doc = Jsoup.parse(content);
	    Elements links = doc.select("a[href]"); 
	    int count = 0; 
	    for (Element link : links) {
	        if (count >= MAX_CHILDREN) {
	            break; // If 5 sub-webpages have been extracted, stop looping
	        }

	        String url = link.attr("href"); 
	        String name = link.text();

	        if (url == null || url.isEmpty() || name == null || name.isEmpty()) {
	            continue;
	        }

	        WebNode child = new WebNode(new WebPage(url, name));
	        node.addChild(child); 
	        
	        createChildren(child, depth + 1);

	        count++;
	    }
	}
}