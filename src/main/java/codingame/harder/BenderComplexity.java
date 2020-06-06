package codingame.harder;

import java.util.Arrays;
import java.util.Scanner;

class BenderComplexity {

	public static double computeComplexity(int family, double x) {
		switch (family) {
			case 0  : return 1;
			case 1  : return Math.log(x);
			case 2  : return x;
			case 3  : return x*Math.log(x);
			case 4  : return x*x;
			case 5  : return x*x*Math.log(x);
			case 6  : return Math.pow(x,3);
			default : return Math.pow(2,x);
		}
	}
	
	public static void main(String args[]) {
		Scanner    in       = new Scanner(System.in);
		int        n        = in.nextInt();
		double[]   kmed     = new double[8];
		double[][] measures = new double[2][n];
		double[][] kEstim   = new double[8][n];
		
		for (int i=0; i<n; i++) {
			double x       = in.nextDouble();
			double time    = in.nextDouble();
			measures[0][i] = x;
			measures[1][i] = time;
			for (int j=0 ; j<8 ; j++) 
				kEstim[j][i]    = time/computeComplexity(j,x);
		}
		in.close();
		
		//We use the median value estimated proportional constant k instead of
		//the mean value in order to not let a few bad measures ruin the estimation
		for (int i=0 ; i<8 ; i++) {
			double[] values = kEstim[i];
			Arrays.sort(values);
			kmed[i] = n % 2 == 0 ? values[n/2]/2 + values[n/2 - 1]/2  : 						 
			  	   				   values[(n - 1)/2];
		}
		
		//We determine the more probable complexity in the least squares sense
		double errorMin = Double.MAX_VALUE;
		int    indexMin = 0;
		for (int i=0 ; i<8 ; i++) {
			double quadraticError = 0;
			double k              = kmed[i];
			for (int j=0 ; j<n ; j++) {
				double estimatedValue = k*computeComplexity(i,measures[0][j]);
				quadraticError       += Math.pow(measures[1][j] - estimatedValue,2);
			}
			if (quadraticError < errorMin) {
				indexMin = i;
				errorMin = quadraticError;
			}
		}
		
		switch (indexMin) {
			case 0  : System.out.println("O(1)");         break;
			case 1  : System.out.println("O(log n)");     break;
			case 2  : System.out.println("O(n)");         break;
			case 3  : System.out.println("O(n log n)");   break;
			case 4  : System.out.println("O(n^2)");       break;
			case 5  : System.out.println("O(n^2 log n)"); break;
			case 6  : System.out.println("O(n^3)");       break;
			default : System.out.println("O(2^n)");       break;
		}
	}
}
