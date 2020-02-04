package edu.lab.newsaggregator.cluster.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.lab.newsaggregator.common.FileReaderWriter;

/**
 * @author Joshana Shakya
 *
 */
public class Preprocessor {

	public String preprocess(String text) {
		String[] tokens = tokenize(text);
		String[] cleaned = removeStopWords(tokens);
		int len = cleaned.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			if (cleaned[i].length() == 1)
				continue;
			sb.append(cleaned[i]);
			sb.append(" ");
		}
		return sb.toString();
	}

	public String[] tokenize(String str) {
		// \\p{P} remove punctuation marks and \\p{N} removes devanagari numbers
		str = str.replaceAll("[\\p{P}\\p{N}\\w=]", "");
		// remove multiple space characters
		str = str.replaceAll("\\s+", " ");
		return str.trim().split("\\s");
	}

	public String[] removeStopWords(String[] tokens) {
		List<String> stopWords = FileReaderWriter.read("nepali_stopwords.txt", true);
		List<String> words = new ArrayList<>(Arrays.asList(tokens));
		words.removeAll(stopWords);
		String[] cleaned = new String[words.size()];
		words.toArray(cleaned);
		return cleaned;
	}
}
