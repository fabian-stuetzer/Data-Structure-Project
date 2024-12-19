package data_structure_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class PageFilter {
	private static final int THRESHOLD = 300;
	
	ArrayList<WebPage> results;
	ArrayList<Keyword> keywords;
	
	PageFilter(ArrayList<WebPage> results) {
		this.results = results;
		this.keywords = readKeywords();
	}
	
	public ArrayList<WebPage> filter() {
		ArrayList<WebPage> results_filtered = new ArrayList<WebPage>();
		for(WebPage result : results) {
			result.setScore(keywords);
			if (result.score >= THRESHOLD) {
				results_filtered.add(result);
			}
		}
		return results_filtered;
	}
	
	private ArrayList<Keyword> readKeywords() {
		File file = new File("keywords.txt");
		Scanner scanner;
		try {
			scanner = new Scanner(file).useLocale(Locale.ENGLISH); // Make sure the parsing isn't system-dependant
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		ArrayList<Keyword> list = new ArrayList<Keyword>();
		
		while (scanner.hasNextLine()) {
			String name = scanner.next();
			int count = scanner.nextInt();
			double weight = scanner.nextDouble();
			if (count < 0 || weight < 0 || name.isBlank()) {
				System.out.println("InvalidOperation");
				break;
			}
			list.add(new Keyword(name, count, weight));
		}
		scanner.close();
		return list;
	}
}
