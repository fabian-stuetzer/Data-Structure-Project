package com.datastructure.travelsearch;

import java.io.IOException;
import java.util.ArrayList;

public class WebNode {
	static final double WEIGHT = 0.6;
	public WebNode parent;
	public ArrayList<WebNode> children;
	public WebPage webPage;
	public double nodeScore;// This node's score += all its children's nodeScore

	public WebNode(WebPage webPage) {
		this.webPage = webPage;
		this.children = new ArrayList<WebNode>();
	}

	public void setNodeScore(ArrayList<Keyword> keywords) throws IOException {
		webPage.setScore(keywords);
		nodeScore = webPage.score;
		for(WebNode child : children) {
			nodeScore += WEIGHT * child.nodeScore;
		}		
	}

	public void addChild(WebNode child) {
		this.children.add(child);
		child.parent = this;
	}

	public boolean isTheLastChild() {
		if (this.parent == null)
			return true;
		ArrayList<WebNode> siblings = this.parent.children;

		return this.equals(siblings.get(siblings.size() - 1));
	}

	public int getDepth() {
		int retVal = 1;
		WebNode currNode = this;
		while (currNode.parent != null) {
			retVal++;
			currNode = currNode.parent;
		}
		return retVal;
	}
}
