package flow;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmChase {

	private static int FILS;
	private static int COLS;
	
	private static List<Point[]> NODES;
	
	private static Solution SOLUTION;
	
	private static Point[][] MATRIX;
	
	private static MyTimer TIMER;
	
	private static boolean SHOW_PROGRESS;
	
	public static Point[][] solve(int fils, int cols, int[][] initial_matrix, boolean show_progress) {
		FILS = fils;
		COLS = cols;
		
		NODES = new ArrayList<>();
		
		MATRIX = new Point[FILS][COLS];
		
		TIMER = new MyTimer(System.currentTimeMillis());
		
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
		
		SOLUTION = new Solution(getEmptyCells(), MATRIX, fils, cols);
		
		findPathPoint(0, NODES.get(0)[0]);
		return SOLUTION.matrix;
	}
	
	private static boolean findPathPoint(int node_index, Point current) {
		int nextFil;
		int nextCol;
		
		Point end = NODES.get(node_index)[1];
		int[][] directions = new int[4][2];
		directions = getDirections(current, end, directions);
		
		for (int i = 0; i < 4; i++) {
			
			nextFil = current.fil + directions[i][0];
			nextCol = current.col + directions[i][1];
			
			boolean isEnd = false;
			
			if (!isOutOfBounds(nextFil, nextCol)) {
				Point next = MATRIX[nextFil][nextCol];
				
				if(next.is_end_node && (next.value == NODES.get(node_index)[0].value))
					isEnd = true;
				
				if (isEnd) {
					current.setDirection(directions[i][0], directions[i][1]);
					
					if (SHOW_PROGRESS) {
						SimplePrinter.setUpPrinterAndPrintStuff(MATRIX);
						TIMER.stallProgress();
						SimplePrinter.disposeWindow();
					}
					
					if (node_index + 1 == NODES.size()) {
						int emptyCells = getEmptyCells();
						Solution new_sol = new Solution(emptyCells, MATRIX, FILS, COLS);
						if (new_sol.freeCellCount < SOLUTION.freeCellCount)
							SOLUTION = new_sol;
						if (emptyCells == 0)
							return true;
					}
					if (node_index + 1 != NODES.size() && findPathPoint(node_index + 1, NODES.get(node_index + 1)[0]))
						return true;
				}
				
				if (next.value == 0) {
					current.setDirection(directions[i][0], directions[i][1]);
					
					if (SHOW_PROGRESS) {
						SimplePrinter.setUpPrinterAndPrintStuff(MATRIX);
						TIMER.stallProgress();
						SimplePrinter.disposeWindow();
					}
					
					MATRIX[next.fil][next.col].value = MATRIX[current.fil][current.col].value;
					if (findPathPoint(node_index, next)) {
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
	
	public static void printMatrix(int fils, int cols, Point[][] matrix) {
		if (matrix == null) {
			System.out.println("Se ha cometido un error");
			return;
		}
		for (int i = 0; i < fils; i++) {
			for (int j = 0; j < cols; j++) {
				if (matrix[i][j].is_node || matrix[i][j].value == 0)
					System.out.print(matrix[i][j].value + "  ");
				else
					System.out.print((matrix[i][j].value * 10) + " ");
			}
			System.out.println("");
		}
	}
	
	public static void printMatrix(int fils, int cols, int[][] matrix) {
		if (matrix == null) {
			System.out.println("Se ha cometido un error");
			return;
		}
		for (int i = 0; i < fils; i++) {
			for (int j = 0; j < cols; j++) {
					System.out.print(matrix[i][j] + "  ");
			}
			System.out.println("");
		}
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
		
}
