package edu.lab.newsaggregator.cluster.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Joshana Shakya
 *
 */
public class VocabBuilder {

	public List<String> build(List<Document> docs) {
		Set<String> vocabs = new HashSet<>();
		for (Document doc : docs) {
			vocabs.addAll(Arrays.asList(doc.getPreprocessedText().split(" ")));
		}
		List<String> sortedVocab = new ArrayList<>(vocabs);
		Collections.sort(sortedVocab);
		return sortedVocab;
	}
}
