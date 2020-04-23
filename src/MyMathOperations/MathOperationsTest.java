package MyMathOperations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathOperationsTest {
    @Test
            void testSolution()

    {
        MathOperations math = new MathOperations();


        double[] fr = new double[]{3, 2, -1, 5};
        double[] sr = new double[]{4, -7, 2, 7};
        double[] thr = new double[]{1, 1, -1, 5};
        double matrix[][] = new double[][]{fr, sr, thr};

        double[][] solution = math.solveEquationSystem(matrix);
        assertTrue(solution.length==3);
        assertEquals(solution[0][0], 17/(double)16);
        assertEquals(solution[1][0], -17/(double)8);
        assertEquals(solution[2][0],-97/(double)16);
    }
    @Test
    void mult() {
        double[][] matrix = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = 1;
            }
        }
        double[][]vector = new double[3][1];
        for(int i=0; i<3; i++)
            vector[i][0] = i;
        assertEquals(MathOperations.multiplyMatrices(matrix, vector, 3, 3, 1)[0][0], 3);
        assertEquals(MathOperations.multiplyMatrices(matrix, vector, 3, 3, 1)[1][0], 3);
        assertEquals(MathOperations.multiplyMatrices(matrix, vector, 3, 3, 1)[2][0], 3);
    }
    @Test
    void quadraticEquation(){
        assertTrue(MathOperations.solveQuadrantEquation(1, 4, -21)[0]==-7||MathOperations.solveQuadrantEquation(1, 4, -21)[0]==3);
        assertTrue(MathOperations.solveQuadrantEquation(1, 4, -21)[1]==-7||MathOperations.solveQuadrantEquation(1, 4, -21)[1]==3);
        assertTrue(MathOperations.solveQuadrantEquation(1, 4, -21)[0]!=MathOperations.solveQuadrantEquation(1, 4, -21)[1]);
    }
}