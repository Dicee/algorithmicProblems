package miscellaneous.deedni;

import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

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
		Set<Pair<Bridge,Isle>> boundary = from.bridges.stream().map(bridge -> Pair.of(bridge,from)).collect(Collectors.toSet());
		explored.add(from);

		Optional<Bridge> minimaxBridge = Optional.empty();
		while (!boundary.isEmpty()) {
			Set<Pair<Bridge,Isle>> newBoundary = new HashSet<>();
			for (Pair<Bridge,Isle> pair : boundary) {
				for (Bridge bridge : pair.getValue().bridges) {
					if ((!minimaxBridge.isPresent() || bridge.getAge() <= minimaxBridge.get().getAge()) && explored.add(bridge.to)) {
						Bridge oldestBridgeOnPath = bridge.oldest(pair.getKey());
						if (to.equals(bridge.to)) minimaxBridge = Optional.of(minimaxBridge.orElse(oldestBridgeOnPath).newest(oldestBridgeOnPath));
						else                      newBoundary.add(Pair.of(oldestBridgeOnPath,bridge.to));
					}
				}
			}
			boundary = newBoundary;
		}
		minimaxBridge.ifPresent(bridge -> bridge.from.destroyBridge(bridge.to));
		System.out.println(minimaxBridge.isPresent() ? "YES " + minimaxBridge.get().getAge() : "NO");
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

	@Value(staticConstructor = "of")
	static class Pair<K, V> {
		@NonNull private final K key;
		@NonNull private final V value;
	}
}
