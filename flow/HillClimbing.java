package flow;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HillClimbing {
	
	private static int FILS;
	private static int COLS;
	
	private static List<Point> FIRST_NODES;
	
	private static Solution2 SOLUTION;
	
	private static Point[] DIRECTIONS;
	private static Point[] POSSIBLE_DIRS;
	private static Point END_DIR;
	
	private static int POS_DIRS_COUNT;
	
	private static int HIGHEST_VALUE;
	
	private static Point[][] INITIAL_MATRIX;
	private static Point[][] AUX_MATRIX;
	
	public static Point[][] solve(int fils, int cols, int[][] initial_matrix, long total_time) {
		FILS = fils;
		COLS = cols;
		
		FIRST_NODES = new ArrayList<>();
		
		HIGHEST_VALUE = 0;
		
		INITIAL_MATRIX = new Point[FILS][COLS];
		
		DIRECTIONS = new Point[4];
		DIRECTIONS[0] = new Point(-1, 0);
		DIRECTIONS[1] = new Point(0, 1);
		DIRECTIONS[2] = new Point(1, 0);
		DIRECTIONS[3] = new Point(0, -1);
		
		POSSIBLE_DIRS = new Point[4];
		
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
				INITIAL_MATRIX[i][j] = point;
			}
		}
		
		return hillAlgorithm(0, FIRST_NODES.get(0), total_time);
	}
	
	private static Point[][] hillAlgorithm(int node_index, Point current, long total_time) {
		AUX_MATRIX = new Point[FILS][COLS];
		resetAuxMatrix();
		boolean reset_matrix = false;
		
		int nextFil;
		int nextCol;
		
		long my_time_start = System.currentTimeMillis();
		long my_time_current = 0;
		
		while (total_time - my_time_current > 0) {
			my_time_current = System.currentTimeMillis() - my_time_start;
			
			POS_DIRS_COUNT = 0;
			END_DIR = null;
			
			if (reset_matrix)
				resetAuxMatrix();
			reset_matrix = false;
			
			Algorithm2.printMatrix(FILS, COLS, AUX_MATRIX);
			System.out.println("");
			
			for (int i = 0; i < 4; i++) {
				nextFil = current.fil + DIRECTIONS[i].fil;
				nextCol = current.col + DIRECTIONS[i].col;
				
				if (!isOutOfBounds(nextFil, nextCol)) {
					Point next = AUX_MATRIX[nextFil][nextCol];
					
					if(next.is_end_node && (next.value == FIRST_NODES.get(node_index).value))
						END_DIR = next;
					
					else if (next.value == 0) {
						POSSIBLE_DIRS[POS_DIRS_COUNT] = next;
						POS_DIRS_COUNT += 1;
					}
				}
			}
			
			System.out.println("Cantidad de posibles direcciones: " + POS_DIRS_COUNT);
			
			Random r = new Random();
			int rand_int;
			
			if (END_DIR != null && (rand_int = r.nextInt(2)) == 0) {
				if (END_DIR.value == HIGHEST_VALUE) {
					int emptyCells = getEmptyCells();
					Solution2 new_sol = new Solution2(emptyCells, AUX_MATRIX, FILS, COLS);
					if (emptyCells == 0 || (SOLUTION == null || new_sol.freeCellCount < SOLUTION.freeCellCount)) {
						SOLUTION = new_sol;
						if (emptyCells == 0) {
							return SOLUTION.matrix;
						}
						node_index = 0;
						current = FIRST_NODES.get(0);
						reset_matrix = true;
					}
				}
				else if (node_index + 1 != FIRST_NODES.size()) {
					node_index ++;
					current = FIRST_NODES.get(node_index);
				}
			}
			else if (POS_DIRS_COUNT == 0) {
				node_index = 0;
				current = FIRST_NODES.get(0);
				reset_matrix = true;
			}
			
			else {
				rand_int = r.nextInt(POS_DIRS_COUNT);
				
				nextFil = POSSIBLE_DIRS[rand_int].fil;
				nextCol = POSSIBLE_DIRS[rand_int].col;
				
				Point next = AUX_MATRIX[nextFil][nextCol];
				
				int factor = 1;
				if (current.is_node)
					factor = 10;
				AUX_MATRIX[next.fil][next.col].value = AUX_MATRIX[current.fil][current.col].value * factor;
				current = AUX_MATRIX[next.fil][next.col];
			}
		}
		
		if (SOLUTION == null) {
			return null;
		}
		return SOLUTION.matrix;
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
				if (AUX_MATRIX[i][j].value == 0)
					resp ++;
			}
		}
		return resp;
	}
	
	private static void resetAuxMatrix() {
		for (int i = 0; i < FILS; i++) {
			for (int j = 0; j < COLS; j++) {
				AUX_MATRIX[i][j] = new Point(INITIAL_MATRIX[i][j]);
			}
		}
	}
	
}
