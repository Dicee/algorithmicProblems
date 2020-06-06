package codingame.harder;

import java.util.Scanner;

class GenomeSequencing {
	
	private static int longerMatch(String left, String right) {
		int l = left.length();
		for (int k=0 ; k<l ; k++)
			if (right.startsWith(left.substring(k)))
				return l - k;
		return 0;
	}
	
	private static String assemble(String left, String right, int indexRight) {
		return left + right.substring(indexRight);
	}
	
	private static void swap(String[] seq, int i, int j) {
	    String tmp = seq[i];
		seq[i]     = seq[j];
		seq[j]     = tmp;
	}
	
	public static void main(String args[]) {
		Scanner in   = new Scanner(System.in);
		int     n    = in.nextInt();
		String[] seq = new String[n];
		in.nextLine();
		
		for (int i=0 ; i<n ; i++) 
			seq[i] = in.nextLine();
		in.close();
		
		String result = seq[0];
		
		
		for (int i=1 ; i<n ; i++) {
			int longerMatchLeft  =  0;
			int longerMatchRight =  0;
			int longerMatchIn    =  0;
			
			int indexMatchLeft   = -1;
			int indexMatchRight  = -1;
			int indexMatchIn     = -1;
			
			for (int j=i ; j<n ; j++) {
				int matchLeft  = longerMatch(seq[j],result);
				int matchRight = longerMatch(result,seq[j]);
				
				if (matchLeft > longerMatchLeft) {
					indexMatchLeft  = j;
					longerMatchLeft = matchLeft;
				}
				
				if (matchRight > longerMatchRight) {
					indexMatchRight  = j;
					longerMatchRight = matchRight;
				}	
				
				int l = seq[j].length();
				if (l <= result.length()) {
				    if (result.contains(seq[j]) && l > longerMatchIn) {
				        indexMatchIn = j;
				        longerMatchIn = l;
				    }
				} else if (seq[j].contains(result) && l > longerMatchIn) {
				        indexMatchIn = j;
				        longerMatchIn = l;
				}
			}
			
			if (indexMatchIn != -1 && longerMatchIn > Math.max(longerMatchRight,longerMatchLeft)) {
			    result = seq[indexMatchIn].length() > result.length() ? seq[indexMatchIn] : result;
			    swap(seq,i,indexMatchIn);
			} else if (longerMatchRight > longerMatchLeft) {
				result = assemble(result,seq[indexMatchRight],longerMatchRight);
				swap(seq,i,indexMatchRight);
			} else {
				/*In the last case, if no match has been found, we take the first one still available.
				 *I can't help thinking it's not a good idea in certain situations, although I get a 100%
				 *score with the CodinGame tests. In fact, we probably should use the sequence (from the
				 *best side) that matches the best with the available others in order to maximize the matching 
				 *length in the next iteration.
				 */
			    if (indexMatchLeft == -1) indexMatchLeft = i;
				result = assemble(seq[indexMatchLeft],result,longerMatchLeft);
				swap(seq,i,indexMatchLeft);
			}
		}
		System.out.println(result.length() + "\n");
		System.err.println(result + "\n");
	}
}