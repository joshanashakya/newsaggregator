package edu.lab.newsaggregator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.lab.newsaggregator.cluster.Cluster;
import edu.lab.newsaggregator.cluster.KMeans;
import edu.lab.newsaggregator.cluster.document.Document;
import edu.lab.newsaggregator.cluster.document.DocumentService;
import edu.lab.newsaggregator.cluster.document.DocumentServiceImpl;
import edu.lab.newsaggregator.cluster.document.VocabBuilder;
import edu.lab.newsaggregator.common.FileReaderWriter;
import edu.lab.newsaggregator.crawler.Crawler;
import edu.lab.newsaggregator.crawler.NewsCrawler;
import edu.lab.newsaggregator.news.NewsContent;
import edu.lab.newsaggregator.news.extractor.NewsExtractor;
import edu.lab.newsaggregator.news.extractor.NewsExtractorFactory;

public class AppServiceImpl implements AppService {

	private static final Logger LOGGER = Logger.getLogger(AppService.class.getName());
	private static final AppProperties PROPERTIES = AppProperties.getInstance();

	@Override
	public void listUrls(int num) {
		Crawler crawler = new NewsCrawler(PROPERTIES.get("seed"), num);
		List<String> urls = crawler.crawl().subList(0, num);
		FileReaderWriter.write(urls, PROPERTIES.get("urls"));
		consoleOutput("LIST OF URLS", urls);
	}

	@Override
	public void listTitles(int num, boolean flag) {
		List<String> urls = flag ? FileReaderWriter.read(PROPERTIES.get("urls"), false)
				: new NewsCrawler(PROPERTIES.get("seed"), num).crawl().subList(0, num);
		download(urls);
		extract();
		List<String> titles = collectTitles();
		FileReaderWriter.write(titles, PROPERTIES.get("titles"));
		consoleOutput("LIST OF TITLES", titles);
	}

	@Override
	public void cluster(int num, int noOfClusters) {
		DocumentService docService = new DocumentServiceImpl();
		List<Document> docs = docService.extract(PROPERTIES.get("news"));
		List<String> vocab = new VocabBuilder().build(docs);
		FileReaderWriter.write(vocab, PROPERTIES.get("vocabulary"));
		List<Document> docsWithWeights = docService.calWeights(docs, vocab);
		KMeans kmeans = new KMeans();
		List<Cluster> clusters = kmeans.cluster(docsWithWeights, noOfClusters);
		createFolder(clusters);
		List<String> output = Arrays.asList(format(clusters));
		FileReaderWriter.write(output, PROPERTIES.get("clusters.info"));
		consoleOutput("CLUSTERS", output);
	}

	@Override
	public void clean() {
		FileReaderWriter.delete(PROPERTIES.get("urls"));
		FileReaderWriter.delete(PROPERTIES.get("pages"));
		FileReaderWriter.delete(PROPERTIES.get("news"));
		FileReaderWriter.delete(PROPERTIES.get("titles"));
		FileReaderWriter.delete(PROPERTIES.get("vocabulary"));
		FileReaderWriter.delete(PROPERTIES.get("clusters"));
		FileReaderWriter.delete(PROPERTIES.get("clusters.info"));
	}

	private void download(List<String> urls) {
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
		for (File file : files) {
			LOGGER.log(Level.INFO, "Extracting file " + file.getName() + ".");
			NewsExtractor extractor = NewsExtractorFactory.get(FileReaderWriter.read(file));
			LOGGER.log(Level.INFO, "Extractor class: " + extractor.getClass().toString());
			NewsContent news = extractor.extract(file);
			FileReaderWriter.write(Arrays.asList(new String[] { news.getTitle(), news.getContent() }),
					PROPERTIES.get("news") + "/" + file.getName());
		}
	}

	private List<String> collectTitles() {
		File folder = new File(PROPERTIES.get("news"));
		File[] files = folder.listFiles();
		List<String> titles = new ArrayList<>();
		for (File file : files) {
			List<String> lines = FileReaderWriter.read(file.getPath(), false);
			titles.add(lines.get(0));
		}
		return titles;
	}

	private String format(List<Cluster> clusters) {
		StringBuilder sb = new StringBuilder();
		for (Cluster cluster : clusters) {
			List<Document> docs = cluster.getDocs();
			sb.append("Cluster Id: " + cluster.getId());
			sb.append("\n");
			sb.append("Number of documents: " + docs.size());
			sb.append("\n");
			for (Document doc : docs) {
				sb.append(doc.toString());
				sb.append("\n");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	private void consoleOutput(String title, List<String> list) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("************************* ");
		sb.append(title);
		sb.append(" *************************");
		sb.append("\n");
		for (String s : list) {
			sb.append(s);
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}

	private void createFolder(List<Cluster> clusters) {
		String newsPath = PROPERTIES.get("news");
		String clusterPath = PROPERTIES.get("clusters");
		for (Cluster cluster : clusters) {
			File file = new File(String.format("%s/%s", clusterPath, cluster.getId()));
			file.mkdir();
			String path = file.getPath();
			List<Document> docs = cluster.getDocs();
			for (Document doc : docs) {
				String id = doc.getId();
				List<String> str = FileReaderWriter.read(String.format("%s/%s", newsPath, id), false);
				FileReaderWriter.write(str, String.format("%s/%s", path, id));
			}
		}
	}
}