package edu.lab.newsaggregator.news.extractor;

import java.io.File;

import edu.lab.newsaggregator.news.NewsContent;

public abstract class NewsExtractor {
	
	public abstract NewsContent extract(File file);
}
