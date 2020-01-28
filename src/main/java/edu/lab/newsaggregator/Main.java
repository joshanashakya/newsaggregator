package edu.lab.newsaggregator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.lab.newsaggregator.common.FileReaderWriter;
import edu.lab.newsaggregator.crawler.Crawler;
import edu.lab.newsaggregator.crawler.NewsCrawler;
import edu.lab.newsaggregator.news.NewsContent;
import edu.lab.newsaggregator.news.extractor.NewsExtractor;
import edu.lab.newsaggregator.news.extractor.NewsExtractorFactory;

public class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
	private static final AppProperties PROPERTIES = AppProperties.getInstance();

	public static void main(String[] args) {
		Main main = new Main();
		main.crawl();
		main.download();
		main.extract();
		main.collectTitles();
	}

	private void crawl() {
		Crawler crawler = new NewsCrawler(PROPERTIES.get("seed"), 5000);
		List<String> urls = crawler.crawl();
		FileReaderWriter.write(urls, PROPERTIES.get("urls"));
	}

	private void download() {
		List<String> urls = FileReaderWriter.read(PROPERTIES.get("urls"), false);
		int count = 1;
		for (String url : urls) {
			LOGGER.log(Level.INFO, "Started downloading " + url);
			LOGGER.log(Level.INFO, "Count: " + count);
			FileReaderWriter.download(url, true, String.valueOf(count));
			count++;
		}
	}

	private void extract() {
		File folder = new File(PROPERTIES.get("pages"));
		File[] files = folder.listFiles();
		int count = 1;
		for (File file : files) {
			NewsExtractor extractor = NewsExtractorFactory.get(FileReaderWriter.read(file));
			System.out.println(extractor.getClass());
			System.out.println(file.getName());
			NewsContent news = extractor.extract(file);
			FileReaderWriter.write(Arrays.asList(new String[] { news.getTitle(), news.getContent() }),
					"downloads/news/" + file.getName());
			if (count == 100)
				break;
			count++;
		}
	}

	private void collectTitles() {
		File folder = new File(PROPERTIES.get("news"));
		File[] files = folder.listFiles();
		List<String> titles = new ArrayList<>();
		for (File file : files) {
			List<String> lines = FileReaderWriter.read(file.getPath(), false);
			titles.add(lines.get(0));
		}
		FileReaderWriter.write(titles, PROPERTIES.get("titles"));
	}
}
