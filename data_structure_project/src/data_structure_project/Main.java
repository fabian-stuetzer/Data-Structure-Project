package data_structure_project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		String query = sc.next();
		sc.close();
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
		System.out.println();
	}
}