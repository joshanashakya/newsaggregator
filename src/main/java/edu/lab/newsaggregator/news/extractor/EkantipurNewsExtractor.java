package edu.lab.newsaggregator.news.extractor;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import edu.lab.newsaggregator.news.NewsContent;

public class EkantipurNewsExtractor extends NewsExtractor {

	@Override
	public NewsContent extract(File file) {
		try {
			Document doc = Jsoup.parse(file, "utf-8");
			Elements elements = doc.getElementsByTag("article");
			String title = elements.get(0).getElementsByTag("h1").get(0).text();

			Elements p = doc.getElementsByClass("description").get(0).getElementsByTag("p");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < p.size(); i++)
				sb.append(p.get(i).text());
			String content = sb.toString();
			return new NewsContent(title, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
