import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution1 {

    private ArrayList<int[]> byteLocations;
    private boolean[][] byteLanded;
    private int[] startIndices;
    private int[] endIndices;
    private int[][] moves;
    private int[][][] dp;
    private int gridHeight;
    private int gridWidth;
    public Solution1(String file, int gridWidth, int gridHeight, int numberBytesRead) throws IOException {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        this.byteLocations = new ArrayList<>();
        Path path = Paths.get(file);
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int[] location = new int[] {Integer.parseInt(line.strip().split(",")[0]),Integer.parseInt(line.strip().split(",")[1])};
            this.byteLocations.add(location);
        }
        scanner.close();
        this.startIndices = new int[] {0,0};
        this.endIndices = new int[] {gridHeight - 1,gridWidth - 1};
        this.byteLanded = new boolean[gridHeight][gridWidth];
        for (int i = 0; i < numberBytesRead; i++) {
            int[] location = this.byteLocations.get(i);
            this.byteLanded[location[0]][location[1]] = true;
        }
        this.moves = new int[][]{{1,0},{-1,0},{0,1},{0,-1}};
        this.dp = new int[gridHeight][gridWidth][3];
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                this.dp[i][j] = new int[] {i,j,Integer.MAX_VALUE};
            }
        }
        this.dp[0][0][2] = 0;
    }

    public void getSolution() {
        populateDP();
        System.out.println("Solution 1: " + this.dp[this.endIndices[0]][this.endIndices[1]][2]);
    }
    private void populateDP() {
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(s -> s[2]));
        queue.add(this.dp[0][0]);
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            if (current[0] == this.endIndices[0] && current[1] == this.endIndices[1]) {
                break;
            }
            int currentCost = current[2];
            for (int[] move : this.moves) {
                if (isMoveValid(current[0],current[1],move)) {
                    int[] next = makeMove(current[0],current[1],move);
                    if (this.dp[next[0]][next[1]][2] > currentCost + 1) {
                        this.dp[next[0]][next[1]][2] = currentCost + 1;
                        queue.add(this.dp[next[0]][next[1]]);
                    }
                }
            }
        }
    }
    private int[] makeMove(int row, int col, int[] move) {
        return new int[] {row + move[0],col + move[1]};
    }
    private boolean isMoveValid(int row, int col, int[] move) {
        int newRow = row + move[0];
        int newCol = col + move[1];
        return newRow >= 0 && newRow < this.byteLanded.length && newCol >= 0 && newCol < this.byteLanded[0].length && !this.byteLanded[newRow][newCol];
    }
}
