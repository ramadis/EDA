package flow;

import java.util.ArrayList;
import java.util.List;

public class Test {
	
	public static void main(String[] args) {
		
		List<Example> examples = new ArrayList<>();
		examples = ExampleGenerator.generateExamples(examples);
	
		int fils;
		int cols;
		int[][] matrix;
		Point[][] solution;
		
		fils = examples.get(0).fils;
		cols = examples.get(0).cols;
		matrix = examples.get(0).matrix;
		
		System.out.println("Entrada del hill climbing: " + "problema: 0");
		Algorithm.printMatrix(fils, cols, matrix);
		System.out.println("");
		
		long total_time = 100000;
		solution = HillClimbing.solve(fils, cols, matrix, total_time);
		System.out.println("Salida del hill climbing:");
		Algorithm2.printMatrix(fils, cols, solution);
		System.out.println("");
		
		/*
		for (int i = 0; i < examples.size(); i++) {
			fils = examples.get(i).fils;
			cols = examples.get(i).cols;
			matrix = examples.get(i).matrix;
			
			System.out.println("Entrada del algoritmo2:" + " problema: " + i);
			//Algorithm.printMatrix(fils, cols, matrix);
			System.out.println("");
			
			long start_time = System.currentTimeMillis();
			solution = Algorithm2.solve(fils, cols, matrix);
			long total_time = System.currentTimeMillis() - start_time;
			
			System.out.println("Salida del algoritmo2:");
			//Algorithm2.printMatrix(fils, cols, solution);
			System.out.println("");
			
			System.out.println("Tiempo total de ejecuci�n: " + total_time);
			System.out.println("");
		}
		
		for (int i = 0; i < examples.size(); i++) {
			fils = examples.get(i).fils;
			cols = examples.get(i).cols;
			matrix = examples.get(i).matrix;
			
			System.out.println("Entrada del algoritmo1:" + " problema: " + i);
			//Algorithm.printMatrix(fils, cols, matrix);
			System.out.println("");
			
			long start_time = System.currentTimeMillis();
			matrix = Algorithm.solve(fils, cols, matrix);
			long total_time = System.currentTimeMillis() - start_time;
			
			System.out.println("Salida del algoritmo1:");
			//Algorithm.printMatrix(fils, cols, matrix);
			System.out.println("");
			
			System.out.println("Tiempo total de ejecuci�n: " + total_time);
			System.out.println("");
		}
		*/
	}
	
}
