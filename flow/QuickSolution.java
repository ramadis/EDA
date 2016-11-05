package flow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuickSolution {

	private static int FILS;
	private static int COLS;
	
	private static List<Point[]> NODES;
	
	private static Solution2 SOLUTION;
	
	private static int[][] DIRECTIONS = {{-1,0},{0,1},{1,0},{0,-1}};
	
	private static Point[][] MATRIX;
	
	private static MyTimer TIMER;
	
	public static Point[][] solve(int fils, int cols, int[][] initial_matrix, int precision, MyTimer timer) {
		FILS = fils;
		COLS = cols;
		
		NODES = new ArrayList<>();
		
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
					Point[] pair_node = new Point[2];
					if (!values.contains(value)) {
						pair_node[0] = point;
						NODES.add(pair_node);
						values.add(value);
					}
					else {
						point.is_end_node = true;
						for (Point[] p : NODES) {
							if (p[0].value == value)
								NODES.get(NODES.indexOf(p))[1] = point;
						}
					}
				}
				MATRIX[i][j] = point;
			}
		}
		
		long seed = System.nanoTime();
		Collections.shuffle(NODES, new Random(seed));
		
		if (!timer.finished()) {
			TIMER = timer;
			findPathPoint(0, NODES.get(0)[0], precision);
		}
		if (SOLUTION != null)
			return SOLUTION.matrix;
		return MATRIX;
	}
	
	private static boolean findPathPoint(int node_index, Point current, int precision) {
		int nextFil;
		int nextCol;
		
		for (int i = 0; i < 4; i++) {
			
			if (TIMER.finished())
				return true;
			
			nextFil = current.fil + DIRECTIONS[i][0];
			nextCol = current.col + DIRECTIONS[i][1];
			
			boolean isEnd = false;
			
			if (!isOutOfBounds(nextFil, nextCol)) {
				Point next = MATRIX[nextFil][nextCol];
				
				if(next.is_end_node && (next.value == NODES.get(node_index)[0].value))
					isEnd = true;
				
				if (isEnd) {
					if (node_index + 1 == NODES.size()) {
						precision--;
						int emptyCells = getEmptyCells();
						Solution2 new_sol = new Solution2(emptyCells, MATRIX, FILS, COLS);
						if (SOLUTION == null || new_sol.freeCellCount < SOLUTION.freeCellCount)
							SOLUTION = new_sol;
						if (emptyCells == 0 || precision == 0)
							return true;
					}
					if (node_index + 1 != NODES.size() && findPathPoint(node_index + 1, NODES.get(node_index + 1)[0], precision))
						return true;
				}
				
				if (next.value == 0) {
					MATRIX[next.fil][next.col].value = MATRIX[current.fil][current.col].value;
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
