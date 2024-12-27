package com.datastructure.travelsearch;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.ArrayList;

public class Search {
	static final int MAX_THREADS = 30;
	
	public static Pair<ArrayList<WebPage>, ArrayList<String>> search(String query) throws IOException {
		
		if (!query.contains("travel")) {
			query = "\"travel\" OR \"vacation\" to -product -shop -order -buy " + query;
		}
		
		GoogleQuery gq = new GoogleQuery(query);
		Pair<ArrayList<WebPage>, ArrayList<String>> query_return = gq.query();
		ArrayList<WebPage> results = query_return.get1();
		System.out.print(results);
		
		PageFilter filter = new PageFilter(results);
		results = filter.filter();
		System.out.print(results);
		
		ArrayList<WebTree> result_trees = new ArrayList<WebTree>();
		
		ExecutorService executor = Executors.newScheduledThreadPool(MAX_THREADS);
		ArrayList<Future<?>> futures = new ArrayList<Future<?>>();
		
		for (WebPage page : results) {
			TreeBuilder tb = new TreeBuilder(page);
			futures.add(executor.submit(() -> {tb.initializeTree(); result_trees.add(tb.tree);}));
		}
		
		for(Future<?> future : futures) {
			try {
				future.get();
			} catch (Exception e) {
				continue;
			}
		}
		
		System.out.println("\n\nGenerating corpus...\n");
		
		executor = Executors.newScheduledThreadPool(MAX_THREADS);
		futures = new ArrayList<Future<?>>();
		
		String[][] corpus = new String[result_trees.size()][];
		for (int i = 0; i < result_trees.size(); i++) {
			final int index = i;
			futures.add(executor.submit(() -> corpus[index] = result_trees.get(index).aggregateContents()));
		}
		
		for(Future<?> future: futures) {
			try {
				future.get();
			} catch (Exception e) {
				continue;
			}
		}
		
		TF_IDF tf_idf = new TF_IDF(corpus);
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
		
		ArrayList<WebPage> results_list = Utilities.heapToList(results_sorted);
		ArrayList<String> related = query_return.get2();
		
		return new Pair<ArrayList<WebPage>, ArrayList<String>>(results_list, related);
	}
}