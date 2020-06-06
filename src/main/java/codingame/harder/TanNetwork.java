package codingame.harder;

import java.util.*;

public class TanNetwork {

	private static class BusStation {
		
		public double longitude, latitude;
        public String id, name;
        public List<BusStation> successors = new ArrayList<>();
        
		public BusStation(String id, String name, double latitude, double longitude) {
			super();
			this.longitude = longitude;
			this.latitude = latitude;
			this.name = name;
			this.id = id;
		}
		
		public double distance(BusStation bs) {
			double x = (this.longitude - bs.longitude)*Math.cos((this.latitude + bs.latitude)/2);
			double y = (this.latitude - bs.latitude);
			return Math.sqrt(x*x + y*y) * 6371;
		}
		
		public static double degToRad(double theta) {
			return Math.PI/180*theta;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
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
			BusStation other = (BusStation) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}
    }
	
	public static class Couple implements Comparable<Couple> {
		public List<BusStation> way;
		public BusStation       pos;
		public double           currentDist;
		public Couple(List<BusStation> way, BusStation pos, double currentDist) {
			this.way         = way;
			this.pos         = pos;
			this.currentDist = currentDist;
		}
		
		@Override
		public int compareTo(Couple c) {
			return Double.compare(  currentDist +   pos.distance(endStation),
		                          c.currentDist + c.pos.distance(endStation));
		}
	}
    
	public static BusStation startStation, endStation;
	
	/**
	 * This solution uses the A* algorithm, however one of the tests fails (not optimal)
	 * and I don't understand why since the compareTo method between two Couple(s) never
	 * over-estimate the real length of the solution...
	 */
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		String idBegin  = in.nextLine();
		String idEnding = in.nextLine();
		
		HashMap<String,BusStation> stations = new HashMap<>();
		
		int n = in.nextInt();
		in.nextLine();
		for (int i=0 ; i<n ; i++) {
			String[] split = in.nextLine().split(",");
			String id = split[0];
			String name = split[1].substring(1,split[1].length() - 1);
			double latitude  = BusStation.degToRad(Double.parseDouble(split[3]));
			double longitude = BusStation.degToRad(Double.parseDouble(split[4]));
			stations.put(id,new BusStation(id,name,latitude,longitude));	
		}
		
		startStation = stations.get(idBegin);
		endStation   = stations.get(idEnding);
		
		int m = in.nextInt();
		in.nextLine();
		for (int i=0 ; i<m ; i++) {
			String[] split = in.nextLine().split(" ");
			stations.get(split[0]).successors.add(stations.get(split[1]));	
		}
		
		List<BusStation> way = shortestWay(idBegin,idEnding,stations);
		if (way == null)
			System.out.println("IMPOSSIBLE");
		else for (BusStation bs : way)
			System.out.println(bs.name);
		in.close();
	}

	private static List<BusStation> shortestWay(String idBegin, String idEnding,
			HashMap<String, BusStation> stations) {
		PriorityQueue<Couple> toVisit = new PriorityQueue<>();
		HashSet<BusStation>   visited = new HashSet<>();
		BusStation            current = stations.get(idBegin);
		List<BusStation>      initWay = new ArrayList<BusStation>();
		Couple                couple  = new Couple(initWay,current,0);
		
		toVisit.add(couple);
		visited.add(current);
		initWay.add(current);
		
		do {
			if (toVisit.isEmpty()) 				
				return null;			
			
			couple                = toVisit.poll();
			current               = couple.pos;	
			List<BusStation> succ = current.successors;
			
			for (BusStation bs : succ)
				if (visited.add(bs)) {
					List<BusStation> way = new ArrayList<>(couple.way);
					way.add(bs);
					toVisit.offer(new Couple(way,bs,couple.currentDist + couple.pos.distance(bs)));
				}	
		} while (!current.id.equals(idEnding));
		
		return couple.way;
	}
}