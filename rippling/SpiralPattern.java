package rippling;
/*
 * Given an integer N, print the symbol * in spiral order such that there will be Ns * in a line.

A spiral order is defined as follows:

Start: Begin at a given position as the center.
Go down: Go 1 step downwards.
Go right: Turn right and move 2 steps.
Go up: Go Up and move 3 steps.
Go left: Turn right and move 4 steps.
Repeat: Continue this pattern of down, right, up, and left, increasing 1 step at each move.
Note you can choose the order to move (like up, right, down, and left), the fixed number of steps for a single move, and the number of steps for the first move starting at the center. But they will be fixed once defined.

Example:

N = 5
steps = 1
initial steps = 1

Start:

                 
           
                
         *     
            
                 

One spiral (down, right, up, and left):

                 
       * * * *   
             *   
         *   *   
         * * *   
                 
     

Two spirals:
 * * * * * * * * *
                 *
     * * * * *   *
     *       *   *
     *   *   *   *
     *   * * *   *
     *           *
     * * * * * * *
Don't see w
 */
public class SpiralPattern {

    public static void printSpiral(int n) {
        int size = 2 * n + 1;
        char[][] grid = new char[size][size];

        // Fill with spaces
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = ' ';
            }
        }

        int top = 0, bottom = size - 1, left = 0, right = size - 1;
        int count = 0;
        while (top <= bottom && left <= right) {
            // Top row
            for (int i = left; i <= right; i++) grid[top][i] = '*';
            top++;

            // Right column
            for (int i = top; i <= bottom; i++) grid[i][right] = '*';
            right--;

            // Bottom row
            for (int i = right; i >= left; i--) grid[bottom][i] = '*';
            bottom--;

            // Left column
            for (int i = bottom; i >= top; i--) grid[i][left] = '*';
            left++;
        }

        // Print the pattern
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        printSpiral(4); // Try changing n for bigger/smaller spirals
    }
}
