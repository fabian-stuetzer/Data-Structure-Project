package data_structure_project;

import java.util.HashMap;

public class TF_IDF implements ScoreProvider {

	private String[][] corpus;
	private HashMap<String, Double> idf_dict;
	
	public TF_IDF(String[][] corpus) {
		this.corpus = corpus;
		this.idf_dict = new HashMap<String, Double>();
	}
	
	public double getScore(String keyword, String[] content) {
		return getTF(keyword, content) * getIDF(keyword);
	}
	
	public double getTF(String keyword, String[] content) {
		return 1;
	}

	public double getIDF(String keyword) {
		double idf;
		if(this.idf_dict.get(keyword) == null) {
			idf = calculateIDF(keyword);
			this.idf_dict.put(keyword, idf);
		} else {
			idf = this.idf_dict.get(keyword); 
		}
		return idf;
	}

	private double calculateIDF(String keyword) {
		return 1;
	}
}
