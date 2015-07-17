package miscellaneous.deedni;

import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.util.Pair;

public class Isles {
	private static int currentTime = 0;
	
	public static void main(String[] args) {
		test("input/isles_input1.txt");
		test("input/isles_input2.txt");
		test("input/isles_input3.txt");
	}

	private static void test(String path) {
		long start = System.currentTimeMillis();
		solve(path);
		System.out.println((System.currentTimeMillis() - start) + " ms");
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
	
	private static void checkPath(Isle from, Isle to) {
		Set<Isle> explored = new HashSet<>();
		Set<Pair<Bridge,Isle>> boundary = from.bridges.stream().map(bridge -> new Pair<>(bridge,from)).collect(Collectors.toSet());
		explored.add(from);
		
		Optional<Bridge> minimaxBridge = Optional.empty();
		while (!boundary.isEmpty()) {
			Set<Pair<Bridge,Isle>> newBoundary = new HashSet<>();
			for (Pair<Bridge,Isle> pair : boundary) {
				for (Bridge bridge : pair.getValue().bridges) {
					if ((!minimaxBridge.isPresent() || bridge.getAge() <= minimaxBridge.get().getAge()) && explored.add(bridge.to)) {
						Bridge oldestBridgeOnPath = bridge.oldest(pair.getKey());
						if (to.equals(bridge.to)) minimaxBridge = Optional.of(minimaxBridge.orElse(oldestBridgeOnPath).newest(oldestBridgeOnPath));
						else                      newBoundary.add(new Pair<>(oldestBridgeOnPath,bridge.to));
					}
				}
			}
			boundary = newBoundary;
		}
		minimaxBridge.ifPresent(bridge -> bridge.from.destroyBridge(bridge.to));
		System.out.println(minimaxBridge.isPresent() ? "YES " + minimaxBridge.get().getAge() : "NO");
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
			return "Isle [id=" + id + "]";
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
			return "Bridge [from=" + from + ", to=" + to + ", creationDate=" + creationDate + "]";
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
			if (from == null) {
				if (other.from != null)
					return false;
			} else if (!from.equals(other.from))
				return false;
			if (to == null) {
				if (other.to != null)
					return false;
			} else if (!to.equals(other.to))
				return false;
			return true;
		}
	}
}
