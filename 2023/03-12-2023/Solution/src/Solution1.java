import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution1 {
    private int output;
    private char[][] grid;
    private int num_rows;
    private int num_cols;
    private ArrayList<String> lines;

    public Solution1(String filepath) throws IOException {
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
        boolean[][] valids = new boolean[num_rows][num_cols];
        ArrayList<Integer[]> validIndices = new ArrayList<>();
        for (int i = 0; i < num_rows; i++) {
            for (int j = 0; j < num_cols; j++) {
                valids[i][j] = checkChar(i,j);
                if (valids[i][j]) {
                    Integer[] indices = new Integer[2];
                    indices[0] = i;
                    indices[1] = j;
                    validIndices.add(indices);
                }
            }
        }

        for (Integer[] indices : validIndices) {
            int row = indices[0];
            int col = indices[1];
            for (int j = Math.max(col - 1, 0); j >= 0; j--) {
                if (!Character.isDigit(grid[row][j])) {
                    break;
                }
                valids[row][j] = true;
            }
            for (int j = Math.min(col + 1, this.num_cols - 1); j < this.num_cols; j++) {
                if (!Character.isDigit(grid[row][j])) {
                    break;
                }
                valids[row][j] = true;
            }
        }

        ArrayList<String> validNumStrings = new ArrayList<>();
        for (int i = 0; i < this.num_rows; i++) {
            StringBuilder s = new StringBuilder();
            for (int j = 0; j < this.num_cols; j++) {
                if ((!s.isEmpty()) && !valids[i][j]) {
                    validNumStrings.add(s.toString());
                    s = new StringBuilder();
                }
                else if (j == this.num_cols - 1 && valids[i][j]) {
                    s.append(grid[i][j]);
                    validNumStrings.add(s.toString());
                    s = new StringBuilder();
                }
                else if (valids[i][j]) {
                    s.append(grid[i][j]);
                }
            }
        }
        for (String num : validNumStrings) {
            output += Integer.parseInt(num);
        }
    }

    private boolean checkChar(int row, int col) {
        char c = grid[row][col];
        if (!Character.isDigit(c)) {
            return false;
        }
        for (int i = Math.max(row - 1, 0); i < Math.min(row + 2, this.num_rows); i++) {
            for (int j = Math.max(col - 1, 0); j < Math.min(col + 2, this.num_cols); j++) {
                if (i == row && j == col) {
                    continue;
                }
                if (!Character.isDigit(grid[i][j]) && grid[i][j] != '.') {
                    return true;
                }
            }
        }
        return false;
    }
}
