package flow;

/**
 * Created by joaquin on 26/10/16.
 */

public class PartialSolution {
	public int freeCellCount;
	public Point[][] matrix;
	public int node_index;
	
	public PartialSolution(int freeCellCount, int node_index, Point[][] matrix, int fils, int cols) {
		this.freeCellCount = freeCellCount;
		this.matrix = new Point[fils][cols];
		this.node_index = node_index;
		for (int i = 0; i < fils; i++) {
			for (int j = 0; j < cols; j++) {
				this.matrix[i][j] = new Point(matrix[i][j]);
			}
		}
	}
}