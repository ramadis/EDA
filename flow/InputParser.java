package flow;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InputParser {
	
	public static void runAlgorithm(Point[][] matrix, String algorithm, boolean progress_on) {
		
	}
	
	public static int[][] parseToMatrix(String filename) throws FileNotFoundException {
		int rows;
		int cols;
		Scanner fileReader = new Scanner(new File(filename));
		String firstLine = fileReader.nextLine();
		
		cols = firstLine.charAt(0) - '0';
		rows = firstLine.charAt(2) - '0';
		
		
		int[][] matrix = new int[rows][cols];
		for(int i = 0; i < cols; i++) {
			matrix[i] = parseStringToRow(fileReader.nextLine());
		}
		fileReader.close();
		return matrix;
	}
	
	public static int[] parseStringToRow(String rowString) {
		int[] row = new int[rowString.length()];
		
		for(int i = 0; i < rowString.length(); i++) {
			char character = rowString.charAt(i); 
			if(character == ' ')
				row[i] = 0;
			else 
				row[i] = rowString.charAt(i) - '0';
		}
		
		return row;
	}
	
	public static Point[][] runAlgorithm(int[][] matrix, String method, int top_time, boolean progress_on) {
		Point[][] mat;		
			
		long start_time = System.currentTimeMillis();
		if(method.equals("exact"))
			mat = Algorithm.solve(matrix[0].length, matrix.length, matrix, progress_on);
		else 
			mat = HillClimbing.solve(matrix[0].length, matrix.length, matrix, top_time, progress_on);


		long total_time = System.currentTimeMillis() - start_time;
		System.out.println("Tiempo total de ejecucion: " + total_time + " ms");
		System.out.println("");
		return mat;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		if(args.length < 2 || args.length > 4)
			throw new IllegalArgumentException("Incorrect number of arguments, expected 2..3, received " + args.length);
		
		String filename = args[0];
		String method 	= args[1];
		int max_time = 0; 
		String param = "";
		
		
		if(! (method.equals("approx") || method.equals("exact")))
			throw new IllegalArgumentException("Incorrect method passed. Supported: approx, exact.");
		
		if(method.equals("approx")) {
			if(args.length < 3)
				throw new IllegalArgumentException("Approximated method selected, expected max time, but got something else.");
			
			max_time = Integer.parseInt(args[2]);
			if(args.length == 4)
				param = args[3];
		}
		else {
			if(args.length == 3)
				param = args[2];
		}
		
		boolean progress_on = param.equalsIgnoreCase("progress");
		
		int[][] matrix = parseToMatrix(filename);
		
		Point[][] solution = runAlgorithm(matrix, method, max_time, progress_on);
		
		SimplePrinter.setUpPrinterAndPrintStuff(solution);
	}	
}
