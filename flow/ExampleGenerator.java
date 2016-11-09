package flow;

import java.util.List;

public class ExampleGenerator {

	static int fils0 = 6;
	static int cols0 = 6;
	static int[][] matrix0 =
			{		{2,1,0,0,0,0},
			 		{0,0,3,0,4,0},
			 		{0,0,5,0,0,4},
					{0,0,0,0,0,3},
					{0,0,1,0,0,5},
			 		{0,0,0,0,0,2}};
	
	static int fils1 = 8;
	static int cols1 = 8;
	static int[][] matrix1 = {{1,2,0,0,0,0,0,0},
							{0,3,0,0,0,0,4,0},
							{0,0,0,0,0,5,0,0},
							{6,3,0,0,0,4,0,0},
							{0,0,1,0,0,7,0,5},
							{0,0,0,0,0,2,0,0},
							{0,0,0,6,0,0,8,0},
							{8,0,0,0,0,7,0,0}};
	
	static int fils2 = 8;
	static int cols2 = 8;
	static int[][] matrix2 = {{0,0,0,0,0,0,0,1},
							{0,1,0,0,0,0,0,0},
							{0,0,0,0,2,0,0,0},
							{0,2,3,0,0,0,0,0},
							{0,0,0,4,0,0,0,0},
							{0,0,5,0,0,0,0,0},
							{0,0,0,3,5,0,6,0},
							{6,4,0,0,0,0,0,0}};
	
	static int fils3 = 8;
	static int cols3 = 8;
	static int[][] matrix3 = {{1,0,0,0,0,0,0,0},
							{0,2,3,0,0,0,0,0},
							{0,4,0,0,5,6,0,0},
							{0,0,0,0,0,0,0,0},
							{0,7,0,0,5,6,0,0},
							{0,8,2,0,0,0,0,0},
							{0,0,0,0,8,4,3,1},
							{0,0,0,0,0,0,0,7}};
	
	static int fils4 = 8;
	static int cols4 = 8;
	static int[][] matrix4 = {{0,0,0,0,1,2,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,3,0,0,3,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,4,5,0,0,0},
							{0,0,0,0,0,0,5,0},
							{0,4,2,0,0,0,0,0},
							{0,1,6,0,0,0,0,6}};
	
	static int fils5 = 8;
	static int cols5 = 8;
	static int[][] matrix5 = {{1,0,0,0,0,0,0,2},
							{3,0,0,0,0,0,0,0},
							{4,0,0,0,0,0,0,0},
							{1,0,0,3,0,0,0,0},
							{0,0,4,0,0,0,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0},
							{2,0,0,0,0,0,0,0}};
	
	static int fils6 = 8;
	static int cols6 = 8;
	static int[][] matrix6 = {{0,1,0,0,0,0,0,0},
							{0,0,0,2,0,0,2,0},
							{0,0,3,0,0,0,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,1,4,0,0,5},
							{0,0,0,6,0,0,0,3},
							{0,4,0,0,0,0,0,0},
							{0,0,0,0,0,0,6,5}};
	
	static int fils7 = 8;
	static int cols7 = 8;
	static int[][] matrix7 = {{0,0,0,0,0,0,0,0},
							{0,1,2,0,0,0,3,0},
							{0,0,4,0,0,0,0,0},
							{4,0,0,0,0,5,0,0},
							{1,0,0,2,0,6,0,0},
							{5,6,0,0,0,0,0,0},
							{0,0,0,0,0,0,3,0},
							{0,0,0,0,0,0,0,0}};
	
	static int fils8 = 8;
	static int cols8 = 8;
	static int[][] matrix8 = {{0,1,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,2,3,0,0},
							{0,0,0,0,0,0,4,0},
							{0,0,0,2,5,0,0,0},
							{0,0,0,0,0,0,0,5},
							{0,0,3,0,0,6,0,6},
							{0,0,0,1,0,0,0,4}};
	
	static int fils9 = 9;
	static int cols9 = 9;
	static int[][] matrix9 = {{1,0,0,0,0,0,0,0,0},
							{2,0,0,0,0,3,0,3,1},
							{0,0,0,4,0,0,0,0,5},
							{0,0,0,0,0,5,0,0,0},
							{0,0,0,0,0,0,0,6,0},
							{0,0,7,0,0,0,0,0,0},
							{0,0,8,0,7,0,8,6,0},
							{0,0,0,0,2,0,0,0,0},
							{0,0,0,0,0,0,0,0,4}};
	
	static int fils10 = 9;
	static int cols10 = 9;
	static int[][] matrix10 = {{0,0,0,0,0,0,1,2,3},
							{0,4,0,0,0,0,0,0,0},
							{5,0,0,1,0,0,0,0,0},
							{0,0,0,2,0,0,0,0,0},
							{0,3,0,0,0,0,0,6,0},
							{0,0,0,0,7,0,0,0,0},
							{0,0,0,0,0,0,8,0,8},
							{0,0,0,5,0,0,7,6,9},
							{4,0,0,0,0,9,0,0,0}};
	
	static int fils11 = 12;
	static int cols11 = 12;
	static int[][] matrix11 = {{0,0,0,0,0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0,1,0,0,0},
							{0,0,0,0,0,0,0,0,0,0,0,0},
							{0,0,0,0,0,2,0,0,3,0,0,0},
							{0,0,0,0,4,5,6,4,0,0,0,0},
							{0,0,0,0,0,0,0,0,3,0,0,0},
							{0,0,0,0,0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0,0,0,0,0},
							{0,0,0,0,2,0,7,0,0,0,0,0},
							{0,0,0,0,0,0,0,0,0,0,0,5},
							{0,0,0,0,0,0,0,0,0,0,1,7},
							{0,0,0,0,0,0,0,0,6,0,0,0}};
	
	static int fils12 = 6;
	static int cols12 = 6;
	static int[][] matrix12 =
			{		{0,1,0,0,0,0},
					{0,0,0,0,1,0},
					{0,0,2,0,0,0},
					{0,0,0,0,0,0},
					{0,0,0,0,0,0},
					{0,0,0,0,0,2}};
	
	public static List<Example> generateExamples(List<Example> examples) {

		examples.add(new Example(fils0, cols0, matrix0));
		//examples.add(new Example(fils1, cols1, matrix1));
		//examples.add(new Example(fils2, cols2, matrix2));
		//examples.add(new Example(fils3, cols3, matrix3));
		//examples.add(new Example(fils4, cols4, matrix4));
		//examples.add(new Example(fils5, cols5, matrix5));
		//examples.add(new Example(fils6, cols6, matrix6));
		//examples.add(new Example(fils7, cols7, matrix7));
		//examples.add(new Example(fils8, cols8, matrix8));
		//examples.add(new Example(fils9, cols9, matrix9));
		//examples.add(new Example(fils10, cols10, matrix10));
		//examples.add(new Example(fils11, cols11, matrix11));
		//examples.add(new Example(fils12, cols12, matrix12));
		
		return examples;
		
	}
	
}
