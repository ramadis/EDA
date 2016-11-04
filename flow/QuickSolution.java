package flow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaquin on 04/11/16.
 */

public class QuickSolution {

    private static int FILS;
    private static int COLS;
    private static List<Point> nodes;
    private static Solution solution;
    private static int[][] directions = {{-1,0},{0,1},{1,0},{0,-1}};
    private static int HIGHEST_VALUE;
    private static int[][] MATRIX;

    public static int[][] solve(int fils, int cols, int[][] matrix, int precision) {
        FILS = fils;
        COLS = cols;
        nodes = new ArrayList<>();
        HIGHEST_VALUE = 0;
        MATRIX = matrix;

        List<Integer> values = new ArrayList<>();

        for (int i = 0; i < fils; i++) {
            for (int j = 0; j < cols; j++) {
                int value = MATRIX[i][j];
                if (value != 0 && !values.contains(value)) {
                    nodes.add(new Point(i, j));
                    values.add(value);
                    if (value > HIGHEST_VALUE)
                        HIGHEST_VALUE = value;
                }
            }
        }

        // Llama al algoritmo pasandole el primer nodo y su posición dentro del array de nodos.
        if (findPathPoint(0, nodes.get(0), precision))
            return MATRIX;
        return solution.matrix;
    }

    private static boolean findPathPoint(int node_index, Point current, int p) {
        boolean isNode = false;
        if (nodes.contains(current)) {
            isNode = true;
        }
        Point next;
        int nextFil;
        int nextCol;

        for (int i = 0; i < 4; i++) {
            nextFil = current.fil + directions[i][0];
            nextCol = current.col + directions[i][1];
            next = new Point(nextFil, nextCol);
            if (!isOutOfBounds(next)) {
                boolean isEnd = false;
                if (isNode && isEndFromNode(current, next))
                    isEnd = true;
                else if (!isNode && isEndFromPoint(current, next, node_index))
                    isEnd = true;
                if (isEnd) {
                    if (MATRIX[next.fil][next.col] == HIGHEST_VALUE) {
                        p--;
                        int emptyCells = getEmptyCells();
                        if (emptyCells == 0) {
                            solution = new Solution(emptyCells, MATRIX);
                            return true;
                        }
                        Solution newSol = new Solution(emptyCells, MATRIX);
                        if (solution == null || newSol.freeCellCount < solution.freeCellCount)
                            solution = newSol;
                        if ( p == 0 )
                            return true;
                    }
                    if (node_index + 1 != nodes.size() && findPathPoint(node_index + 1, nodes.get(node_index + 1), p)) {
                        return true;
                    }
                }
                if (isFree(next)) {
                    int factor = 1;
                    if (isNode)
                        factor = 10;
                    MATRIX[next.fil][next.col] = MATRIX[current.fil][current.col] * factor;
                    if (findPathPoint(node_index, next, p)) {
                        return true;
                    }
                }

            }
        }
        if (!isNode)
            MATRIX[current.fil][current.col] = 0;
        return false;
    }

    private static boolean isOutOfBounds(Point p) {
        if (p.fil < 0 || p.fil >= FILS || p.col < 0 || p.col >= COLS )
            return true;
        return false;
    }

    private static boolean isFree(Point p) {
        if (MATRIX[p.fil][p.col] != 0)
            return false;
        return true;
    }

    private static int getEmptyCells() {
        int resp = 0;
        for (int i = 0; i < FILS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (MATRIX[i][j] == 0)
                    resp ++;
            }
        }
        return resp;
    }

    private static boolean isEndFromNode(Point current, Point next) {
        if (MATRIX[current.fil][current.col] == MATRIX[next.fil][next.col]) {
            return true;
        }
        return false;
    }

    private static boolean isEndFromPoint(Point current, Point next, int node_index) {
        if ((MATRIX[next.fil][next.col] == (MATRIX[current.fil][current.col] / 10))
                && !next.equals(nodes.get(node_index)))
            return true;
        return false;
    }

    public static void printMatrix(int fils, int cols, int[][] matrix) {
        if (matrix == null) {
            System.out.println("Se ha cometido un error");
            return;
        }
        for (int i = 0; i < fils; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] >= 10)
                    System.out.print(matrix[i][j] + " ");
                else
                    System.out.print(matrix[i][j] + "  ");
            }
            System.out.println("");
        }
    }
}
