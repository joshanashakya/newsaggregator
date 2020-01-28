package edu.lab.newsaggregator.news.extractor;

public class NewsExtractorFactory {

	private NewsExtractorFactory() {

	}

	public static NewsExtractor get(String source) {
		if (source.contains("https://ekantipur.com"))
			return new EkantipurNewsExtractor();
		if (source.contains("https://www.onlinekhabar.com"))
			return new OnlinekhabarNewsExtractor();
		if (source.contains("https://ratopati.com"))
			return new RatopatiNewsExtractor();
		if (source.contains("https://www.setopati.com"))
			return new SetopatiNewsExtractor();
		return null;
	}
}
