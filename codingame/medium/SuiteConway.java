package codingame.medium;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class SuiteConway {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);		
		int r    = in.nextInt();
		int l    = in.nextInt();
		
		long deb = System.currentTimeMillis();
		List<Integer> suite = new ArrayList<>();
		suite.add(r);
		for (int i=1 ; i<l ; i++) {
			int size = suite.size();
			int k = 0;
			List<Integer> suiteNextStep = new ArrayList<>();
			while (k < size) {
				int val   = suite.get(k);
				int count = 0;
				while (count + k < size && suite.get(count + k) == val) 
					count++;
				suiteNextStep.add(count);
				suiteNextStep.add(val);
				k += count;
			}
			suite = suiteNextStep;
		}
		System.err.println("Computed : " + (System.currentTimeMillis() - deb));
		
		//The StringBuilder class is way faster than String for that kind of operation
		StringBuilder sb = new StringBuilder();
		int k            = 0;
		int size         = suite.size();
		for (Integer i : suite)
			sb.append(i + (++k < size ? " " : ""));
		
		System.out.println(sb);
		in.close();
		System.err.println("Finished : " + (System.currentTimeMillis() - deb));
	}
}