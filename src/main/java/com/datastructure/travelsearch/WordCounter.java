package com.datastructure.travelsearch;

public class WordCounter
{
	private String urlStr;
	private String content;

	public WordCounter(String urlStr)
	{
		this.urlStr = urlStr;
	}

	public int countKeyword(String keyword)
	{
		if (content == null)
		{
			content = Utilities.fetchContent(urlStr);
		}

		// To do a case-insensitive search, we turn the whole content and keyword into
		// upper-case:
		content = content.toUpperCase();
		keyword = keyword.toUpperCase();

		int retVal = 0;
		int fromIdx = 0;
		int found = -1;

		while ((found = content.indexOf(keyword, fromIdx)) != -1)
		{
			retVal++;
			fromIdx = found + keyword.length();
		}

		return retVal;
	}
}
