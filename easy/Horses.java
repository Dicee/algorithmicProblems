package codingame.easy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

class Horses {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
        int n = Integer.valueOf(in.nextLine().trim());
        
        List<Integer> puissances = new ArrayList<Integer>();
        for (int i = 0; i < n; i++)
            puissances.add(in.nextInt());

        Collections.sort(puissances);

        Iterator<Integer> it = puissances.iterator();
		int pow0 = it.next();
		int pow1 = it.next();
		
		int minDiff = Integer.MAX_VALUE;
		while (it.hasNext()) {
			minDiff = Math.min(minDiff,Math.abs(pow0 - pow1));
			pow0 = pow1;
			pow1 = it.next();
		}
		minDiff = Math.min(minDiff,Math.abs(pow0 - pow1));
		System.out.println(minDiff);
		in.close();
	}
}