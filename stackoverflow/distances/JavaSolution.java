package stackoverflow.distances;

import java.util.HashSet;
import java.util.Set;

/**
 * Answer to http://stackoverflow.com/questions/39049189/an-efficient-way-to-get-and-store-the-shortest-paths/39049397#39049397
 */
public class JavaSolution {
    public enum Cell { FREE, BLOCKED }

    // assuming cells is a rectangular array with non-empty columns
    public static int[][] distances(Cell[][] cells, ArrayCoordinate startingPoint) {
        int[][] distances = new int[cells.length][cells[0].length];
        // -1 will mean that the cell is unreachable from the startingPoint
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                distances[i][j] = -1;
            }
        }
        distances[startingPoint.i][startingPoint.j] = 0;

        Set<ArrayCoordinate> border = startingPoint.validNeighbours(cells);
        for (int currentDistance = 1; !border.isEmpty(); currentDistance++) {
            Set<ArrayCoordinate> newBorder = new HashSet<>();
            for (ArrayCoordinate coord : border) {
                distances[coord.i][coord.j] = currentDistance;

                for (ArrayCoordinate neighbour : coord.validNeighbours(cells)) {
                    if (distances[neighbour.i][neighbour.j] < 0) {
                        newBorder.add(neighbour);
                    }
                }
            }
            border = newBorder;
        }

        return distances;
    }

    private static class ArrayCoordinate {
        public ArrayCoordinate(int i, int j) {
            if (i < 0 || j < 0) throw new IllegalArgumentException("Array coordinates must be positive");
            this.i = i;
            this.j = j;
        }

        public final int i, j;

        public Set<ArrayCoordinate> validNeighbours(Cell[][] cells) {
            Set<ArrayCoordinate> neighbours = new HashSet<>();

            // inlining for not doing extra work in a loop iterating over (-1, 1) x (-1, 1). If diagonals are allowed
            // then switch for using a loop
            addIfValid(cells, neighbours,  1,  0);
            addIfValid(cells, neighbours, -1,  0);
            addIfValid(cells, neighbours,  0,  1);
            addIfValid(cells, neighbours,  0, -1);

            return neighbours;
        }

        private void addIfValid(Cell[][] cells, Set<ArrayCoordinate> neighbours, int dx, int dy) {
            int x = i + dx, y = j + dy;
            if (0 <= x && 0 <= y && x < cells.length && y < cells[0].length && cells[x][y] == Cell.FREE) {
                neighbours.add(new ArrayCoordinate(i + dx, j + dy));
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ArrayCoordinate point = (ArrayCoordinate) o;

            if (i != point.i) return false;
            if (j != point.j) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = i;
            result = 31 * result + j;
            return result;
        }
    }

    public static void main(String[] args) {
        int n = 11, m = 5;

        Cell[][] cells = new Cell[n][m];
        cells[1][1] = Cell.BLOCKED;
        cells[1][2] = Cell.BLOCKED;
        cells[2][1] = Cell.BLOCKED;

        ArrayCoordinate startingPoint = new ArrayCoordinate(5, 2);

        System.out.println("Initial matrix:");
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (cells[i][j] == null) {
                    cells[i][j] = Cell.FREE;
                }
                if (startingPoint.i == i && startingPoint.j == j) {
                    System.out.print("S ");
                } else {
                    System.out.print(cells[i][j] == Cell.FREE ? ". " : "X ");
                }
            }
            System.out.println();
        }

        int[][] distances = distances(cells, startingPoint);
        System.out.println("\nDistances from starting point:");
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < distances[0].length; j++) {
                System.out.print((distances[i][j] < 0 ? "X" : distances[i][j]) + " ");
            }
            System.out.println();
        }
    }
}

