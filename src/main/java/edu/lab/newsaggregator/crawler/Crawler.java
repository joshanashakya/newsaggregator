package edu.lab.newsaggregator.crawler;

import java.util.List;

public interface Crawler {

	/**
	 * 1. Initializes queue 'Q' with initial set of seed urls. 
	 * 2. Until the number of pages to crawl limit exceeds
	 * 2.1. Pop url 'L' from the 'Q'
	 * 2.2. If 'L' is not an HTML page then continue loop
	 * 2.3. If 'L' is already visited then continue loop
	 * 2.4. Download page 'P' for 'L'
	 * 2.5. If cannot download 'P' then continue loop (or if content is empty continue loop)
	 * 2.6. Index 'P' // not yet done
	 * 2.7. Parse 'P' to obtain list of new links 'N' (and remove duplicate urls)
	 * 2.8. Append 'N' to the end of 'Q'
	 * 3. Loop
	 * 
	 * @return collection of url
	 */
	List<String> crawl();
}
