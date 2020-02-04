package edu.lab.newsaggregator.cluster.document;

import java.util.List;

public interface DocumentService {

	Document create(String id, String text);

	List<Document> extract(String folderName);

	List<Document> calWeights(List<Document> docs, List<String> vocab);
}
