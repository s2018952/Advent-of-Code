import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution2 {
    private ArrayList<int[]> byteLocations;
    private boolean[][] byteLanded;
    private int[] startIndices;
    private int[] endIndices;
    private int[][] moves;
    private int[][][] dp;
    private int gridHeight;
    private int gridWidth;
    public Solution2(String file, int gridWidth, int gridHeight) throws IOException {
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
        this.moves = new int[][]{{1,0},{-1,0},{0,1},{0,-1}};
        this.dp = new int[gridHeight][gridWidth][3];
    }

    public void getSolution() {
        int[] indices = getBlockingPathIndices();
        System.out.println("Solution 2: " + indices[0] + "," + indices[1]);
    }

    private int[] getBlockingPathIndices() {
        for (int[] byteIndices : this.byteLocations) {
            this.byteLanded[byteIndices[0]][byteIndices[1]] = true;
            initialiseDP();
            populateDP();
            if (dp[this.endIndices[0]][this.endIndices[1]][2] == Integer.MAX_VALUE) {
                return byteIndices;
            }
        }
        return new int[] {-1,-1};
    }
    private void initialiseDP() {
        for (int i = 0; i < this.gridHeight; i++) {
            for (int j = 0; j < this.gridWidth; j++) {
                this.dp[i][j] = new int[] {i,j,Integer.MAX_VALUE};
            }
        }
        this.dp[0][0][2] = 0;
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
