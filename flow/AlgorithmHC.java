package flow;

import java.util.*;

/**
 * Created by joaquin on 26/10/16.
 */

public class AlgorithmHC {

    private static int FILS;
    private static int COLS;

    private static List<Point> nodes;

    private static Solution solution;
    private static int CANT_DIR = 4;
    private static int[][] directions = {{-1,0},{0,1},{1,0},{0,-1}};

    private static int HIGHEST_VALUE;

    private static int[][] MATRIX;

    /**
     * Método llamado por el usuario para obtener la solución aproximada del problema.
     * Inicializa las variables estáticas para que puedan ser usadas por el algoritmo.
     */

    private static class FlowState{
        FlowState previous;
        int[][]matrix;
        int fils,cols;
        int freeNodes;
        int nodeIndex;
        Point current;

        FlowState(int[][] m, int f, int c, FlowState p, int freeN, int nodeI, Point currN){
            this.matrix = m;
            this.fils = f;
            this.cols = c;
            this.previous = p;
            this.freeNodes = freeN;
            this.nodeIndex = nodeI;
            this.current = currN;
        }


        public boolean isSolution() {
            if(nodeIndex==nodes.size() && freeNodes==0)
                return true;
            else return false;
        }

        public List<FlowState> moves(){
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

                        /*if (matrix[next.fil][next.col] == HIGHEST_VALUE) {
                            int emptyCells = getEmptyCells();
                            if (emptyCells == 0) {
                                solution = new Solution(emptyCells, matrix);
                                return true;
                            }
                            Solution newSol = new Solution(emptyCells, matrix);
                            if (solution == null || newSol.freeCellCount < solution.freeCellCount)
                                solution = newSol;
                        }*/

                        if(nodeIndex+1 != nodes.size()){
                            FlowState newState = new FlowState(matrix, fils, cols, this, freeNodes-1, nodeIndex+1, next);
                            result.add(newState);
                        }

                        /*if (node_index + 1 != nodes.size() && findPathPoint(node_index + 1, nodes.get(node_index + 1))) {
                            return true;
                        }*/
                    }
                    if (isFree(matrix, next)) {
                        FlowState newState = new FlowState(matrix, fils, cols, this, freeNodes-1, nodeIndex, next);

                        int factor = 1;
                        if (isNode)
                            factor = 10;
                        newState.matrix[next.fil][next.col] = matrix[current.fil][current.col] * factor;
                        result.add(newState);
                        /*matrix[next.fil][next.col] = matrix[current.fil][current.col] * factor;
                        if (findPathPoint(node_index, next)) {
                            return true;
                        }*/
                    }

                }
            }

            /*
            if (!isNode)
                MATRIX[current.fil][current.col] = 0;
            return false;
            */

            return result;
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
            return true;
        }

        @Override
        public int hashCode() {
            return 1;
        }
    }


    public static int[][] solve(int fils, int cols, int[][] matrix) {
        FILS = fils;
        COLS = cols;
        nodes = new ArrayList<>();
        HIGHEST_VALUE = 0;
        int[][] m2 = new int[fils][cols];
        copyMatrix(m2,matrix,fils,cols);
        MATRIX = m2;

        List<Integer> values = new ArrayList<>();

        for (int i = 0; i < fils; i++) {
            for (int j = 0; j < cols; j++) {
                int value = m2[i][j];
                if (value != 0 && !values.contains(value)) {
                    nodes.add(new Point(i, j));
                    values.add(value);
                    if (value > HIGHEST_VALUE)
                        HIGHEST_VALUE = value;
                }
            }
        }

        Set<FlowState> visited = new HashSet<>();
        Queue<FlowState> queue = new LinkedList<>();

        FlowState state = new FlowState(m2,fils,cols,null,getEmptyCells(m2),0,nodes.get(0));

        visited.add(state);
        queue.add(state);

        while (!queue.isEmpty()) {

            state = queue.remove();

            if (state.isSolution()) {
                /*while (state != null) {
                    //System.out.println(state);
                    //state = state.previous;
                }*/
                return state.matrix;
            }
            for (FlowState neighbor : state.moves()){
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    }

    /**
     * Checkea si un punto está fuera de los límites de la matriz.
     *
     * @param p 	Punto a checkear.
     * @return 		true, si p está fuera de los límites y false en caso contrario.
     */
    private static boolean isOutOfBounds(Point p) {
        if (p.fil < 0 || p.fil >= FILS || p.col < 0 || p.col >= COLS )
            return true;
        return false;
    }

    /**
     * Checkea si un punto o celda de la matriz está libre.
     *
     * @param p		Punto que determina la celda a chequear.
     * @return		true, si la celda está vacia y false en caso contrario.
     */
    private static boolean isFree(int[][] m,Point p) {
        if (m[p.fil][p.col] != 0)
            return false;
        return true;
    }

    /**
     *
     * @return 		Cantidad de celdas vacias que tiene la matriz estática en el momento del llamado.
     */
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

    /**
     * Checkea si el siguiente punto es el final del nodo que se está analizando,
     * cuando la celda actual es el nodo de origen.
     *
     * @param current		Celda actual.
     * @param next			Celda a analizar.
     * @return				true, si next es el nodo final de ese "color", false en caso contrario.
     */
    private static boolean isEndFromNode(int[][] m, Point current, Point next) {
        if (m[current.fil][current.col] == m[next.fil][next.col]) {
            return true;
        }
        return false;
    }

    /**
     * Idem anterior, pero teniendo en cuenta que la celda actual (current) no es un nodo.
     *
     * @param current
     * @param next
     * @param node_index	Posición dentro del array de nodos del nodo o "color" que se está resolviendo.
     * @return
     */
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