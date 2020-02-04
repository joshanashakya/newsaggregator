package edu.lab.newsaggregator;

public interface AppService {

	/**
	 * Uses seed urls and extract other urls from the page.
	 * 
	 * @param num the number of urls
	 */
	void listUrls(int num);

	/**
	 * Downloads pages from urls, parses each page and extracts the title from page
	 * including the page content.
	 * 
	 * @param num  the number of urls for title extraction
	 * @param flag the value for determining whether to extract titles from existing
	 *             urls or new urls
	 */
	void listTitles(int num, boolean flag);

	/**
	 * From news folder, reads the news content and prepares the documents.
	 * Constructs the vocabulary from the news contents, computes weights of each
	 * document and uses KMeans clustering to cluster documents. Creates folder for
	 * each cluster and add the associated document in the cluster.
	 * 
	 * @param num
	 * @param noOfClusters
	 */
	void cluster(int num, int noOfClusters);

	/**
	 * Deletes files from all the folders associated with the application.
	 */
	void clean();

}
