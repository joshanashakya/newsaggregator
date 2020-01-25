package edu.lab.newsaggregator.crawler.parser;

public class LinkParserFactory {

	private LinkParserFactory() {

	}

	public static LinkParser get(String source) {
		if ("https://ekantipur.com".equals(source) || "https://www.ekantipur.com".equals(source))
			return new EkantipurLinkParser();
		if ("https://www.onlinekhabar.com".equals(source))
			return new OnlineKhabarLinkParser();
		if ("https://ratopati.com".equals(source))
			return new RatopatiLinkParser();
		if ("https://www.setopati.com".equals(source))
			return new SetopatiLinkParser();
		return null;
	}
}
