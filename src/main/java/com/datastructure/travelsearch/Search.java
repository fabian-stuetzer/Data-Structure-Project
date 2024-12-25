package com.datastructure.travelsearch;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.ArrayList;

public class Search {
	static final int MAX_THREADS = 10;
	
	public static PriorityQueue<WebPage> search(String query) throws IOException {
		
		if (!query.contains("travel")) {
			query = query + " travel";
		}
		
		GoogleQuery gq = new GoogleQuery(query);
		ArrayList<WebPage> results = gq.query();
		System.out.print(results);
		
		PageFilter filter = new PageFilter(results);
		System.out.println("\nFiltering results...\n");
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
		
		if(results_sorted.isEmpty()) {
			WebPage wp = new WebPage("localhost:8080", "No results");
			wp.snippet = "No results for your search were found. You can return to the start page or try a different search term.";
			PriorityQueue<WebPage> pq = new PriorityQueue<WebPage>();
			pq.add(wp);
			return pq;
		}
		
		return results_sorted;
	}
}