package com.datastructure.travelsearch;

import java.util.HashMap;
import java.util.HashSet;

public class TF_IDF {
	
    private String[][] corpus; // Corpus: a collection of documents, where each document is an array of strings (words)
    private HashMap<String, Double> idf_dict; // Stores the IDF values for each term
    private HashMap<String, Integer> docFreqDict; // Stores the document frequency for each term

    // Constructor: initializes the corpus and prepares the data structures for IDF calculation
    public TF_IDF(String[][] corpus) {
    	this.corpus = corpus;
        this.idf_dict = new HashMap<String, Double>();
        this.docFreqDict = new HashMap<String, Integer>();
        preprocessCorpus();
    }
    
    // Preprocess the corpus to calculate document frequency (DF) for each term
    private void preprocessCorpus() {
        // Iterate through each document in the corpus
        for (String[] document : corpus) {
            HashSet<String> uniqueWords = new HashSet<>(); // Use a set to ensure no duplicate words per document
            
            for (String word : document) {
                uniqueWords.add(word.toLowerCase());
            }
            
            for (String word : uniqueWords) {
                docFreqDict.put(word, docFreqDict.getOrDefault(word, 0) + 1);
            }
        }
    }
    
    // Calculate Term Frequency (TF) for a keyword in the content
    public double getTF(String keyword, String[] content) {
        int keywordCount = 0; 
        
        for (String word : content) {
            if (word.equalsIgnoreCase(keyword)) {
                keywordCount++;
            }
        }
        if (keywordCount == 0) return 0.0;
        
        return 1 + Math.log(keywordCount); // Log-normalized term frequency
    }

    // Get the IDF for a keyword
    public double getIDF(String keyword) {
        String lowerKeyword = keyword.toLowerCase(); 
        if (idf_dict.get(lowerKeyword) == null) {
            double idf = calculateIDF(lowerKeyword);
            idf_dict.put(lowerKeyword, idf);
        }
        return idf_dict.get(lowerKeyword);
    }

    // Calculate the IDF value using the formula: log((N + 1) / (df + 1)) + 1, where N is the total number of documents, and df is the document frequency of the term
    private double calculateIDF(String keyword) {
        // Get the document frequency for the keyword, or 0 if the keyword is not found
        int docCountContainingKeyword = docFreqDict.getOrDefault(keyword, 0);
        // Calculate the smoothed IDF value to avoid division by zero or log(0)
        return Math.log((double) (corpus.length + 1) / (docCountContainingKeyword + 1)) + 1;
    }
    
    // Get the TF-IDF score for a specific keyword in a document
    public double getScore(String keyword, String[] content) {
        return getTF(keyword, content) * getIDF(keyword);
    }
}
