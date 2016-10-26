package flow;

import java.util.ArrayList;
import java.util.List;

public class Test {
	
	public static void main(String[] args) {
		
		List<Example> examples = new ArrayList<>();
		examples = ExampleGenerator.generateExamples(examples);
	
		int fils;
		int cols;
		int[][] matrix,FinalMatrixHC;
		
		for (int i = 0; i < examples.size(); i++) {
			fils = examples.get(i).fils;
			cols = examples.get(i).cols;
			matrix = examples.get(i).matrix;

			System.out.println("SOLUCION APROXIMADA:");
			System.out.println("Entrada del algoritmo:" + " problema: " + i);
			AlgorithmHC.printMatrix(fils, cols, matrix);
			System.out.println("");
			FinalMatrixHC = AlgorithmHC.solve(fils, cols, matrix);
			System.out.println("Salida del algoritmo:");
			AlgorithmHC.printMatrix(fils, cols, FinalMatrixHC);
			System.out.println("");


			System.out.println("SOLUCION EXACTA:");
			System.out.println("Entrada del algoritmo:" + " problema: " + i);
			Algorithm.printMatrix(fils, cols, matrix);
			System.out.println("");
			matrix = Algorithm.solve(fils, cols, matrix);
			System.out.println("Salida del algoritmo:");
			Algorithm.printMatrix(fils, cols, matrix);
			System.out.println("");
		}
	}
	
}
