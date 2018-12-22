package interviewbit.Programming.Arrays.RotateMatrix;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// Difficulty: easy, but it seems like I overcomplicated it. The editorial solution looks a lot simpler although it's basically doing the same
//             i.e. rotating groups of 4 elements. Also, I hate Interviewbit for not always supporting Scala.

// https://www.interviewbit.com/problems/rotate-matrix/
public class JavaSolution {
    public static void main(String[] args) {
        List<List<Integer>> matrix = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
        );
        rotate(matrix);
        System.out.println(matrix); // [[7, 4, 1], [8, 5, 2], [9, 6, 3]]
    }

    public static void rotate(List<List<Integer>> matrix) {
        int n = matrix.size();

        // rotate concentric squares of decreasing width to allow rotating the whole image
        // without using any buffer
        for (int width = n; width >= 2; width -= 2) {
            int padding = (n - width) / 2;
            for (int j = 0; j < width - 1; j++) {
                Cell firstCell = new Cell(0, j);
                Cell cell = firstCell;
                int displaced = matrix.get(cell.i + padding).get(cell.j + padding);

                do {
                    Cell nextCell = move(cell, width);
                    int nextDisplaced = matrix.get(nextCell.i + padding).get(nextCell.j + padding);
                    matrix.get(nextCell.i + padding).set(nextCell.j + padding, displaced);

                    cell = nextCell;
                    displaced = nextDisplaced;
                } while (!cell.equals(firstCell));
            }
        }
    }

    private static Cell move(Cell cell, int w) {
        if (cell.i == 0) return new Cell(cell.j, w - 1);
        if (cell.i == w - 1) return new Cell(cell.j, 0);
        if (cell.j == 0) return new Cell(0, w - 1 - cell.i);
        return new Cell(w - 1, w - 1 - cell.i);
    }

    private static class Cell {
        public final int i;
        public final int j;

        public Cell(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return i == cell.i && j == cell.j;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j);
        }
    }
}
