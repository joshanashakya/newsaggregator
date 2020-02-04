package edu.lab.newsaggregator;

import java.util.Scanner;

/**
 * @author joshanashakya <Feb 4, 2020>
 */
public class App {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		AppService appService = new AppServiceImpl();
		while (true) {
			System.out.println("Options");
			System.out.println("1. List news urls\n2. List news topics\n3. Cluster news\n4. Clean application");
			System.out.println("Enter your choice: ");
			int ch = sc.nextInt();
			int num = 0;
			if (ch != 4 && ch != 3) {
				System.out.println("Enter the number of news you wish to perform task:");
				num = sc.nextInt();
			}
			switch (ch) {
			case 1:
				appService.listUrls(num);
				break;
			case 2:
				System.out.println("Do you wish to list news topics from existing urls(y/n):");
				boolean flag = "y".equals(sc.next()) ? true : false;
				appService.listTitles(num, flag);
				break;
			case 3:
				System.out.println("Clustering using existing resources.");
				System.out.println("Enter the number of clusters:");
				appService.cluster(num, sc.nextInt());
				break;
			case 4:
				appService.clean();
				break;
			}
		}
	}
}
