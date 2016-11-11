package flow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuickSolutionChase {

	private static int FILS;
	private static int COLS;
	
	private static List<Point[]> NODES;
	
	private static Solution SOLUTION;
	
	private static Point[][] MATRIX;
	
	private static MyTimer TIMER;
	
	private static boolean SHOW_PROGRESS;
	
	public static Point[][] solve(int fils, int cols, int[][] initial_matrix, int precision, MyTimer timer, boolean show_progress) {
		FILS = fils;
		COLS = cols;
		
		NODES = new ArrayList<>();
		
		MATRIX = new Point[FILS][COLS];
		
		SHOW_PROGRESS = show_progress;
		
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
		
		SOLUTION = new Solution(getEmptyCells(), -1, MATRIX, fils, cols);
		
		long seed = System.nanoTime();
		Collections.shuffle(NODES, new Random(seed));
		
		if (!timer.finished()) {
			TIMER = timer;
			findPathPoint(0, NODES.get(0)[0], precision);
		}
		return SOLUTION.matrix;
	}
	
	private static boolean findPathPoint(int node_index, Point current, int precision) {
		int nextFil;
		int nextCol;
		
		Point end = NODES.get(node_index)[1];
		int[][] directions = new int[4][2];
		directions = getDirections(current, end, directions);
		
		for (int i = 0; i < 4; i++) {	
			if (TIMER.finished())
				return true;
			
			nextFil = current.fil + directions[i][0];
			nextCol = current.col + directions[i][1];
			
			boolean isEnd = false;
			
			if (!isOutOfBounds(nextFil, nextCol)) {
				Point next = MATRIX[nextFil][nextCol];
				
				if(next.is_end_node && (next.value == NODES.get(node_index)[0].value))
					isEnd = true;
				
				if (isEnd) {
					
					if (findLockedNode(node_index)) {
						MATRIX[current.fil][current.col].value = 0;
						return false;
					}
					
					current.setDirection(directions[i][0], directions[i][1]);
					
					if (SHOW_PROGRESS) {
						SimplePrinter.setUpPrinterAndPrintStuff(MATRIX);
						TIMER.stallProgress();
						SimplePrinter.disposeWindow();
					}
					
					if (node_index + 1 == NODES.size()) {
						int emptyCells = getEmptyCells();
						Solution new_sol = new Solution(emptyCells, node_index, MATRIX, FILS, COLS);
						if (new_sol.freeCellCount < SOLUTION.freeCellCount) {
							SOLUTION = new_sol;
							precision --;
						}
						if (emptyCells == 0 || precision == 0)
							return true;
					}
					
					else if(node_index > SOLUTION.node_index) {
		                Solution new_sol = new Solution(getEmptyCells(), node_index, MATRIX, FILS, COLS);
		                SOLUTION = new_sol;
		            }
					
					if (node_index + 1 != NODES.size() && findPathPoint(node_index + 1, NODES.get(node_index + 1)[0], precision))
						return true;
				}
				
				if (next.value == 0) {
					MATRIX[next.fil][next.col].value = MATRIX[current.fil][current.col].value;
					current.setDirection(directions[i][0], directions[i][1]);
					
					if (SHOW_PROGRESS) {
						SimplePrinter.setUpPrinterAndPrintStuff(MATRIX);
						TIMER.stallProgress();
						SimplePrinter.disposeWindow();
					}
					
					if (findPathPoint(node_index, next, precision)) {
						return true;
					}
				}
			}
		}
		if (!current.is_node)
			MATRIX[current.fil][current.col].value = 0;
		current.clearDirection();
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
	
	private static int[][] getDirections(Point current, Point end, int[][] directions) {
		
		if (end.fil < current.fil) {
			directions[0][0] = -1;
			directions[0][1] = 0;
			
			if (end.col <= current.col) {
				directions[1][0] = 0;
				directions[1][1] = -1;
				
				directions[2][0] = 0;
				directions[2][1] = 1;
			}
			else if (end.col > current.col) {
				directions[1][0] = 0;
				directions[1][1] = 1;
				
				directions[2][0] = 0;
				directions[2][1] = -1;
			}
			directions[3][0] = 1;
			directions[3][1] = 0;
		}
		else if (end.fil > current.fil) {
			directions[0][0] = 1;
			directions[0][1] = 0;
			
			if (end.col <= current.col) {
				directions[1][0] = 0;
				directions[1][1] = -1;
				
				directions[2][0] = 0;
				directions[2][1] = 1;
			}
			else if (end.col > current.col) {
				directions[1][0] = 0;
				directions[1][1] = 1;
				
				directions[2][0] = 0;
				directions[2][1] = -1;
			}
			directions[3][0] = -1;
			directions[3][1] = 0;
		}
		else if (end.fil == current.fil) {
			if (end.col <= current.col) {
				directions[0][0] = 0;
				directions[0][1] = -1;
				
				directions[3][0] = 0;
				directions[3][1] = 1;
			}
			else if (end.col > current.col) {
				directions[0][0] = 0;
				directions[0][1] = 1;
				
				directions[3][0] = 0;
				directions[3][1] = -1;
			}
			directions[1][0] = -1;
			directions[1][1] = 0;
			
			directions[2][0] = 1;
			directions[2][1] = 0;
		}
		
		return directions;
	}
	
	private static boolean findLockedNode(int index_node) {
		int[][] total_directions = {{-1,0},{1,0},{0,1},{0,-1}};
		boolean is_ok = false;
		
		for (int i = index_node + 1; i < NODES.size(); i ++) {
			Point current = NODES.get(i)[0];
			int next_fil;
			int next_col;
			is_ok = false;
			
			for (int j = 0; j < 4 && !is_ok; j++) {
				next_fil = current.fil + total_directions[j][0];
				next_col = current.col + total_directions[j][1];
				if (!isOutOfBounds(next_fil, next_col)) {
					if (MATRIX[next_fil][next_col].value == 0 || MATRIX[next_fil][next_col].value == current.value)
						is_ok = true;
				}
			}
			
			if (!is_ok)
				return true;
		}
		
		return false;
	}
	
}
