package flow;

public class Solution {
	public int freeCellCount;
	public Point[][] matrix;
	public int node_index;
	
	public Solution(int freeCellCount, int node_index, Point[][] matrix, int fils, int cols) {
		this.freeCellCount = freeCellCount;
		this.matrix = new Point[fils][cols];
		this.node_index = node_index;
		for (int i = 0; i < fils; i++) {
			for (int j = 0; j < cols; j++) {
				this.matrix[i][j] = new Point(matrix[i][j]);
			}
		}
	}
	public Solution(int freeCellCount, Point[][] matrix, int fils, int cols) {
		this(freeCellCount, 0, matrix, fils, cols);
	}
}