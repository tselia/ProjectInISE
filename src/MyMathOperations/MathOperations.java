package MyMathOperations;
import org.junit.jupiter.api.Test;

import java.lang.Math;

/**
 * a class to make the counts we need in this project
 * Authors - Polina Frolov Korogodsky and Tselia Tevol
 */
public class MathOperations {
    /** function to get the inverse matrix
     * The code's source: https://www.sanfoundry.com/java-program-solve-linear-equation/
     * @param a (matrix)
     * @return
     */
    public static double[][] invert(double a[][])

    {

        int n = a.length;

        double x[][] = new double[n][n];

        double b[][] = new double[n][n];

        int index[] = new int[n];

        for (int i=0; i<n; ++i)

            b[i][i] = 1;



        // Transform the matrix into an upper triangle

        gaussian(a, index);



        // Update the matrix b[i][j] with the ratios stored

        for (int i=0; i<n-1; ++i)

            for (int j=i+1; j<n; ++j)

                for (int k=0; k<n; ++k)

                    b[index[j]][k]

                            -= a[index[j]][i]*b[index[i]][k];



        // Perform backward substitutions

        for (int i=0; i<n; ++i)

        {

            x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];

            for (int j=n-2; j>=0; --j)

            {

                x[j][i] = b[index[j]][i];

                for (int k=j+1; k<n; ++k)

                {

                    x[j][i] -= a[index[j]][k]*x[k][i];

                }

                x[j][i] /= a[index[j]][j];

            }

        }

        return x;

    }

    /**
     * Makes Gaussian matrix
     * code's source: https://www.sanfoundry.com/java-program-solve-linear-equation/
     * @param a (matrix)
     * @param index
     */
    public static void gaussian(double a[][], int index[])

    {

        int n = index.length;

        double c[] = new double[n];



        // Initialize the index

        for (int i=0; i<n; ++i)

            index[i] = i;



        // Find the rescaling factors, one from each row

        for (int i=0; i<n; ++i)

        {

            double c1 = 0;

            for (int j=0; j<n; ++j)

            {

                double c0 = Math.abs(a[i][j]);

                if (c0 > c1) c1 = c0;

            }

            c[i] = c1;

        }



        // Search the pivoting element from each column

        int k = 0;

        for (int j=0; j<n-1; ++j)

        {

            double pi1 = 0;

            for (int i=j; i<n; ++i)

            {

                double pi0 = Math.abs(a[index[i]][j]);

                pi0 /= c[index[i]];

                if (pi0 > pi1)

                {

                    pi1 = pi0;

                    k = i;

                }

            }



            // Interchange rows according to the pivoting order

            int itmp = index[j];

            index[j] = index[k];

            index[k] = itmp;

            for (int i=j+1; i<n; ++i)

            {

                double pj = a[index[i]][j]/a[index[j]][j];



                // Record pivoting ratios below the diagonal

                a[index[i]][j] = pj;



                // Modify other elements accordingly

                for (int l=j+1; l<n; ++l)

                    a[index[i]][l] -= pj*a[index[j]][l];

            }

        }


    }

    /**
     * A method to solve system of linear equations
     * Code's source: https://www.sanfoundry.com/java-program-solve-linear-equation/
     * Changes by Polina Frolov Korogodsky and Tselia Tevol
     * @param matrix
     * @return
     */
    public static double[][] solveEquationSystem(double [][] matrix){
        double[][]mat = new double[3][3];
        for (int i=0; i<3; i++){
            for(int j = 0; j<3; j++){
                mat[i][j]=matrix[i][j];}}
        System.out.println(mat);
        int n=3;
        double [][]constants = new double[3][1];
        constants[0][0] = matrix[0][3];
        constants[1][0] = matrix[1][3];
        constants[2][0] = matrix[2][3];

        double inverted_mat[][] = invert(mat);
        double result[][] = new double[n][1];

        for (int i = 0; i < n; i++)

        {

            for (int j = 0; j < 1; j++)

            {

                for (int k = 0; k < n; k++)

                {

                    result[i][j] += inverted_mat[i][k] * constants[k][j];

                }

            }

        }
        return result;
      /*  int columns = 4;
        int rows = 3;
        for(int i=0; i<4; i++)//making the coefficient of the [0][0] = 1
            matrix[0][i]/=matrix[0][0];
        for(int i=0; i<4; i++){
            matrix[1][i]-=(matrix[0][i]*matrix[1][0]);
            matrix[2][i]-=(matrix[0][i]*matrix[2][0]); //making all the first column except for the first zeroes
        }
        System.out.println(matrix);
        for(int i=1; i<columns; i++){
            matrix[1][i]/=matrix[1][1]; // transform the second line to 0 1 x y
            matrix[2][i]-=(matrix[1][i]*matrix[2][1]); //the third line'll look like   0 0 w z
        }
        System.out.println(matrix);
        for(int i=2; i<columns; i++){
            matrix[2][i]/=matrix[2][2];
            matrix[1][i]-=(matrix[2][i]*matrix[1][2]);
            matrix[0][i]-=(matrix[2][i]*matrix[0][2]);
        }
        System.out.println(matrix);
        for(int i=1; i<columns; i++){
            matrix[0][i]-=(matrix[1][i]*matrix[0][1]);
        }
        System.out.println(matrix);
        double [] ans = new double[3];
        for (int i=0; i<3; i++)
        {
            ans[i]=matrix[i][3];
        }
        return ans;*/

    }

    /**
     * The function to multiply two matrices
     * Code source: https://www.programiz.com/java-programming/examples/multiply-matrix-function
     * Changes by Polina Frolov Korogodsky and Tselia Tebol
     * @param firstMatrix
     * @param secondMatrix
     * @param r1 number of rows of first matrix
     * @param c1 num of columns in the first matrix
     * @param c2 bum of columns of the second matrix
     * @return double[][]
     */
    public static double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix, int r1, int c1, int c2)
    {
        double [][] product = new double[r1][c2];
        for(int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                for (int k = 0; k < c1; k++) {
                    product[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }

        return product;
    }

    /**
     * The method finds the root(roots) of quadrant equation
     * Code source: https://www.w3resource.com/java-exercises/conditional-statement/java-conditional-statement-exercise-2.php
     * Code changes by Polina Frolov Korogodsly and Tselia Tebol
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static double[] solveQuadrantEquation(double a, double b, double c){

        double result = b * b - 4.0 * a * c;

        if (result > 0.0) {
            double r1 = (-b + Math.pow(result, 0.5)) / (2.0 * a);
            double r2 = (-b - Math.pow(result, 0.5)) / (2.0 * a);
            double [] answers = new double[]{r1, r2};
            return answers;

        } else if (result == 0.0) {
            double r1 = -b / (2.0 * a);
            double [] answers = new double[]{r1};
            return answers;
        } else {
           return null;
        }

    }
    @Test
    public void inverse(){
        double[][] matrix= new double[3][3];
        matrix[0] = new double[]{1, 2, 0};
        matrix[1] = new double[]{5, 0, 7};
        matrix[2] = new double[]{1, 0, 0};
        double[][] inversedMatrix = MathOperations.invert(matrix);
        double[][] simpleMatrix = MathOperations.multiplyMatrices(matrix, inversedMatrix, 3, 3, 3);
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                System.out.println(simpleMatrix[i][j]);
}
    }

