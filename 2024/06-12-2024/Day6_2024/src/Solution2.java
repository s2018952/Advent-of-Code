import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution2 {
    private ArrayList<ArrayList<Character>> grid;
    private ArrayList<Integer> startIndices;
    private boolean[][] visited;
    private boolean[][] potentialObstacles;
    private ArrayList<int[]> path;
    public Solution2(String file) throws IOException {
        this.path = new ArrayList<>();
        this.grid = new ArrayList<>();
        this.startIndices = new ArrayList<>();
        Path path = Paths.get(file);
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ArrayList<Character> lineChars = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                lineChars.add(line.charAt(i));
            }
            this.grid.add(lineChars);
        }
        scanner.close();
        this.visited = new boolean[this.grid.size()][this.grid.get(0).size()];
        this.potentialObstacles = new boolean[this.grid.size()][this.grid.get(0).size()];
        for (int i = 0; i < this.grid.size(); i++) {
            for (int j = 0; j < this.grid.get(0).size(); j++) {
                this.visited[i][j] = false;
                this.potentialObstacles[i][j] = false;
                if (this.grid.get(i).get(j) == '^') {
                    this.startIndices.add(i);
                    this.startIndices.add(j);
                    this.visited[i][j] = true;
                }
            }
        }
    }

    public void getSolution() {
        populateVisited();
        addObstacles();
        int count = countObstacles();
        System.out.println("Solution 2: " + count);
    }

    private int countObstacles() {
        int count = 0;
        for (int i = 0; i < this.grid.size(); i++) {
            for (int j = 0; j< this.grid.get(0).size(); j++) {
                if (this.potentialObstacles[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }
    private void addObstacles() {
        boolean[][] tempVisited = new boolean[this.grid.size()][this.grid.get(0).size()];
        for (int i = 0; i < this.grid.size(); i++) {
            for (int j = 0; j < this.grid.get(0).size(); j++) {
                tempVisited[i][j] = visited[i][j];
            }
        }
        for (int i = 0; i < this.grid.size(); i++) {
            for (int j = 0; j < this.grid.get(0).size(); j++) {
                if (tempVisited[i][j] && this.grid.get(i).get(j) == '.') {
                    this.grid.get(i).set(j,'#');
                    this.path = new ArrayList<>();
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
                        boolean breakWhile = false;
                        for (int[] point : this.path) {
                            if (currRow == point[0] && currCol == point[1] && vertDir == point[2] && horDir == point[3]) {
                                this.potentialObstacles[i][j] = true;
                                breakWhile = true;
                                break;
                            }
                        }
                        this.path.add(output);
                        if (breakWhile) {
                            break;
                        }
                    }
                    this.grid.get(i).set(j,'.');
                }
            }
        }
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
        int colEndIndex = (horDir == 0) ? currCol : ((horDir == 1) ? this.grid.get(0).size() - 1 : 0);
        int vertIterator = (vertDir == 0) ? 1 : vertDir;
        int horIterator = (horDir == 0) ? 1 : horDir;
        boolean breakLoop = false;
        for (int i = currRow; (vertIterator > 0) ? (i <= rowEndIndex) : (i >= rowEndIndex); i += vertIterator) {
            for (int j = currCol; (horIterator > 0) ? (j <= colEndIndex) : (j >= colEndIndex); j += horIterator) {
                if (i == rowEndIndex && j == colEndIndex && this.grid.get(i).get(j) != '#') {
                    visited[i][j] = true;
                    breakLoop = true;
                    output[0] = i;
                    output[1] = j;
                    output[2] = 0;
                    output[3] = 0;
                    break;
                }
                if (this.grid.get(i).get(j) == '#') {
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
