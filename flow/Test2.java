package flow;

        import com.sun.xml.internal.bind.v2.model.annotation.Quick;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by joaquin on 26/10/16.
 */
public class Test2 {

    public static void main(String[] args) {

        List<Example> examples = new ArrayList<>();
        examples = ExampleGenerator.generateExamples(examples);

        int fils;
        int cols;
        int[][] matrix,FinalMatrix;
        Point[][] solution;

        for (int i = 0; i < examples.size(); i++) {
            fils = examples.get(i).fils;
            cols = examples.get(i).cols;
            matrix = examples.get(i).matrix;
            /* AlgorithmHC, último parámetro de AlgorithmHC es el tiempo, en ms */
            System.out.println("SOLUCION APROXIMADA:");
            System.out.println("Entrada del algoritmo:" + " problema: " + i);
            AlgorithmHC.printMatrix(fils, cols, matrix);
            System.out.println("");
            FinalMatrix = AlgorithmHC.solve(fils, cols, matrix, 1000);
            System.out.println("Salida del algoritmo:");
            AlgorithmHC.printMatrix(fils, cols, FinalMatrix);
            System.out.println("");


            /* QuickSolution
            QuickSolution.printMatrix(fils, cols, matrix);
            System.out.println("");
            FinalMatrix = QuickSolution.solve(fils, cols, matrix,1);
            QuickSolution.printMatrix(fils, cols, FinalMatrix);
            System.out.println("");

            /* Algorithm
            Algorithm.printMatrix(fils, cols, matrix);
            System.out.println("");
            FinalMatrix = Algorithm.solve(fils, cols, matrix);
            Algorithm.printMatrix(fils, cols, FinalMatrix);
            System.out.println("");*/


        }
    }

}
