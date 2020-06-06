package unknownSource;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Pb2 {
	public int solution(int x, int[] arr) {
		int countEqual = 0;
		for (int elt : arr) 
			if (elt == x) countEqual++;
		
		int countDiff = 0;
		int i = arr.length - 1;
		for ( ; countEqual != countDiff && i >= 0 ; i--) {
			if      (arr[i] != x) countDiff ++;
			else if (arr[i] == x) countEqual--;
			if (countEqual == countDiff) return i;
		}
		return i;
	}

	public static void main(String[] args) {
		Pb2 sol = new Pb2();
		assertThat(sol.solution(5, new int[] { 5,5,1,7,2,3,5 }), is(4));
	}
}
