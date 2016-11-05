package flow;

public class Solution2 {
	public int freeCellCount;
	public Point[][] matrix;
	
	public Solution2(int freeCellCount, Point[][] matrix, int fils, int cols) {
		this.freeCellCount = freeCellCount;
		this.matrix = new Point[fils][cols];
		for (int i = 0; i < fils; i++) {
			for (int j = 0; j < cols; j++) {
				this.matrix[i][j] = new Point(matrix[i][j]);
			}
		}
	}
}