package com.datastructure.travelsearch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TreeBuilder {
	private static final int MAX_DEPTH = 1;
	private static final int MAX_CHILDREN = 3;
	
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
	    links.stream()
	    	.filter(link -> !link.attr("href").isEmpty())
	    	.filter(link -> link.attr("href").startsWith("http://") || link.attr("href").startsWith("https://") || link.attr("href").startsWith("www."))
	    	.filter(link -> !link.attr("href").endsWith(".pdf"))
	    	.filter(link -> !link.attr("href").isEmpty() && !link.text().isEmpty())
	    	.limit(MAX_CHILDREN)
	    	.forEach(link -> {
		        String url = link.attr("href"); 
		        String name = link.text();
	
	
		        WebNode child = new WebNode(new WebPage(url, name));
		        node.addChild(child); 
		        
		        createChildren(child, depth + 1);
	    	});
	}
}