package edu.lab.newsaggregator.cluster.document;

import java.util.List;

/**
 * @author joshanashakya <Feb 4, 2020>
 */
public interface DocumentService {

	/**
	 * Creates document from id and text. The document title is set by reading first
	 * line of the text.
	 * 
	 * @param id
	 * @param text
	 * @return document
	 */
	Document create(String id, String text);

	/**
	 * Reads the folder and creates documents
	 * 
	 * @param folderName
	 * @return collection of document
	 */
	List<Document> extract(String folderName);

	/**
	 * Calculates term frequency of each word in the document.
	 * 
	 * @param docs
	 * @param vocab
	 * @return collection of document with weights assigned
	 */
	List<Document> calWeights(List<Document> docs, List<String> vocab);
}
