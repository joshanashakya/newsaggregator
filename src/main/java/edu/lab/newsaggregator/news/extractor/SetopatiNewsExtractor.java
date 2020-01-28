package edu.lab.newsaggregator.news.extractor;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import edu.lab.newsaggregator.news.NewsContent;

public class SetopatiNewsExtractor extends NewsExtractor {

	@Override
	public NewsContent extract(File file) {
		try {
			Document doc = Jsoup.parse(file, "utf-8");
			Elements elements = doc.getElementsByClass("news-detail-section");
			if (elements.size() == 0)
				elements = doc.getElementsByClass("content-editor");
			String title = elements.get(0).getElementsByTag("span").get(0).text();

			Elements ps = doc.getElementsByClass("detail-box").get(0).getElementsByTag("p");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < ps.size(); i++)
				sb.append(ps.get(i).text());
			String content = sb.toString();
			return new NewsContent(title, content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
