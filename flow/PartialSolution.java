package flow;

/**
 * Created by joaquin on 26/10/16.
 */

public class PartialSolution {
    int freeCellCount;
    int[][] matrix;
    int nodeIndex;

    public PartialSolution(int freeCellCount, int nodeIndex, int[][] matrix) {
        this.freeCellCount = freeCellCount;
        this.nodeIndex = nodeIndex;
        this.matrix = matrix;
    }

}