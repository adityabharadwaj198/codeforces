package rippling;

    import java.util.*;

class Rectangle {
    int id;
    int row, col;
    int height, width;
    char symbol;

    public Rectangle(int id, int row, int col, int height, int width, char symbol) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.height = height;
        this.width = width;
        this.symbol = symbol;
    }
}

public class canvasDrawing {
    private final int m, n;
    private char[][] canvas;
    private final List<Rectangle> rectangles;
    private int nextId;

    public canvasDrawing(int m, int n) {
        this.m = m;
        this.n = n;
        this.canvas = new char[m][n];
        for (char[] row : canvas) Arrays.fill(row, '0');
        this.rectangles = new ArrayList<>();
        this.nextId = 1;
    }

    public int addRectangle(int row, int col, int height, int width, char symbol) {
        if (!isInBounds(row, col, height, width)) {
            System.out.println("Rectangle out of bounds, cannot add.");
            return -1;
        }
        Rectangle rect = new Rectangle(nextId++, row, col, height, width, symbol);
        rectangles.add(rect);
        redrawCanvas();
        return rect.id;
    }

    public void moveRectangle(int id, int newRow, int newCol) {
        for (Rectangle rect : rectangles) {
            if (rect.id == id) {
                if (!isInBounds(newRow, newCol, rect.height, rect.width)) {
                    System.out.println("Move out of bounds, aborting.");
                    return;
                }
                rect.row = newRow;
                rect.col = newCol;
                redrawCanvas();
                return;
            }
        }
        System.out.println("Rectangle ID not found.");
    }

    private boolean isInBounds(int row, int col, int height, int width) {
        return row >= 0 && col >= 0 && row + height <= m && col + width <= n;
    }

    private void redrawCanvas() {
        for (char[] row : canvas) Arrays.fill(row, '0');
        for (Rectangle rect : rectangles) {
            for (int i = 0; i < rect.height; i++) {
                for (int j = 0; j < rect.width; j++) {
                    canvas[rect.row + i][rect.col + j] = rect.symbol;
                }
            }
        }
    }

    public void printCanvas() {
        for (char[] row : canvas) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        canvasDrawing manager = new canvasDrawing(4, 3);
        manager.printCanvas();

        int id1 = manager.addRectangle(0, 0, 2, 2, 'a');
        manager.printCanvas();

        int id2 = manager.addRectangle(0, 1, 2, 2, 'b');
        manager.printCanvas();

        manager.moveRectangle(id2, 0, 2); // move b one step to the right
        manager.printCanvas();
    }
}
