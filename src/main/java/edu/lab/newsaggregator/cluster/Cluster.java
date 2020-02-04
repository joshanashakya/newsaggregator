package edu.lab.newsaggregator.cluster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.lab.newsaggregator.cluster.document.Document;

public class Cluster implements Cloneable {

	private String id;
	private List<Document> docs = new ArrayList<>();
	private List<Double> centroid;

	public Cluster(String id, List<Double> centroid) {
		this.id = id;
		this.centroid = centroid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Document> getDocs() {
		return docs;
	}

	public void setDocs(List<Document> docs) {
		this.docs = docs;
	}

	public List<Double> getCentroid() {
		return centroid;
	}

	public void setCentroid(List<Double> centroid) {
		this.centroid = centroid;
	}

	public Cluster clone() throws CloneNotSupportedException {
		Cluster clonedObj = (Cluster) super.clone();
		clonedObj.docs = new ArrayList<>();
		Iterator<Document> iterator = this.docs.iterator();
		while (iterator.hasNext()) {
			clonedObj.docs.add((Document) iterator.next().clone());
		}
		return clonedObj;
	}

	@Override
	public String toString() {
		return String.format("Cluster Name: %s\nNumber of documents: %d", id, docs.size());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Cluster))
			return false;
		Cluster cluster = (Cluster) obj;
		return cluster.getId().equals(id) && (cluster.getDocs().equals(docs) && cluster.getCentroid().equals(centroid));
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + id.hashCode();
		result = 31 * result + docs.hashCode();
		result = 31 * result + centroid.hashCode();
		return result;
	}
}
