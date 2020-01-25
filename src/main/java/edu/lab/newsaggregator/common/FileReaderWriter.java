package edu.lab.newsaggregator.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileReaderWriter {

	public static List<String> read(String fileName) {
		String path = getPath(fileName);
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

	public static String download(String url, boolean doSave) {
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
				BufferedWriter out = new BufferedWriter(new FileWriter(downloadPath(url)));
				out.write(content);
				out.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}

	private static String getPath(String fileName) {
		return FileReaderWriter.class.getClassLoader().getResource(fileName).getPath().replaceAll("%20", " ");
	}

	private static String downloadPath(String fileName) {
		return String.format("%s/download-%s", "downloads", fileName);
	}
}
