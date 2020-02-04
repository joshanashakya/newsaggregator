package edu.lab.newsaggregator.cluster.document;

import java.util.List;

public class Document implements Cloneable{

	private String id;
	private String title;
	private String text;
	private String preprocessedText;
	private List<Double> weights;

	public Document(String id, String text, String preprocessedText) {
		this.id = id;
		this.text = text;
		this.preprocessedText = preprocessedText;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPreprocessedText() {
		return preprocessedText;
	}

	public void setPreprocessedText(String preprocessedText) {
		this.preprocessedText = preprocessedText;
	}

	public List<Double> getWeights() {
		return weights;
	}

	public void setWeights(List<Double> weights) {
		this.weights = weights;
	}
	
	public Document clone() throws CloneNotSupportedException {
		return (Document) super.clone();
	}
	
	@Override
	public String toString() {
		return String.format("%s (%s)", title, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Document))
			return false;

		Document doc = (Document) obj;
		return doc.getId().equals(id) && doc.getText().equals(text)
				&& doc.getPreprocessedText().equals(preprocessedText) && doc.getWeights().equals(weights);
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + id.hashCode();
		result = 31 * result + text.hashCode();
		result = 31 * result + preprocessedText.hashCode();
		result = 31 * result + weights.hashCode();
		return result;
	}
}
