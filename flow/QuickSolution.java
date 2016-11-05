package flow;

import java.util.ArrayList;
import java.util.List;

public class QuickSolution {

	private static int FILS;
	private static int COLS;
	
	private static List<Point> FIRST_NODES;
	
	private static Solution2 SOLUTION;
	
	private static int[][] DIRECTIONS = {{-1,0},{0,1},{1,0},{0,-1}};
	
	private static int HIGHEST_VALUE;
	
	private static Point[][] MATRIX;
	
	public static Point[][] solve(int fils, int cols, int[][] initial_matrix, int precision) {
		FILS = fils;
		COLS = cols;
		
		FIRST_NODES = new ArrayList<>();
		
		HIGHEST_VALUE = 0;
		MATRIX = new Point[FILS][COLS];
		SOLUTION = null;
		
		List<Integer> values = new ArrayList<>();
		
		for (int i = 0; i < fils; i++) {
			for (int j = 0; j < cols; j++) {
				Point point = new Point(i, j);
				int value = initial_matrix[i][j];
				if (value != 0) {
					point.value = value;
					point.is_node = true;
					if (!values.contains(value)) {
						FIRST_NODES.add(point);
						values.add(value);
					}
					else
						point.is_end_node = true;
				}
				if (value > HIGHEST_VALUE)
					HIGHEST_VALUE = value;
				MATRIX[i][j] = point;
			}
		}
		
		findPathPoint(0, FIRST_NODES.get(0), precision);
		return SOLUTION.matrix;
	}
	
	private static boolean findPathPoint(int node_index, Point current, int precision) {
		int nextFil;
		int nextCol;
		
		for (int i = 0; i < 4; i++) {
			
			nextFil = current.fil + DIRECTIONS[i][0];
			nextCol = current.col + DIRECTIONS[i][1];
			
			boolean isEnd = false;
			
			if (!isOutOfBounds(nextFil, nextCol)) {
				Point next = MATRIX[nextFil][nextCol];
				
				if(next.is_end_node && (next.value == FIRST_NODES.get(node_index).value))
					isEnd = true;
				
				if (isEnd) {
					if (next.value == HIGHEST_VALUE) {
						precision--;
						int emptyCells = getEmptyCells();
						Solution2 new_sol = new Solution2(emptyCells, MATRIX, FILS, COLS);
						if (SOLUTION == null || new_sol.freeCellCount < SOLUTION.freeCellCount)
							SOLUTION = new_sol;
						if (emptyCells == 0 || precision == 0)
							return true;
					}
					if (node_index + 1 != FIRST_NODES.size() && findPathPoint(node_index + 1, FIRST_NODES.get(node_index + 1), precision))
						return true;
				}
				
				if (next.value == 0) {
					int factor = 1;
					if (current.is_node)
						factor = 10;
					MATRIX[next.fil][next.col].value = MATRIX[current.fil][current.col].value * factor;
					if (findPathPoint(node_index, next, precision)) {
						return true;
					}
				}
			}
		}
		if (!current.is_node)
			MATRIX[current.fil][current.col].value = 0;
		return false;
	}
	
	private static boolean isOutOfBounds(int fil, int col) {
		if (fil < 0 || fil >= FILS || col < 0 || col >= COLS )
			return true;
		return false;
	}
	
	private static int getEmptyCells() {
		int resp = 0;
		for (int i = 0; i < FILS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (MATRIX[i][j].value == 0)
					resp ++;
			}
		}
		return resp;
	}
	
}
