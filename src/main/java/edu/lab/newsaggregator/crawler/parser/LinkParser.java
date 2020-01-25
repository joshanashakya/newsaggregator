package edu.lab.newsaggregator.crawler.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class LinkParser {

	public List<String> parse(String url, String content) {
		Set<String> links = new HashSet<>();
		String pattern = getPattern(url);
		Matcher matcher = Pattern.compile(pattern).matcher(content);
		while (matcher.find()) {
			String link = matcher.group(2);
			if (link.startsWith("http"))
				links.add(link);
		}
		return new ArrayList<>(links);
	}

	public abstract String getPattern(String url);
}
