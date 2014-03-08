package codingame.medium;

import java.util.Scanner;

class DwarvesStandingOnGiantShoulders {

	public static void main(String args[]) {
		Scanner in   = new Scanner(System.in);
		int n        = in.nextInt();	
		int maxIndex = Integer.MIN_VALUE;
		
		int[][] influences = new int[n][2];
		for (int i=0 ; i<influences.length ; i++) {
			for (int j=0 ; j<influences[0].length ; j++)
				maxIndex = Math.max(influences[i][j] = in.nextInt(),maxIndex);
		}
			
		boolean updated;
		int[] longestChain = new int[maxIndex];		
		do {
			updated = false;
			for (int[] inf : influences) {
				int longestFromNext = longestChain[inf[1] - 1];
				if (longestFromNext >= longestChain[inf[0] - 1]) {
					longestChain[inf[0] - 1] = 1 + longestFromNext;
					updated = true;
				}				
			}			
		} while (updated);
		
		int result = 0;
		for (Integer i : longestChain) 
			result = Math.max(result,i);
		System.out.println(result + 1);
		in.close();
	}
}