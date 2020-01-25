package edu.lab.newsaggregator.crawler.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {

	public static String getSource(String url) {
		Matcher matcher = Pattern.compile("http.*?com").matcher(url);
		matcher.find();
		return matcher.group();
	}
}
