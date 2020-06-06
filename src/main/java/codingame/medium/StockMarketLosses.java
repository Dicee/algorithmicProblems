package codingame.medium;

import java.util.Scanner;

public class StockMarketLosses {
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		int n      = in.nextInt();
		int first  = in.nextInt();
		int min    = first;
		int pertes = 0;
		for (int i=1 ; i<n ; i++) {
			int v = in.nextInt();
			min   = Math.min(min,v);
			if (v > first) {
				pertes = Math.min(pertes,min - first);
				first = v;
				min   = v;
			}	
		}
		pertes = Math.min(pertes,min - first);
		System.out.println(pertes);
		in.close();
	}
}