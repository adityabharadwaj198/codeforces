package rippling;

public class RangeSum {
    /*
     * Given a 2D matrix matrix, handle multiple queries of the following type:

    Calculate the sum of the elements of matrix inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
    Implement the NumMatrix class:

    NumMatrix(int[][] matrix) Initializes the object with the integer matrix matrix.
    int sumRegion(int row1, int col1, int row2, int col2) Returns the sum of the elements of matrix inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
    You must design an algorithm where sumRegion works on O(1) time complexity.

    Input
    ["NumMatrix", "sumRegion", "sumRegion", "sumRegion"]
    [[[[3, 0, 1, 4, 2], [5, 6, 3, 2, 1], [1, 2, 0, 1, 5], [4, 1, 0, 1, 7], [1, 0, 3, 0, 5]]], [2, 1, 4, 3], [1, 1, 2, 2], [1, 2, 2, 4]]
    Output
    [null, 8, 11, 12]
    Explanation:

    NumMatrix numMatrix = new NumMatrix([[3, 0, 1, 4, 2], [5, 6, 3, 2, 1], [1, 2, 0, 1, 5], [4, 1, 0, 1, 7], [1, 0, 3, 0, 5]]);
    numMatrix.sumRegion(2, 1, 4, 3); // return 8 (i.e sum of the red rectangle)
    numMatrix.sumRegion(1, 1, 2, 2); // return 11 (i.e sum of the green rectangle)
    numMatrix.sumRegion(1, 2, 2, 4); // return 12 (i.e sum of the blue rectangle)
     */

     class NumMatrix {

        int[][] prefixMat;
        public NumMatrix(int[][] matrix) {
            prefixMat = new int[matrix.length+1][matrix[0].length+1];
            int row = matrix.length;
            int col = matrix[0].length;
            for (int i=1; i<=row; i++) {
                for (int j=1; j<=col; j++) {
                    prefixMat[i][j] = matrix[i-1][j-1] + prefixMat[i-1][j] + prefixMat[i][j-1] - prefixMat[i-1][j-1];
                }
            }
        }
        
        public int sumRegion(int row1, int col1, int row2, int col2) {
            return prefixMat[row2+1][col2+1] - prefixMat[row2+1][col1] - prefixMat[row1][col2+1] + prefixMat[row1][col1];
        }
    }
    
    /**
     * Your NumMatrix object will be instantiated and called as such:
     * NumMatrix obj = new NumMatrix(matrix);
     * int param_1 = obj.sumRegion(row1,col1,row2,col2);
     */
}
