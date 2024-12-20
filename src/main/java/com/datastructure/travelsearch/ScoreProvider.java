package com.datastructure.travelsearch;

public interface ScoreProvider {
	/**
	 * 
	 * @param keyword	The keyword for which the score should be calculated. It's a single word, so if the search term consists of more than one word, the function will be called multiple times.
	 * @param content	The content of the website, preprocessed and tokenized. Each field in the array represents one word.
	 * @return			TF-IDF score of the given content and keywords
	 */
	public double getScore(String keyword, String[] content);
	
	/**
	 * 
	 * @param keyword	The keyword whose Term Frequency should be calculated
	 * @param content	The content of the website, preprocessed and tokenized
	 * @return			TF-score of the given keyword
	 */
	public double getTF(String keyword, String[] content);
	
	/**
	 * 
	 * @param keyword	The keyword whose Inverse Document Frequency should be calculated
	 * @return			IDF-score of the given keyword
	 */
	public double getIDF(String keyword);
}