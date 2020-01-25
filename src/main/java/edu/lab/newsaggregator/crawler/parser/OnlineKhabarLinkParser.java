package edu.lab.newsaggregator.crawler.parser;

public class OnlineKhabarLinkParser extends LinkParser {

	@Override
	public String getPattern(String url) {
		return "(<div.*?class=\"soft__wrap\".*?>.*?<a.*?href=\"(.*?)\".*?>.*?</a>.*?</div>)";
	}
}
