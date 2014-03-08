package codingame.harder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

class RollerCoaster {

	private static class Tour {
		private List<Integer> indexes;
		public long earned;
		
		public Tour(long earned, List<Integer> indexes) {
			this.indexes = indexes;
			this.earned  = earned;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((indexes == null) ? 0 : indexes.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Tour other = (Tour) obj;
			if (indexes == null) {
				if (other.indexes != null)
					return false;
			} else if (!indexes.equals(other.indexes))
				return false;
			return true;
		}
	}
	
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		int L = in.nextInt();
		int C = in.nextInt();
		int N = in.nextInt();
		
		 int[] groups = new int[N];
		for (int i=0; i<N; i++) 
			groups[i] = in.nextInt();
		in.close();
		
		int tour                = 0;
		long earned             = 0;
		HashSet<Tour> tours     = new HashSet<>();
		List<Tour> orderedTours = new ArrayList<>();
		
		while (C > 0) {
			int placesLeft        = L;
			int count             = 0;
			long currentEarned    = 0;
			List<Integer> indexes = new ArrayList<>();
			while (groups[tour] <= placesLeft && count < N) {
				indexes.add(tour);
				placesLeft     -= groups[tour];
				currentEarned  += groups[tour];
				tour            = (tour + 1) % N;
				count++;
			}
			Tour currentTour = new Tour(currentEarned,indexes);
			if (tours.add(currentTour)) {
				orderedTours.add(currentTour);
				earned += currentEarned;
				C--;	
			} else {
				int index       = orderedTours.indexOf(currentTour);
				int size        = orderedTours.size();
				int cycleLength = size - index;
				int repeat      = cycleLength != 0 ? C / cycleLength : C;
				int rest        = cycleLength != 0 ? C % cycleLength : 0;
				for (int i=index ; i<size ; i++) {
					earned += (repeat + (rest > 0 ? 1 : 0))*orderedTours.get(i).earned;
					rest--;
				}
				C = 0;
			}
		}
		System.out.println(earned);		
	}
}