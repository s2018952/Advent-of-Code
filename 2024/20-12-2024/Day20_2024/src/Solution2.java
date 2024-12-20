import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution2 {
    private ArrayList<ArrayList<Character>> grid;
    private int[] startIndices;
    private int[] endIndices;
    private int[][] moves;
    private int[][][] dp;
    private ArrayList<int[]> freeIndices;
    public Solution2(String file) throws IOException {
        this.grid = new ArrayList<>();
        this.freeIndices = new ArrayList<>();
        int rowIndex = 0;
        Path path = Paths.get(file);
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ArrayList<Character> lineChars = new ArrayList<>();
            for (int j = 0; j < line.length(); j++) {
                lineChars.add(line.charAt(j));
                if (line.charAt(j) != '#') {
                    this.freeIndices.add(new int[] {rowIndex,j});
                }
                if (line.charAt(j) == 'S') {
                    this.startIndices = new int[] {rowIndex,j};
                }
                else if (line.charAt(j) == 'E') {
                    this.endIndices = new int[] {rowIndex,j};
                }
            }
            this.grid.add(lineChars);
            rowIndex++;
        }
        scanner.close();
        this.moves = new int[][] {{1,0},{-1,0},{0,1},{0,-1}};
        this.dp = new int[this.grid.size()][this.grid.get(0).size()][3];
        initialiseDP();
        populateDP();
    }

    public void getSolution() {
        int count = getNumberCheatsWithMinTimeSaved(100);
        System.out.println("Solution 2: " + count);
    }
    private int getNumberCheatsWithMinTimeSaved(int minTimeSaved) {
        int count = 0;
        for (int[] free : this.freeIndices) {
            for (int[] free2 : this.freeIndices) {
                int distance = Math.abs(free[0] - free2[0]) + Math.abs(free[1] - free2[1]);
                if (distance <= 20) {
                    int timeSaved = this.dp[free2[0]][free2[1]][2] - (this.dp[free[0]][free[1]][2] + distance);
                    if (timeSaved >= minTimeSaved) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
    private void populateDP() {
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(s -> s[2]));
        queue.add(this.dp[this.startIndices[0]][this.startIndices[1]]);
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int currentCost = current[2];
            if (current[0] == this.endIndices[0] && current[1] == this.endIndices[1]) {
                break;
            }
            for (int[] move : this.moves) {
                int newRow = current[0] + move[0];
                int newCol = current[1] + move[1];
                if (isPositionFree(newRow,newCol) && this.dp[newRow][newCol][2] > currentCost + 1) {
                    this.dp[newRow][newCol][2] = currentCost + 1;
                    queue.add(this.dp[newRow][newCol]);
                }
            }
        }
    }
    private void initialiseDP() {
        for (int i = 0; i < this.grid.size(); i++) {
            for (int j = 0; j < this.grid.get(0).size(); j++) {
                this.dp[i][j] = new int[]{i,j,Integer.MAX_VALUE};
            }
        }
        this.dp[this.startIndices[0]][this.startIndices[1]][2] = 0;
    }

    private boolean isPositionFree(int row, int column) {
        return row >= 0 && row < this.grid.size() && column >= 0 && column < this.grid.get(0).size() && this.grid.get(row).get(column) != '#';
    }
}
