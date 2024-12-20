package com.datastructure.travelsearch;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

public class Search {
	public static HashMap<String, String> search(String query) throws IOException {
		/***
		if (!query.contains("travel")) {
			query = query + " travel";
		}
		
		GoogleQuery gq = new GoogleQuery(query);
		ArrayList<WebPage> results = gq.query();
		System.out.print(results);
		
		PageFilter filter = new PageFilter(results);
		results = filter.filter();
		System.out.println("Filtering results...");
		System.out.print(results);
		
		ArrayList<WebTree> result_trees = new ArrayList<WebTree>();
		for (WebPage page : results) {
			TreeBuilder tb = new TreeBuilder(page);
			tb.initializeTree();
			result_trees.add(tb.tree);
		}
		
		String[][] corpus = new String[result_trees.size()][];
		for (int i = 0; i < result_trees.size(); i++) {
			corpus[i] = result_trees.get(i).aggregateContents();
		}
		
		TF_IDF_Singleton tf_idf = TF_IDF_Singleton.getInstance(corpus);
		double[] score = new double[result_trees.size()];
		for (int i = 0; i < result_trees.size(); i++) {
			score[i] = 0;
			for (String keyword : query.split(" ")) {
				score[i] += tf_idf.getScore(keyword, corpus[i]);
			}
		}
		
		System.out.println(Arrays.toString(score));
		System.out.println();***/
		
		HashMap<String, String> example = new HashMap<String,String>(); //Return the search results in the right order as a HashMap
		example.put("Fang", "http://soslab.nccu.edu.tw/Courses.html");
		example.put("NCCU", "https://www.nccu.edu.tw/");
		example.put("Banana", "https://en.wikipedia.org/wiki/Banana");
		return example;
	}
}