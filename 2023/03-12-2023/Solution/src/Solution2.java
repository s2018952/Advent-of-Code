import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution2 {
    private int output;
    private char[][] grid;
    private int num_rows;
    private int num_cols;
    private ArrayList<String> lines;

    public Solution2(String filepath) throws IOException {
        this.output = 0;
        Path path = Paths.get(filepath);
        Scanner scanner = new Scanner(path);
        this.num_rows = 0;
        this.lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            this.lines.add(line);
            this.num_rows++;
            this.num_cols = line.length();
        }
        scanner.close();
        this.grid = new char[this.num_rows][this.num_cols];
        for (int i = 0; i < num_rows; i++) {
            for (int j = 0; j < num_cols; j++) {
                grid[i][j] = lines.get(i).charAt(j);
            }
        }
    }

    public void printOutput() {
        this.computeOutput();
        System.out.println(output);
    }

    private void computeOutput() {
        ArrayList<Integer[]> numberIndices = findNumberIndices();
        ArrayList<Integer[]> starIndices = findStars();
        ArrayList<Integer[]> goodStars = new ArrayList<>();
        for (Integer[] starIndex : starIndices) {
            int count = 0;
            for (Integer[] numIndex : numberIndices) {
                if (checkStarAdjacent(starIndex[0],starIndex[1],numIndex)) {
                    count++;
                }
            }
            if (count == 2) {
                goodStars.add(starIndex);
            }
        }
        for (Integer[] starIndex : goodStars) {
            int product = 1;
            for (Integer[] numIndex : numberIndices) {
                if (checkStarAdjacent(starIndex[0],starIndex[1],numIndex)) {
                    StringBuilder s = new StringBuilder();
                    for (int j = numIndex[1]; j <= numIndex[2]; j++) {
                        s.append(grid[numIndex[0]][j]);
                    }
                    product *= Integer.parseInt(s.toString());
                }
            }
            output += product;
        }
    }

    private boolean checkStarAdjacent(int row, int col, Integer[] index) {
        if (index[0] < row - 1 || index[0] > row + 1) {
            return false;
        }
        else if (index[2] < col - 1 || index[1] > col + 1) {
            return false;
        }
        return true;
    }

    private ArrayList<Integer[]> findStars() {
        ArrayList<Integer[]> starIndices = new ArrayList<>();
        for (int i = 0; i < this.num_rows; i++) {
            for (int j = 0; j < this.num_cols; j++) {
                if (grid[i][j] == '*') {
                    Integer[] index = new Integer[2];
                    index[0] = i;
                    index[1] = j;
                    starIndices.add(index);
                }
            }
        }
        return starIndices;
    }

    private ArrayList<Integer[]> findNumberIndices() {
        ArrayList<Integer[]> numberIndices = new ArrayList<>();
        for (int i = 0; i < num_rows; i++) {
            int previous = -1;
            for (int j = 0; j < num_cols; j++) {
                if (previous != -1 && !Character.isDigit(grid[i][j])) {
                    Integer[] index = new Integer[3];
                    index[0] = i;
                    index[1] = previous;
                    index[2] = j - 1;
                    numberIndices.add(index);
                    previous = -1;
                }
                else if (previous != -1 && Character.isDigit(grid[i][j]) && j == this.num_cols - 1) {
                    Integer[] index = new Integer[3];
                    index[0] = i;
                    index[1] = previous;
                    index[2] = j;
                    numberIndices.add(index);
                    previous = -1;
                }
                else if (previous == -1 && Character.isDigit(grid[i][j])) {
                    previous = j;
                }
            }
        }
        return numberIndices;
    }
}
