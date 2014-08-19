package codingame.medium;
import java.util.Arrays;
import java.util.Scanner;

class NetworkCabling {
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		int n      = in.nextInt();
		int[] ord  = new int[n];
		int maxX   = 0;
		int minX   = 0;
		
		for (int i = 0; i < n; i++) {
			int x  =  in.nextInt();
			int y  =  in.nextInt();
			ord[i] = y;
			if (x > maxX || i == 0)
				maxX = x;
			if (x < minX || i == 0) 
				minX = x;
		} 
		Arrays.sort(ord);
		long result = Long.MAX_VALUE;
		int ymed    = n % 2 == 0 ? ord[n/2]/2 + ord[n/2 - 1]/2  : 						 
							  	   ord[(n - 1)/2];
		result      = maxX - minX;
		for (Integer y : ord) 
			result += Math.abs(y - ymed);
		System.out.println(result);
		in.close();
	}
}