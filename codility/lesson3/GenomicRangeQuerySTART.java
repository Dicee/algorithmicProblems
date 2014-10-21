package lesson3;

import java.util.*;

/**
 * Level : respectable
 */
public class GenomicRangeQuerySTART {
	public int[] solution(String S, int[] P, int[] Q) {
        Map<Character,Integer> impactFactors = new HashMap<>();
        impactFactors.put('A',1);
        impactFactors.put('C',2);
        impactFactors.put('G',3);
        impactFactors.put('T',4);
        
        int[] minPrefixes = new int[S.length()];
        minPrefixes[0] = impactFactors.get(S.charAt(0));
        for (int i=1 ; i<S.length() ; i++) 
            minPrefixes[i] = Math.min(minPrefixes[i - 1],impactFactors.get(S.charAt(i)));
        
        
        System.out.println(Arrays.toString(minPrefixes));
        return P;
    }
}
