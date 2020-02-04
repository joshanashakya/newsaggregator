package edu.lab.newsaggregator.cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.lab.newsaggregator.cluster.document.Document;

/**
 * @author Joshana Shakya
 */
public class KMeans {

	private static final Logger LOGGER = Logger.getLogger(KMeans.class.getName());

	/**
	 * Initializes 'k' clusters by selecting first 'k' documents as centroids. Makes
	 * deep copy of clusters. For each document, finds the nearest cluster of the
	 * document, removes the document from all clusters, and assigns this document
	 * to the chosen cluster. Calculates centroid of each of the clusters. Compares
	 * the newly updated clusters with the old copy of clusters for equality. If the
	 * two copies are same, the updated clusters is the final clusters.
	 * 
	 * @param docs documents to be clustered
	 * @param k    number of clusters
	 * @return clusters
	 */
	public List<Cluster> cluster(List<Document> docs, int k) {
		List<Cluster> clusters = initialize(docs, k);
		int count = 1;
		while (true) {
			LOGGER.log(Level.INFO, "Iteration: " + count);
			List<Cluster> prevClusters = copyCluster(clusters);

			for (Document doc : docs) {
				Cluster cluster = findCluster(doc, clusters);
				removeDoc(doc, clusters);
				cluster.getDocs().add(doc);
			}
			for (Cluster cluster : clusters) {
				List<Double> centroid = calCentroid(cluster);
				cluster.setCentroid(centroid);
			}
			if (conditionMet(clusters, prevClusters)) {
				break;
			}
			count++;
		}
		LOGGER.log(Level.INFO, "Total number of iterations: " + (count - 1));
		return clusters;
	}

	/**
	 * Makes deep copy of the collection of cluster.
	 * 
	 * @param clusters
	 * @return copy of clusters
	 */
	private List<Cluster> copyCluster(List<Cluster> clusters) {
		List<Cluster> copy = new ArrayList<>();
		for (Cluster cluster : clusters) {
			try {
				copy.add(cluster.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		return copy;
	}

	/**
	 * Removes document from the clusters
	 * 
	 * @param doc
	 * @param clusters
	 */
	private void removeDoc(Document doc, List<Cluster> clusters) {
		for (Cluster cluster : clusters) {
			if (cluster.getDocs().contains(doc))
				cluster.getDocs().remove(doc);
		}
	}

	/**
	 * Check if two collection of cluster are same
	 * 
	 * @param clusters
	 * @param prevClusters
	 * @return true if the two collection of cluster are same, false otherwise
	 */
	private boolean conditionMet(List<Cluster> clusters, List<Cluster> prevClusters) {
		boolean flag = true;
		for (int i = 0; i < clusters.size(); i++) {
			flag = flag && clusters.get(i).equals(prevClusters.get(i));
		}
		LOGGER.log(Level.INFO, "Clusters equality:" + flag);
		return flag;
	}

	/**
	 * doc1 = <1, 1, 2> and doc2 = <2, 3, 4> then centroid = <(1+1)/2, (1+3)/2,
	 * (2+4)/2>
	 * 
	 * @param cluster
	 * @return centroid of the cluster
	 */
	private List<Double> calCentroid(Cluster cluster) {
		List<Document> docs = cluster.getDocs();
		List<Double> temp = new ArrayList<>();
		int size = docs.size();
		for (Document doc : docs) {
			List<Double> weights = doc.getWeights();
			if (temp.isEmpty())
				temp.addAll(weights);
			else {
				for (int i = 0; i < weights.size(); i++) {
					temp.set(i, temp.get(i) + weights.get(i));
				}
			}
		}
		List<Double> centroid = new ArrayList<>();
		for (Double t : temp) {
			centroid.add(t / size);
		}
		return centroid;
	}

	private List<Cluster> initialize(List<Document> docs, int num) {
		LOGGER.log(Level.INFO, "Initializing cluster.");
		List<Cluster> clusters = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			String id = String.format("cluster%d", i + 1);
			List<Double> centroid = docs.get(i).getWeights();
			clusters.add(new Cluster(id, centroid));
		}
		for (Document doc : docs) {
			Cluster cluster = findCluster(doc, clusters);
			cluster.getDocs().add(doc);
		}
		return clusters;
	}

	/**
	 * Finds the nearest cluster of the document.
	 * 
	 * @param doc
	 * @param clusters
	 * @return the nearest cluster
	 */
	private Cluster findCluster(Document doc, List<Cluster> clusters) {
		double maxSimilarity = 0.0;
		Cluster bestCluster = clusters.get(0);
		for (Cluster cluster : clusters) {
			double similarity = similarity(doc.getWeights(), cluster.getCentroid());
			if (similarity > maxSimilarity) {
				maxSimilarity = similarity;
				bestCluster = cluster;
			}
		}
		return bestCluster;
	}

	/**
	 * Calculates cosine similarity of weights in the form of two vectors.
	 * 
	 * @param weight1
	 * @param weight2
	 * @return cosine similarity value
	 */
	private double similarity(List<Double> weight1, List<Double> weight2) {
		int size = weight1.size();
		// compute numerator
		double dotProd = 0;
		double sqWeight1 = 0;
		double sqWeight2 = 0;
		for (int i = 0; i < size; i++) {
			double wt1 = weight1.get(i);
			double wt2 = weight2.get(i);
			dotProd = dotProd + (wt1 * wt2);
			sqWeight1 = sqWeight1 + (wt1 * wt1);
			sqWeight2 = sqWeight2 + (wt2 * wt2);
		}
		// compute denominator
		double normWt1 = Math.sqrt(sqWeight1);
		double normWt2 = Math.sqrt(sqWeight2);
		return dotProd / (normWt1 * normWt2);
	}
}
