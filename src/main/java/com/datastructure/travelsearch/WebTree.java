package com.datastructure.travelsearch;

import java.io.IOException;
import java.util.ArrayList;

public class WebTree{
	public WebNode root;

	public WebTree(WebPage rootPage){
		this.root = new WebNode(rootPage);
	}

	public void setPostOrderScore(ArrayList<Keyword> keywords) throws IOException{
		setPostOrderScore(root, keywords);
	}

	private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException{
		for(WebNode child : startNode.children) {
			setPostOrderScore(child, keywords);
		}
		startNode.setNodeScore(keywords);
		// YOUR TURN
		// 3. compute the score of children nodes via post-order, then setNodeScore for
		// startNode

	}

	public void eularPrintTree(){
		eularPrintTree(root);
	}

	private void eularPrintTree(WebNode startNode){
		int nodeDepth = startNode.getDepth();

		if (nodeDepth > 1)
			System.out.print("\n" + repeat("\t", nodeDepth - 1));

		System.out.print("(");
		System.out.print(startNode.webPage.name + "," + startNode.nodeScore);
		
		// YOUR TURN
		// 4. print child via pre-order
		for(WebNode child : startNode.children) {
			eularPrintTree(child);
		}

		System.out.print(")");

		if (startNode.isTheLastChild())
			System.out.print("\n" + repeat("\t", nodeDepth - 2));
	}
	
	public String[] aggregateContents() {
		return aggregateContents(this.root, new ArrayList<String>());
	}
	
	private static String[] aggregateContents(WebNode node, ArrayList<String> contents) {
		String content = Utilities.fetchContent(node.webPage.url);
		for (String word : content.split(" ")) {
			contents.add(word);
		}
		for (WebNode child : node.children) {
			aggregateContents(child, contents);			
		}
		return contents.toArray(new String[contents.size()]);
	}

	private String repeat(String str, int repeat){
		String retVal = "";
		for (int i = 0; i < repeat; i++){
			retVal += str;
		}
		return retVal;
	}
}