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
		/*
		for (int i = 0; i < examples.size(); i++) {
			fils = examples.get(i).fils;
			cols = examples.get(i).cols;
			matrix = examples.get(i).matrix;
			
			System.out.println("Entrada del algoritmo2:" + " problema: " + i);
			Algorithm.printMatrix(fils, cols, matrix);
			System.out.println("");
			
			long start_time = System.currentTimeMillis();
			solution = Algorithm.solve(fils, cols, matrix, true);
			long total_time = System.currentTimeMillis() - start_time;
			
			System.out.println("Tiempo total de ejecucion: " + total_time + " ms");
			System.out.println("");

			SimplePrinter.setUpPrinterAndPrintStuff(solution);
		}
		*/
		
		for (int i = 0; i < examples.size(); i++) {
			fils = examples.get(i).fils;
			cols = examples.get(i).cols;
			matrix = examples.get(i).matrix;
			
			System.out.println("Entrada del Hill Climbing:" + " problema: " + i);
			Algorithm.printMatrix(fils, cols, matrix);
			System.out.println("");
			
			long start_time = System.currentTimeMillis();
			solution = HillClimbing.solve(fils, cols, matrix, 15000, true);
			long total_time = System.currentTimeMillis() - start_time;
			
			System.out.println("Tiempo total de ejecucion: " + total_time + " ms");
			System.out.println("");

			SimplePrinter.setUpPrinterAndPrintStuff(solution);
		}
		
	}
	
}
