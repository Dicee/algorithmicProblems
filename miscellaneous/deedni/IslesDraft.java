package miscellaneous.deedni;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.util.Pair;

public class IslesDraft {
	private static int currentTime;
	
	public static void main(String[] args) {
		test("input/isles_input1.txt");
		test("input/isles_input2.txt");
		test("input/isles_input3.txt");
	}

	private static void test(String path) {
		init();
		
		long start = System.currentTimeMillis();
		solve(path);
		System.out.println((System.currentTimeMillis() - start) + " ms");
	}
	
	private static void init() {
		currentTime = 1;
	}

	private static void solve(String path) {
		Scanner sc = new Scanner(Rotation.class.getResourceAsStream(path));
		
		int n = sc.nextInt();
		int q = sc.nextInt();
		sc.nextLine();
		
		Isle[] isles = new Isle[n];
		for (int i=0 ; i<n ; i++) isles[i] = new Isle();
		
		for (int i=0 ; i<q ; i++) {
			String cmd = sc.next();
			Isle from = isles[sc.nextInt() - 1];
			Isle to = isles[sc.nextInt() - 1];
			if (cmd.equals("check")) checkPath(from,to);
			else                     from.buildBridge(to);
			currentTime++;
			if (sc.hasNext()) sc.nextLine();
		}
		sc.close();
	}
	
	private static boolean accept(Map<Isle,Bridge> explored, Pair<Bridge,Isle> pair) {
		Bridge bridge = null;
		Bridge pairBridge = pair.getKey();
		
		if (explored.containsKey(pairBridge)) {
			Bridge current = explored.get(pairBridge);
			bridge = pairBridge.newest(current);
			if (pairBridge == current || bridge.getAge() != pairBridge.getAge()) return false;			
		} else
			bridge = pairBridge;
		
		explored.put(pair.getValue(),bridge);
		return true;
	}
	
	private static void checkPath(Isle from, Isle to) {
		Map<Isle,Bridge> explored = new HashMap<>();
		Set<Pair<Bridge,Isle>> boundary = from.bridges.stream().map(bridge -> new Pair<>(bridge,from)).collect(Collectors.toSet());
		
		Optional<Bridge> minimaxBridge = Optional.empty();
		while (!boundary.isEmpty()) {
			Set<Pair<Bridge,Isle>> newBoundary = new HashSet<>();
			for (Pair<Bridge,Isle> pair : boundary) {
//				System.out.println("bridges = " + pair.getValue().bridges);
				for (Bridge bridge : pair.getValue().bridges) {
					if ((!minimaxBridge.isPresent() || bridge.getAge() <= minimaxBridge.get().getAge()) && accept(explored,pair)) {
//						System.out.println("in if with " + bridge);
						Bridge oldestBridgeOnPath = bridge.oldest(pair.getKey());
//						System.out.println("oldest = " + oldestBridgeOnPath + ", reached = " + to.equals(bridge.to));
						if (to.equals(bridge.to)) minimaxBridge = Optional.of(minimaxBridge.orElse(oldestBridgeOnPath).newest(oldestBridgeOnPath));
						else                      newBoundary.add(new Pair<>(oldestBridgeOnPath,bridge.to));
					}
				}
			}
			if (boundary.equals(newBoundary)) break;
			boundary = newBoundary;
//			printAndWait(explored,boundary,sc);
		}
		minimaxBridge.ifPresent(bridge -> bridge.from.destroyBridge(bridge.to));
		System.out.println(minimaxBridge.isPresent() ? "YES " + minimaxBridge.get().creationDate : "NO");
	}

	private static class Isle {
		private static int currentId = 0;
		public final int id = currentId++;
		private final Set<Bridge> bridges = new HashSet<>();
		
		public void destroyBridge(Isle to) { 
			Bridge bridge = new Bridge(this,to,currentTime);
			bridges.remove(bridge); 
			to.bridges.remove(bridge);
		}
		
		public void buildBridge  (Isle to) { 
			Bridge bridge = new Bridge(this,to,currentTime);
			bridges.add(bridge);
			to.bridges.add(bridge);
		}
		
		@Override
		public String toString() {
			return "Isle [" + (id + 1) + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
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
			Isle other = (Isle) obj;
			if (id != other.id)
				return false;
			return true;
		}		
	}
	
	private static class Bridge /*implements Comparable<Bridge>*/ {
		private static final int UNKNOWN = -1;
		
		public final Isle from;
		public final Isle to;
		public final int creationDate;
		
		public Bridge(Isle from, Isle to, int creationDate) {
			this.from = from;
			this.to = to;
			this.creationDate = creationDate;
		}
		
		public int getAge() { return creationDate == UNKNOWN ? UNKNOWN : currentTime - creationDate; }
		
		public Bridge oldest(Bridge that) { return getAge() >= that.getAge() ? this : that; }
		public Bridge newest(Bridge that) { return getAge() <= that.getAge() ? this : that; }

		@Override
		public String toString() {
			return "Bridge [" + (from.id + 1) + ", " + (to.id + 1) + ", " + getAge() + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((from == null) ? 0 : from.hashCode());
			result = prime * result + ((to == null) ? 0 : to.hashCode());
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
			Bridge other = (Bridge) obj;
			return (from == other.from && to == other.to) || (from == other.to && to == other.from);
		}
	}
}
