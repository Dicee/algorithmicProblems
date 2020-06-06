package miscellaneous.deedni;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.Value;
import miscellaneous.deedni.Isles.Pair;

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
		Set<Pair<Bridge,Isle>> boundary = from.bridges.stream().map(bridge -> Pair.of(bridge,from)).collect(Collectors.toSet());

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
						else                      newBoundary.add(Pair.of(oldestBridgeOnPath,bridge.to));
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

	@Value
	private static class Isle {
		private static int currentId = 0;
		public final int id = currentId++;

		@EqualsAndHashCode.Exclude
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
	}

	@Value
	private static class Bridge /*implements Comparable<Bridge>*/ {
		private static final int UNKNOWN = -1;

		public final Isle from;
		public final Isle to;
		public final int creationDate;

		public int getAge() { return creationDate == UNKNOWN ? UNKNOWN : currentTime - creationDate; }

		public Bridge oldest(Bridge that) { return getAge() >= that.getAge() ? this : that; }
		public Bridge newest(Bridge that) { return getAge() <= that.getAge() ? this : that; }
	}
}
