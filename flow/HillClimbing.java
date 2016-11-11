package flow;

import java.util.Random;

public class HillClimbing {
	
	private static int FILS;
	private static int COLS;
	private static MyTimer TIMER;
	
	private static boolean SHOW_PROGRESS;
	
	public static Point[][] solve(int fils, int cols, int[][] initial_matrix, long top_time, boolean show_progress) {
		
		FILS = fils;
		COLS = cols;
		TIMER = new MyTimer(top_time);
		
		SHOW_PROGRESS = show_progress;
		
		Point[][] quick_solution = null;
		Solution best_solution = null;
		Solution other_solution = null;
		
		while (!TIMER.finished()) {
			quick_solution = QuickSolutionChase.solve(fils, cols, initial_matrix, 1, TIMER, show_progress);
			
			if (TIMER.finished()) {
				if (best_solution == null)
					return quick_solution;
				else
					return best_solution.matrix;
			}
			
			while (findEmptyPairs(quick_solution));
			
			other_solution = new Solution(getEmptyCells(fils, cols, quick_solution), quick_solution, fils, cols);
			
			if (best_solution == null || (other_solution.freeCellCount < best_solution.freeCellCount))
				best_solution = other_solution;
			if (best_solution.freeCellCount == 0) {
				return best_solution.matrix;
			}
			
		}
		
		return best_solution.matrix;
	
	}
	
