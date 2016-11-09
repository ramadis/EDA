package flow;

import java.util.Random;

public class HCPremium {
	
	private static int FILS;
	private static int COLS;
	private static MyTimer TIMER;
	
	public static Point[][] solve(int fils, int cols, int[][] initial_matrix, long top_time) {
		
		FILS = fils;
		COLS = cols;
		TIMER = new MyTimer(top_time);
		
		Point[][] quick_solution = null;
		Solution2 best_solution = null;
		Solution2 other_solution = null;
		
		while (!TIMER.finished()) {
			quick_solution = Juli.solve(fils, cols, initial_matrix, 1, TIMER);
			
			if (TIMER.finished()) {
				if (best_solution == null)
					return quick_solution;
				else
					return best_solution.matrix;
			}
				
			
			System.out.println("");
			Algorithm2.printMatrix(fils, cols, quick_solution);
			System.out.println("");
			
			while (findEmptyPairs(quick_solution)) {
				System.out.println("CHAU");
			};
			other_solution = new Solution2(getEmptyCells(fils, cols, quick_solution), quick_solution, fils, cols);
			
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
						if (tryToFillHorizontal(first, second, matrix))
							changed = true;
					}
					else if (!isOutOfBounds(i+1, j) && matrix[i+1][j].value == 0) {
						second = new Point(i+1, j);
						if (tryToFillVertical(first, second, matrix))
							changed = true;
					}
				}
			}
		}
		return changed;
		
	}
	
	private static boolean tryToFillVertical(Point first, Point second, Point[][] matrix) {
		Random r = new Random(System.currentTimeMillis());
		boolean coloured;
		int aux_fil, aux_col, randInt, new_value;
		aux_fil = first.fil;
		aux_col = first.col - 1;
		coloured = false;
		new_value = 0;
		if (!isOutOfBounds(aux_fil, aux_col) && matrix[aux_fil][aux_col].value != 0) {
			if (matrix[aux_fil][aux_col].value == matrix[aux_fil + 1][aux_col].value) {
				new_value = matrix[aux_fil][aux_col].value;
				coloured = true;
			}
		}
		aux_col = first.col + 1;
		if (!isOutOfBounds(aux_fil, aux_col) && matrix[aux_fil][aux_col].value != 0) {
			if (matrix[aux_fil][aux_col].value == matrix[aux_fil + 1][aux_col].value) {
				if (coloured){
					if (matrix[aux_fil][aux_col].value != matrix[aux_fil][aux_col-2].value){
						randInt = r.nextInt(2);
						if (randInt == 0){
							new_value = matrix[aux_fil][aux_col].value;
						}
					}
				}
				else {
					new_value = matrix[aux_fil][aux_col].value;
					coloured = true;
				}
			}
		}
		if (coloured){
			matrix[first.fil][first.col].value =  new_value;
			matrix[second.fil][second.col].value = new_value;
			return true;
		}
		else
			return false;
	}
	
	private static boolean tryToFillHorizontal(Point first, Point second, Point[][] matrix) {
		Random r = new Random(System.currentTimeMillis());
		boolean coloured;
		int aux_fil, aux_col, randInt, new_value;
		aux_col = first.col;
		aux_fil = first.fil - 1;
		coloured = false;
		new_value = 0;
		if (!isOutOfBounds(aux_fil, aux_col) && matrix[aux_fil][aux_col].value != 0) {
			if (matrix[aux_fil][aux_col].value == matrix[aux_fil][aux_col + 1].value) {
				new_value = matrix[aux_fil][aux_col].value;
				coloured = true;
			}
		}
		aux_fil = first.fil + 1;
		if (!isOutOfBounds(aux_fil, aux_col) && matrix[aux_fil][aux_col].value != 0) {
			if (matrix[aux_fil][aux_col].value == matrix[aux_fil][aux_col + 1].value) {
				if (coloured){
					if (matrix[aux_fil][aux_col].value != matrix[aux_fil-2][aux_col].value){
						randInt = r.nextInt(2);
						if (randInt == 0){
							new_value = matrix[aux_fil][aux_col].value;
						}
					}
				}
				else {
					new_value = matrix[aux_fil][aux_col].value;
					coloured = true;
				}
			}
		}
		if (coloured){
			matrix[first.fil][first.col].value =  new_value;
			matrix[second.fil][second.col].value = new_value;
			return true;
		}
		else
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
	
}
