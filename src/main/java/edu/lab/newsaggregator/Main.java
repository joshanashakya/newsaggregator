package edu.lab.newsaggregator;

import edu.lab.newsaggregator.crawler.Crawler;
import edu.lab.newsaggregator.crawler.NewsCrawler;

public class Main {

	public static void main(String[] args) {
		Crawler crawler = new NewsCrawler("seed", 10000);
		crawler.crawl();
	}
}
