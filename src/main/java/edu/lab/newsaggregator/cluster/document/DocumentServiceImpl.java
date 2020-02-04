package edu.lab.newsaggregator.cluster.document;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.lab.newsaggregator.common.FileReaderWriter;

public class DocumentServiceImpl implements DocumentService {

	@Override
	public Document create(String id, String text) {
		Document doc = new Document(id, text, new Preprocessor().preprocess(text));
		doc.setTitle(text.split("\n")[0]);
		return doc;
	}

	@Override
	public List<Document> extract(String folderName) {
		File[] files = new File(folderName).listFiles();
		List<Document> docs = new ArrayList<>();
		for (File file : files) {
			docs.add(create(file.getName(), FileReaderWriter.read(file)));
		}
		return docs;
	}

	@Override
	public List<Document> calWeights(List<Document> docs, List<String> vocab) {
		for (Document doc : docs) {
			List<Double> weights = calTfs(doc, vocab);
			doc.setWeights(weights);
		}
		return docs;
	}

	/**
	 * Calculates term frequency.
	 * 
	 * @param doc
	 * @param vocab
	 * @return list of frequency of each vocabulary term in document
	 */
	private List<Double> calTfs(Document doc, List<String> vocab) {
		List<Double> tfs = new ArrayList<>();
		String text = doc.getPreprocessedText();
		for (String word : vocab) {
			int splitCount = text.split(String.format("\\b%s\\b", word)).length;
			double count = text.endsWith(word) ? splitCount : splitCount - 1;
			tfs.add(count);
		}
		return tfs;
	}
}
