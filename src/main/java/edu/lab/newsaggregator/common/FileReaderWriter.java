package edu.lab.newsaggregator.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.lab.newsaggregator.AppProperties;

public class FileReaderWriter {

	private static final AppProperties PROPERTIES = AppProperties.getInstance();

	public static List<String> read(String filePath, boolean isInResource) {
		String path = isInResource ? getPath(filePath) : filePath;
		List<String> lines = new ArrayList<>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			String line;
			while ((line = in.readLine()) != null) {
				lines.add(line);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	public static String download(String url, boolean doSave, String fileName) {
		StringBuilder sb = new StringBuilder();
		String content = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
			content = sb.toString();
			if (doSave) {
				BufferedWriter out = new BufferedWriter(new FileWriter(downloadPath(fileName)));
				out.write(content);
				out.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}

	public static <T> void write(List<T> list, String path) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			for (T l : list) {
				writer.write(String.valueOf(l));
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String read(File file) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static void delete(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				delete(f.getPath());
			}
		} else {
			file.delete();
		}
	}

	private static String getPath(String filePath) {
		return FileReaderWriter.class.getClassLoader().getResource(filePath).getPath().replaceAll("%20", " ");
	}

	private static String downloadPath(String fileName) {
		return String.format("%s/%s", PROPERTIES.get("pages"), fileName);
	}
}
