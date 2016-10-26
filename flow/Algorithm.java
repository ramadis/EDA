package flow;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Martin
 *
 */

public class Algorithm {
	
	/**
	 * Variables estáticas para tener a mano ciertos valores, para no tener que estar pasandolos
	 * entre llamadas de funciones dentro del algoritmo.
	 */
	
	/**
	 * Filas y columnas de la matriz.
	 */
	private static int FILS;
	private static int COLS;
	
	/**
	 * Lista de los puntos de origen de cada "color". Se considera como punto de origen a aquel
	 * que primero se halle en la matriz.
	 */
	private static List<Point> nodes;
	
	/**
	 * Almacena la mejor solución encontrada hasta el momento.
	 */
	private static Solution solution;
	
	/**
	 * Contiene las posibles direcciones desde un punto de la matriz (arriba, derecha, abajo, izquierda),
	 * en ese orden.
	 */
	private static int[][] directions = {{-1,0},{0,1},{1,0},{0,-1}};
	
	/**
	 * Almacena el valor del nodo más alto de la matriz.
	 */
	private static int HIGHEST_VALUE;
	
	private static int[][] MATRIX;
	
	/**
	 * Método llamado por el usuario para obtener la solución del problema.
	 * Inicializa las variables estáticas para que puedan ser usadas por el algoritmo.
	 */
	public static int[][] solve(int fils, int cols, int[][] matrix) {
		FILS = fils;
		COLS = cols;
		nodes = new ArrayList<>();
		HIGHEST_VALUE = 0;
		MATRIX = matrix;
		
		List<Integer> values = new ArrayList<>();
		
		for (int i = 0; i < fils; i++) {
			for (int j = 0; j < cols; j++) {
				int value = MATRIX[i][j];
				if (value != 0 && !values.contains(value)) {
					nodes.add(new Point(i, j));
					values.add(value);
					if (value > HIGHEST_VALUE)
						HIGHEST_VALUE = value;
				}	
			}
		}
		
		// Llama al algoritmo pasandole el primer nodo y su posición dentro del array de nodos.
		if (findPathPoint(0, nodes.get(0)))
			return MATRIX;
		return solution.matrix;
	}
	
	/**
	 * Algoritmo que halla los caminos utilizando el método de backtracking y llamandose recursivamente.
	 * 
	 * @param node_index
	 * @param current
	 * @return				true, si se halló una solución que cubre todas las casillas, false en caso contrario.
	 */
	private static boolean findPathPoint(int node_index, Point current) {
		boolean isNode = false;
		if (nodes.contains(current)) {
			isNode = true;
		}
		Point next;
		int nextFil;
		int nextCol;
		
		for (int i = 0; i < 4; i++) {
			nextFil = current.fil + directions[i][0];
			nextCol = current.col + directions[i][1];
			next = new Point(nextFil, nextCol);
			if (!isOutOfBounds(next)) {
				boolean isEnd = false;
				if (isNode && isEndFromNode(current, next))
					isEnd = true;
				else if (!isNode && isEndFromPoint(current, next, node_index))
					isEnd = true;
				if (isEnd) {
					if (MATRIX[next.fil][next.col] == HIGHEST_VALUE) {
						int emptyCells = getEmptyCells();
						if (emptyCells == 0) {
							solution = new Solution(emptyCells, MATRIX);
							return true;
						}
						Solution newSol = new Solution(emptyCells, MATRIX);
						if (solution == null || newSol.freeCellCount < solution.freeCellCount)
							solution = newSol;
					}
					if (node_index + 1 != nodes.size() && findPathPoint(node_index + 1, nodes.get(node_index + 1))) {
						return true;
					}
				}
				if (isFree(next)) {
					int factor = 1;
					if (isNode)
						factor = 10;
					MATRIX[next.fil][next.col] = MATRIX[current.fil][current.col] * factor;
					if (findPathPoint(node_index, next)) {
						return true;
					}
				}
				
			}
		}
		if (!isNode)
			MATRIX[current.fil][current.col] = 0;
		return false;
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
	private static boolean isFree(Point p) {
		if (MATRIX[p.fil][p.col] != 0)
			return false;
		return true;
	}
	
	/**
	 * 
	 * @return 		Cantidad de celdas vacias que tiene la matriz estática en el momento del llamado.
	 */
	private static int getEmptyCells() {
		int resp = 0;
		for (int i = 0; i < FILS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (MATRIX[i][j] == 0)
					resp ++;
			}
		}
		return resp;
	}
	
	/**
	 * Checkea si el siguiente punto es el final del nodo que se está analizando,
	 * cuando la celda actual es el nodo de origen.
	 * 
	 * @param current		Celda actual.
	 * @param next			Celda a analizar.
	 * @return				true, si next es el nodo final de ese "color", false en caso contrario.
	 */
	private static boolean isEndFromNode(Point current, Point next) {
		if (MATRIX[current.fil][current.col] == MATRIX[next.fil][next.col]) {
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
	private static boolean isEndFromPoint(Point current, Point next, int node_index) {
		if ((MATRIX[next.fil][next.col] == (MATRIX[current.fil][current.col] / 10)) 
				&& !next.equals(nodes.get(node_index)))
			return true;
		return false;
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
