package edu.lab.newsaggregator;

public interface AppService {

	void listUrls(int num);

	void listTitles(int num, boolean flag);

	void cluster(int num, int noOfClusters);

	void clean();

}
