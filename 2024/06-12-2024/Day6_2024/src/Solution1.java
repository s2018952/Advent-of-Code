import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution1 {
    private ArrayList<String> grid;
    private ArrayList<Integer> startIndices;
    private boolean[][] visited;
    public Solution1(String file) throws IOException {
        this.grid = new ArrayList<>();
        this.startIndices = new ArrayList<>();
        Path path = Paths.get(file);
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            this.grid.add(line);
        }
        scanner.close();
        this.visited = new boolean[this.grid.size()][this.grid.get(0).length()];
        for (int i = 0; i < this.grid.size(); i++) {
            for (int j = 0; j < this.grid.get(0).length(); j++) {
                visited[i][j] = false;
                if (this.grid.get(i).charAt(j) == '^') {
                    this.startIndices.add(i);
                    this.startIndices.add(j);
                    visited[i][j] = true;
                }
            }
        }
    }

    public void getSolution() {
        populateVisited();
        int count = countVisited();
        System.out.println("Solution 1: " + count);
    }

    private int countVisited() {
        int count = 0;
        for (int i = 0; i < this.grid.size(); i++) {
            for (int j = 0; j< this.grid.get(0).length(); j++) {
                if (this.visited[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }
    private void populateVisited() {
        int currRow = this.startIndices.get(0);
        int currCol = this.startIndices.get(1);
        int vertDir = -1;
        int horDir = 0;
        while (vertDir != horDir) {
            int[] output = getNextPositionAndDirection(currRow,currCol,vertDir,horDir);
            currRow = output[0];
            currCol = output[1];
            vertDir = output[2];
            horDir = output[3];
        }
    }
    private int[] getNextPositionAndDirection(int currRow, int currCol, int vertDir, int horDir) {
        int[] output = new int[4];
        int rowEndIndex = (vertDir == 0) ? currRow : ((vertDir == 1) ? this.grid.size() - 1 : 0);
        int colEndIndex = (horDir == 0) ? currCol : ((horDir == 1) ? this.grid.get(0).length() - 1 : 0);
        int vertIterator = (vertDir == 0) ? 1 : vertDir;
        int horIterator = (horDir == 0) ? 1 : horDir;
        boolean breakLoop = false;
        for (int i = currRow; (vertIterator > 0) ? (i <= rowEndIndex) : (i >= rowEndIndex); i += vertIterator) {
            for (int j = currCol; (horIterator > 0) ? (j <= colEndIndex) : (j >= colEndIndex); j += horIterator) {
                if (i == rowEndIndex && j == colEndIndex && this.grid.get(i).charAt(j) != '#') {
                    visited[i][j] = true;
                    breakLoop = true;
                    output[0] = i;
                    output[1] = j;
                    output[2] = 0;
                    output[3] = 0;
                    break;
                }
                if (this.grid.get(i).charAt(j) == '#') {
                    output[0] = i - vertDir;
                    output[1] = j - horDir;
                    output[2] = (vertDir == 0) ? horDir : -horDir;
                    output[3] = (vertDir == 0) ? vertDir : -vertDir;
                    breakLoop = true;
                    break;
                }
                else {
                    visited[i][j] = true;
                }
            }
            if (breakLoop) {
                break;
            }
        }
        return output;
    }
}