	private static boolean findEmptyPairs(Point[][] matrix) {
		
		boolean changed = false;
		
		for (int i = 0; i < FILS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (matrix[i][j].value == 0) {
					Point first = new Point(i,j);
					Point second;
					if (!isOutOfBounds(i, j+1) && matrix[i][j + 1].value == 0) {
						second = new Point(i, j+1);
						if (tryToFillHorizontal(first, second, matrix)) {
							if (SHOW_PROGRESS) {
								SimplePrinter.setUpPrinterAndPrintStuff(matrix);
								TIMER.stallProgress();
								SimplePrinter.disposeWindow();
							}
							changed = true;
						}
					}
					else if (!isOutOfBounds(i+1, j) && matrix[i+1][j].value == 0) {
						second = new Point(i+1, j);
						if (tryToFillVertical(first, second, matrix)) {
							if (SHOW_PROGRESS) {
								SimplePrinter.setUpPrinterAndPrintStuff(matrix);
								TIMER.stallProgress();
								SimplePrinter.disposeWindow();
							}
							changed = true;
						}
					}
				}
			}
		}
		return changed;
		
	}
	
	private static boolean tryToFillVertical(Point top, Point bottom, Point[][] matrix) {
		Random r = new Random(System.currentTimeMillis());
		boolean fill_with_left = false;
		boolean fill_with_right = false;
		
		int aux_fil, aux_col, randInt;
		aux_fil = top.fil;
		aux_col = top.col - 1;

		if (!isOutOfBounds(aux_fil, aux_col) && matrix[aux_fil][aux_col].value != 0) {
			if (matrix[aux_fil][aux_col].value == matrix[aux_fil + 1][aux_col].value) {
				if (areMarriedVerticaly(matrix[aux_fil][aux_col], matrix[aux_fil + 1][aux_col]))
					fill_with_left = true;
			}
		}
		aux_col = top.col + 1;
		if (!isOutOfBounds(aux_fil, aux_col) && matrix[aux_fil][aux_col].value != 0) {
			if (matrix[aux_fil][aux_col].value == matrix[aux_fil + 1][aux_col].value) {
				if (areMarriedVerticaly(matrix[aux_fil][aux_col], matrix[aux_fil + 1][aux_col]))
					fill_with_right = true;
			}
		}
		if (fill_with_left && fill_with_right) {
			randInt = r.nextInt(2);
			if (randInt == 0)
				fill_with_right = false;
			else
				fill_with_left = false;
		}
		if (fill_with_left) {
			aux_col = top.col - 1;
			Point left_top = matrix[aux_fil][aux_col];
			Point left_bottom = matrix[aux_fil + 1][aux_col];
			
			top.value = left_top.value;
			bottom.value = left_top.value;
			
			if (left_top.direction_fil == 1) {				// If the left_top points down
				left_top.direction_fil = 0;					// Make it point right
				left_top.direction_col = 1;
				top.direction_fil = 1;						// Then make the top one that was empty point down 
				top.direction_col = 0;
				bottom.direction_fil = 0;					// And the bottom one that was empty point left
				bottom.direction_col = -1;
				
				matrix[left_top.fil][left_top.col] = left_top;
			}
			else {											// If the left_bottom points up
				left_bottom.direction_fil = 0;				// Make it point right
				left_bottom.direction_col = 1;
				bottom.direction_fil = -1;					// Then make the bottom one that was empty point up
				bottom.direction_col = 0;
				top.direction_fil = 0;						// And the top one that was empty point left
				top.direction_col = -1;
				
				matrix[left_bottom.fil][left_bottom.col] = left_bottom;
			}
			matrix[top.fil][top.col] = top;
			matrix[bottom.fil][bottom.col] = bottom;
			return true;
		}
		if (fill_with_right) {
			aux_col = top.col + 1;
			Point right_top = matrix[aux_fil][aux_col];
			Point right_bottom = matrix[aux_fil + 1][aux_col];
			
			top.value = right_top.value;
			bottom.value = right_top.value;
			
			if (right_top.direction_fil == 1) {				// If the right_top points down
				right_top.direction_fil = 0;				// Make it point left
				right_top.direction_col = -1;
				top.direction_fil = 1;						// Then the top one that was empty point down
				top.direction_col = 0;
				bottom.direction_fil = 0;					// And the bottom one that was empty point right
				bottom.direction_col = 1;
				
				matrix[right_top.fil][right_top.col] = right_top;
			}
			else {											// If the right_bottom points up
				right_bottom.direction_fil = 0;				// Make it point left
				right_bottom.direction_col = -1;
				bottom.direction_fil = -1;					// Then the bottom one that was empty point up
				bottom.direction_col = 0;
				top.direction_fil = 0;						// And the top one that was empty point right
				top.direction_col = 1;
				
				matrix[right_bottom.fil][right_bottom.col] = right_bottom;
			}
			matrix[top.fil][top.col] = top;
			matrix[bottom.fil][bottom.col] = bottom;
			return true;
		}
		return false;
	}
	
	private static boolean tryToFillHorizontal(Point left, Point right, Point[][] matrix) {
		Random r = new Random(System.currentTimeMillis());
		boolean fill_with_top = false;
		boolean fill_with_bottom = false;
		
		int aux_fil, aux_col, randInt;
		aux_fil = left.fil - 1;
		aux_col = left.col;

		if (!isOutOfBounds(aux_fil, aux_col) && matrix[aux_fil][aux_col].value != 0) {
			if (matrix[aux_fil][aux_col].value == matrix[aux_fil][aux_col + 1].value) {
				if (areMarriedHorizontaly(matrix[aux_fil][aux_col], matrix[aux_fil][aux_col + 1]))
					fill_with_top = true;
			}
		}
		aux_fil = left.fil + 1;
		if (!isOutOfBounds(aux_fil, aux_col) && matrix[aux_fil][aux_col].value != 0) {
			if (matrix[aux_fil][aux_col].value == matrix[aux_fil][aux_col + 1].value) {
				if (areMarriedHorizontaly(matrix[aux_fil][aux_col], matrix[aux_fil][aux_col + 1]))
					fill_with_bottom = true;
			}
		}
		if (fill_with_top && fill_with_bottom) {
			randInt = r.nextInt(2);
			if (randInt == 0)
				fill_with_top = false;
			else
				fill_with_bottom = false;
		}
		if (fill_with_top) {
			aux_fil = left.fil - 1;
			Point top_left = matrix[aux_fil][aux_col];
			Point top_right = matrix[aux_fil][aux_col + 1];
			
			left.value = top_left.value;
			right.value = top_left.value;
			
			if (top_left.direction_col == 1) {				// If the top_left points right
				top_left.direction_fil = 1;					// Make it point down
				top_left.direction_col = 0;
				left.direction_fil = 0;						// Then make the left one that was empty point right
				left.direction_col = 1;
				right.direction_fil = -1;					// And the right one that was empty point up
				right.direction_col = 0;
				
				matrix[top_left.fil][top_left.col] = top_left;
			}
			else {											// If the top_right points left
				top_right.direction_fil = 1;				// Make it point down
				top_right.direction_col = 0;
				right.direction_fil = 0;					// Then make the right one that was empty point left
				right.direction_col = -1;
				left.direction_fil = -1;					// And the left one that was empty point up
				left.direction_col = 0;
				
				matrix[top_right.fil][top_right.col] = top_right;
			}
			matrix[left.fil][left.col] = left;
			matrix[right.fil][right.col] = right;
			return true;
		}
		if (fill_with_bottom) {
			aux_fil = left.fil + 1;
			Point bottom_left = matrix[aux_fil][aux_col];
			Point bottom_right = matrix[aux_fil][aux_col + 1];
			
			left.value = bottom_left.value;
			right.value = bottom_left.value;
			
			if (bottom_left.direction_col == 1) {			// If the bottom_left points right
				bottom_left.direction_fil = -1;				// Make it point up
				bottom_left.direction_col = 0;
				left.direction_fil = 0;						// Then make the left one that was empty point right
				left.direction_col = 1;
				right.direction_fil = 1;					// And the right one that was empty point down
				right.direction_col = 0;
				
				matrix[bottom_left.fil][bottom_left.col] = bottom_left;
			}
			else {											// If the bottom_right points left
				bottom_right.direction_fil = -1;			// Make it point up
				bottom_right.direction_col = 0;
				right.direction_fil = 0;					// Then the right one that was empty point left
				right.direction_col = -1;
				left.direction_fil = 1;						// And the left one that was empty point down
				left.direction_col = 0;
				
				matrix[bottom_right.fil][bottom_right.col] = bottom_right;
			}
			matrix[left.fil][left.col] = left;
			matrix[right.fil][right.col] = right;
			return true;
		}
		return false;
	}
	
	private static boolean isOutOfBounds(int fil, int col) {
		if (fil < 0 || fil >= FILS || col < 0 || col >= COLS )
			return true;
		return false;
	}
	
	private static int getEmptyCells(int fils, int cols, Point[][] matrix) {
		int resp = 0;
		for (int i = 0; i < fils; i++) {
			for (int j = 0; j < cols; j++) {
				if (matrix[i][j].value == 0)
					resp ++;
			}
		}
		return resp;
	}
	
	private static boolean areMarriedVerticaly(Point top, Point bottom) {
		if (top.direction_fil == 1 || bottom.direction_fil == -1) {
			return true;
		}
		return false;
	}
	
	private static boolean areMarriedHorizontaly(Point left, Point right) {
		if (left.direction_col == 1 || right.direction_col == -1) {
			return true;
		}
		return false;
	}
	
}
