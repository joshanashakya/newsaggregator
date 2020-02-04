package edu.lab.newsaggregator.crawler;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.lab.newsaggregator.common.FileReaderWriter;
import edu.lab.newsaggregator.crawler.parser.LinkParser;
import edu.lab.newsaggregator.crawler.parser.LinkParserFactory;
import edu.lab.newsaggregator.crawler.parser.Utilities;

public class NewsCrawler implements Crawler {

	private String seedFileName;
	private int limit;
	private static final Logger LOGGER = Logger.getLogger(NewsCrawler.class.getName());

	public NewsCrawler(String seedFileName, int limit) {
		this.seedFileName = seedFileName;
		this.limit = limit;
	}

	@Override
	public List<String> crawl() {
		List<String> seed = initialize();
		List<String> urls = new ArrayList<>();
		List<String> visited = new ArrayList<>();
		urls.addAll(seed);
		int size = urls.size();
		for (int i = 0; i < size; i++) {
			String url = urls.get(i);
			visited.add(url);
			LOGGER.info("Crawling " + url);
			if (!isHtml(url)) {
				LOGGER.log(Level.INFO, "Not an HTML page.");
				continue;
			}
			if (visited.contains(url)) {
				LOGGER.log(Level.INFO, "Already visited.");
				continue;
			}
			String content = FileReaderWriter.download(url, false, null);
			if (isEmpty(content)) {
				LOGGER.log(Level.INFO, "The page is empty.");
				continue;
			}
			// TODO index
			urls.addAll(parse(url, content));
			urls = removeDuplicate(urls);
			size = urls.size();
			if (size >= limit - seed.size())
				break;
		}
		urls.removeAll(seed);
		return urls;
	}

	List<String> initialize() {
		return FileReaderWriter.read(seedFileName, true);
	}

	boolean isHtml(String pageUrl) {
		try {
			URL url = new URL(pageUrl);
			URLConnection connection = url.openConnection();
			String type = connection.getHeaderField("Content-Type");
			LOGGER.info("Content-Type: " + type);
			return type.equalsIgnoreCase("text/html; charset=UTF-8") || type.equalsIgnoreCase("text/html");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	boolean isEmpty(String content) {
		return content == null || content.isEmpty();
	}

	List<String> parse(String url, String content) {
		String source = Utilities.getSource(url);
		LOGGER.log(Level.INFO, source);
		LinkParser parser = LinkParserFactory.get(source);
		return parser.parse(url, content);
	}

	private List<String> removeDuplicate(List<String> urls) {
		Set<String> set = new LinkedHashSet<>(urls);
		return new ArrayList<>(set);
	}
}
