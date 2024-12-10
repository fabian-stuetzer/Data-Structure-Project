package data_structure_project;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TreeBuilder {
	private static final int MAX_DEPTH = 1;
	
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
		if(depth > MAX_DEPTH) {
			return;
		}
		String content = Utilities.fetchContent(node.webPage.url);
		Document doc = Jsoup.parse(content);
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			String url = link.attr("href");
			String name = link.text();
			WebNode child = new WebNode(new WebPage(url, name));
			node.addChild(child);
			createChildren(child, depth+1);
		}
	}
}