package com.datastructure.travelsearch;

import java.util.ArrayList;

public class WebPage{
	public String url;
	public String name;
	public WordCounter counter;
	public double score;
	public String content;

	public WebPage(String url, String name){
		this.url = url;
		this.name = name;
		this.counter = new WordCounter(url);
	}

	public void setScore(ArrayList<Keyword> keywords) {
		score = 0;
		WordCounter wc = new WordCounter(this.url);
		for (Keyword keyword : keywords) {
			score += wc.countKeyword(keyword.name) * keyword.weight;
		}
	}
	
	public String toString() {
		return "\n[" + this.url + ", " + this.name + "]";
	}
}