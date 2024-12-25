package com.datastructure.travelsearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;

public class PageFilter {
	private static final int THRESHOLD = 3000;
	
	ArrayList<WebPage> results;
	ArrayList<Keyword> keywords;
	
	PageFilter(ArrayList<WebPage> results) {
		this.results = results;
		this.keywords = readKeywords();
	}
	
	public ArrayList<WebPage> filter() {
		ArrayList<WebPage> results_filtered = new ArrayList<WebPage>();
		
		ExecutorService executor = Executors.newScheduledThreadPool(Search.MAX_THREADS);
		ArrayList<Future<?>> futures = new ArrayList<Future<?>>();
		
		for(WebPage result : results) {
			futures.add(executor.submit(() -> result.setScore(keywords)));
		}
		
		for(Future<?> future : futures) {
			try {
				future.get();
			} catch(Exception e) {
				continue;
			}
		}
		
		for(WebPage result : results) {
			if (result.score >= THRESHOLD) {
				results_filtered.add(result);
			}
		}
		return results_filtered;
	}
	
	private ArrayList<Keyword> readKeywords() {
	    File file = new File("keywords.txt");
	    ArrayList<Keyword> list = new ArrayList<>();
	    
	    try (Scanner scanner = new Scanner(file).useLocale(Locale.ENGLISH)) { 
	    	while (scanner.hasNextLine()) {
	            String name = scanner.next();
	            double weight = scanner.nextDouble();
	            if (weight < 0 || name.isBlank()) {
	                System.out.println("InvalidOperation");
	                break;
	            }
	            list.add(new Keyword(name, weight));
	        }
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        return null;
	    }
	    return list;
	}
}
