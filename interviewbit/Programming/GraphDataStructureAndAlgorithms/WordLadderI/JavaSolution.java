package interviewbit.Programming.GraphDataStructureAndAlgorithms.WordLadderI;

import java.util.*;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

// Difficulty: easy, classic A*. Annoyed that Interviewbit didn't have Scala available for this problem. DFS would have been
//             a better choice here in terms of simplicity of implementation.

// https://www.interviewbit.com/problems/word-ladder-i/
public class JavaSolution {
    public static void main(String[] args) {
        System.out.println(ladderLength("hit", "cog", Arrays.asList("hot","dot","dog","lot","log"))); // 5
        System.out.println(ladderLength("ababbabb", "aababaab" , Arrays.asList(
                "aabbaaba", "aababaaa", "baabbaaa", "baabbbab", "bbbabbaa", "bbabbaba", "abbaabaa", "aabbbabb", "abababbb", "abaaabba", "bbbaaabb", "abbaaaab", "abababab", "abbbabab", "abaaaabb", "aaaaabaa",
                "baaaabaa", "bbabbabb", "ababaaab", "aabaabab", "babbbaba", "bbbaaabb", "babaaabb", "aabaaaab", "bbaabbaa", "aaababaa", "bbbbabab", "aaaababa", "bbbbbaba", "abaabaab",
                "baaababb", "bbabbaaa", "abbbbbab", "bbbbbabb", "abaaabaa", "babbaabb", "babaabab", "aabbbbba", "baabaabb"))); // 0
    }

    public static int ladderLength(String start, String end, List<String> dictionary) {
        Map<String, Node> nodes = buildGraph(start, end, dictionary);

        Path initial = new Path(0, nodes.get(start));
        PriorityQueue<Path> candidates = new PriorityQueue<>();
        Map<Node, Integer> optimalPath = new HashMap<>(); // stores the minimum distance found so far between the start and a given node

        candidates.add(initial);
        optimalPath.put(initial.node, 0);

        while (!candidates.isEmpty()) {
            Path path = candidates.remove();
            if (path.node.equals(nodes.get(end))) return path.distance + 1; // total length of the path, not just the number of transitions

            for (Node neighbour : path.node.neighbours) {
                Path next = new Path(path.distance + 1, neighbour);
                Integer dist = optimalPath.get(neighbour);
                if (dist == null || next.distance < dist) {
                    optimalPath.put(neighbour, next.distance);
                    candidates.add(next);
                }
            }
        }
        return 0;
    }

    private static Map<String, Node> buildGraph(String start, String end, List<String> dictionary) {
        int n = dictionary.size() + 2;

        List<String> words = new ArrayList<>(n);
        words.add(start);
        words.addAll(dictionary);
        words.add(end);

        Map<String, Node> nodes = words.stream()
                .distinct()
                .collect(toMap(identity(), word -> new Node(minDistance(word, end))));

        // iterate over the upper triangle of the cartesian product while being careful not to
        // create an illegal links (e.g. cannot link a word in the dictionary to the start word
        // unless it belongs to the dictionary, whereas the opposite is always valid)
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (minDistance(words.get(i), words.get(j)) == 1) {
                    nodes.get(words.get(i)).link(nodes.get(words.get(j)));
                    if (i > 0 && j < n - 1 ) nodes.get(words.get(j)).link(nodes.get(words.get(i)));
                }
            }
        }

        return nodes;
    }

    private static int minDistance(String w1, String w2) {
        int count = 0;
        for (int i = 0; i < w1.length(); i++) {
            if (w1.charAt(i) != w2.charAt(i)) count++;
        }
        return count;
    }

    private static class Path implements Comparable<Path> {
        private final int distance;
        private final Node node;
        private final int totalDistance;

        public Path(int distance, Node node) {
            this.distance = distance;
            this.node = node;
            this.totalDistance = distance + node.minDistanceToEnd;
        }

        @Override
        public int compareTo(Path path) {
            return Integer.compare(totalDistance, path.totalDistance);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Path path = (Path) o;
            return distance == path.distance && Objects.equals(node, path.node);
        }

        @Override
        public int hashCode() {
            return Objects.hash(distance, node);
        }
    }

    private static class Node {
        private final int minDistanceToEnd;
        private final Set<Node> neighbours = new HashSet<>();

        public Node(int minDistanceToEnd) {
            this.minDistanceToEnd = minDistanceToEnd;
        }

        public void link(Node node) {
            neighbours.add(node);
        }
    }
}
