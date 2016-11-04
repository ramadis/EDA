package flow;

import java.util.*;

/**
 * Created by joaquin on 26/10/16.
 */

public class AlgorithmHC {

    private static int FILS;
    private static int COLS;

    private static List<Point> nodes;

    private static PartialSolution pSolution;
    private static int CANT_DIR = 4;
    private static int[][] directions = {{-1,0},{0,1},{1,0},{0,-1}};

    private static int HIGHEST_VALUE;

    private static int[][] MATRIX;

    private static Random r;

    private static class FlowState{
        FlowState previous;
        int[][]matrix;
        int fils,cols;
        int freeNodes;
        int nodeIndex;
        Point current;

        Random r;

        FlowState(int[][] m, int f, int c, FlowState p, int freeN, int nodeI, Point currN){
            this.matrix = m;
            this.fils = f;
            this.cols = c;
            this.previous = p;
            this.freeNodes = freeN;
            this.nodeIndex = nodeI;
            this.current = currN;
            this.r = new Random();
            r.setSeed(System.currentTimeMillis());
        }


        public boolean isSolution() {
            if(nodeIndex==nodes.size() && freeNodes==0)
                return true;
            else{
                if(nodeIndex>pSolution.nodeIndex || (nodeIndex==pSolution.nodeIndex && freeNodes<pSolution.freeCellCount)) {
                    PartialSolution newS = new PartialSolution(freeNodes, nodeIndex, matrix);
                    pSolution = newS;
                }
                return false;
            }
        }

        public FlowState move(){
            List<FlowState> result = new ArrayList<>();
            boolean isNode = false;
            if (nodes.contains(current)) {
                isNode = true;
            }
            Point next;
            int nextFil;
            int nextCol;

            for (int i = 0; i < CANT_DIR; i++) {
                nextFil = current.fil + directions[i][0];
                nextCol = current.col + directions[i][1];
                next = new Point(nextFil, nextCol);
                if (!isOutOfBounds(next)) {
                    boolean isEnd = false;
                    if (isNode && isEndFromNode(matrix, current, next))
                        isEnd = true;
                    else if (!isNode && isEndFromPoint(matrix, current, next, nodeIndex))
                        isEnd = true;
                    if (isEnd) {
                        if(nodeIndex+1 != nodes.size()){
                            int[][] mNew = new int[fils][cols];
                            copyMatrix(mNew,matrix,fils,cols);
                            FlowState newState = new FlowState(mNew, fils, cols, this, freeNodes, nodeIndex+1, nodes.get(nodeIndex + 1));
                            result.add(newState);
                        }
                    }
                    if (isFree(matrix, next)) {
                        int[][] mNew = new int[fils][cols];
                        copyMatrix(mNew,matrix,fils,cols);
                        FlowState newState = new FlowState(mNew, fils, cols, this, freeNodes-1, nodeIndex, next);

                        int factor = 1;
                        if (isNode)
                            factor = 10;
                        newState.matrix[next.fil][next.col] = matrix[current.fil][current.col] * factor;
                        result.add(newState);
                    }
                }
            }
            if(result.size()==0)
                return null;
            else{
            int rand = r.nextInt(result.size());
            return result.get(rand);
            }
        }

        @Override
        public boolean equals(Object obj) {
            FlowState other = (FlowState) obj;
            for(int i=0; i<fils; i++){
                for(int j=0; j<cols; j++){
                    if(matrix[i][j]!=other.matrix[i][j]){
                        return false;
                    }
                }
            }
            if(nodeIndex==other.nodeIndex)
                return true;
            else return false;
        }

        @Override
        public int hashCode() {
            int count=0;
            for(int i=0; i<fils; i++) {
                for (int j = 0; j < cols; j++) {
                    count+=i+j+matrix[i][j];
                }
            }
            return count*nodeIndex;
        }
    }




    public static int[][] solve(int fils, int cols, int[][] matrix, long total_time) {
        FILS = fils;
        COLS = cols;
        nodes = new ArrayList<>();
        HIGHEST_VALUE = 0;

        long my_time_start = System.currentTimeMillis();
        long my_time_current = 0;

        pSolution = new PartialSolution(getEmptyCells(matrix), 0, matrix);
        List<Integer> values = new ArrayList<>();

        r = new Random(System.currentTimeMillis());

        for (int i = 0; i < fils; i++) {
            for (int j = 0; j < cols; j++) {
                int value = matrix[i][j];
                if (value != 0 && !values.contains(value)) {
                    nodes.add(new Point(i, j));
                    values.add(value);
                    if (value > HIGHEST_VALUE)
                        HIGHEST_VALUE = value;
                }
            }
        }
        while (total_time - my_time_current > 0) {
            my_time_current = System.currentTimeMillis() - my_time_start;

            Set<FlowState> visited = new HashSet<>();
            Queue<FlowState> queue = new LinkedList<>();
            int[][] m2 = new int[fils][cols];
            copyMatrix(m2,matrix,fils,cols);
            FlowState state = new FlowState(m2, fils, cols, null, getEmptyCells(m2), 0, nodes.get(0));
            visited.add(state);
            queue.add(state);

            while (!queue.isEmpty()) {

                state = queue.remove();

                if (state.isSolution()) {
                    return state.matrix;
                }

                FlowState neighbour = state.move();
                if (neighbour != null && !visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }

        }
        return pSolution.matrix;
    }

    private static boolean isOutOfBounds(Point p) {
        if (p.fil < 0 || p.fil >= FILS || p.col < 0 || p.col >= COLS )
            return true;
        return false;
    }

    private static boolean isFree(int[][] m,Point p) {
        if (m[p.fil][p.col] != 0)
            return false;
        return true;
    }

    private static int getEmptyCells(int m[][]) {
        int count = 0;
        for (int i = 0; i < FILS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (m[i][j] == 0)
                    count ++;
            }
        }
        return count;
    }

    private static boolean isEndFromNode(int[][] m, Point current, Point next) {
        if (m[current.fil][current.col] == m[next.fil][next.col]) {
            return true;
        }
        return false;
    }

    private static boolean isEndFromPoint(int[][] m,Point current, Point next, int node_index) {
        if ((m[next.fil][next.col] == (m[current.fil][current.col] / 10))
                && !next.equals(nodes.get(node_index)))
            return true;
        return false;
    }

    private static void copyMatrix(int[][] mDest, int[][] mOrig, int f, int c){
        for(int i=0; i<f; i++){
            for(int j=0; j<c; j++){
                mDest[i][j]=mOrig[i][j];
            }
        }
    }

    public static void printMatrix(int fils, int cols, int[][] matrix) {
        if (matrix == null) {
            System.out.println("Se ha cometido un error");
            return;
        }
        for (int i = 0; i < fils; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] >= 10)
                    System.out.print(matrix[i][j] + " ");
                else
                    System.out.print(matrix[i][j] + "  ");
            }
            System.out.println("");
        }
    }
}