package com.datastructure.travelsearch;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class Search {
	public static PriorityQueue<WebPage> search(String query) throws IOException {
		
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
		PriorityQueue<WebPage> results_sorted = new PriorityQueue<WebPage>((s1, s2) -> Double.compare(s2.tf_idf_score, s1.tf_idf_score));
		
		for (int i = 0; i < result_trees.size(); i++) {
			double score = 0;
			for (String keyword : query.split(" ")) {
				score += tf_idf.getScore(keyword, corpus[i]);
			}
			result_trees.get(i).root.webPage.tf_idf_score = score;
			results_sorted.add(result_trees.get(i).root.webPage);
		}		
		System.out.println("Search completed.");
		return results_sorted;
	}
}