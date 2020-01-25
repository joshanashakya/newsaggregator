package edu.lab.newsaggregator.crawler.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EkantipurLinkParser extends LinkParser {

	@Override
	public List<String> parse(String url, String content) {
		Set<String> links = new HashSet<>();
		String source = Utilities.getSource(url);
		String pattern = getPattern(url);
		Matcher matcher = Pattern.compile(pattern).matcher(content);
		while (matcher.find()) {
			String link = matcher.group(3);
			if (!link.startsWith("http"))
				link = String.format("%s/%s", source, link);
			links.add(link);
		}
		return new ArrayList<>(links);
	}

	@Override
	public String getPattern(String url) {
		if (url.endsWith(".html")) {
			return "(<div class=\"item normal\">.*?(<a.*?href=\"(.*?)\".*?>.*?</a>).*?</div>)";
		}
		return "(<article.*?>.*?(<a.*?href=\"(.*?)\".*?>.*?</a>).*?</article>)";
	}
}
