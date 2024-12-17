import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution1 {
    private ArrayList<ArrayList<Character>> grid;
    private int[] startIndices;
    private int[] endIndices;
    private int[][][] dpCosts;
    public Solution1(String file) throws IOException {
        this.grid = new ArrayList<>();
        this.endIndices = new int[2];
        Path path = Paths.get(file);
        Scanner scanner = new Scanner(path);
        int rowIndex = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ArrayList<Character> lineChars = new ArrayList<>();
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                lineChars.add(c);
                if (c == 'S') {
                    this.startIndices = new int[]{rowIndex, j};
                }
                if (c == 'E') {
                    this.endIndices = new int[]{rowIndex,j};
                }
            }
            this.grid.add(lineChars);
            rowIndex++;
        }
        scanner.close();
        this.dpCosts = new int[this.grid.size()][this.grid.get(0).size()][5];
        for (int i = 0; i < this.grid.size(); i++) {
            for (int j = 0; j < this.grid.get(0).size(); j++) {
                this.dpCosts[i][j] = new int[] {i,j,-1,-1,Integer.MAX_VALUE};
            }
        }
        this.dpCosts[this.startIndices[0]][this.startIndices[1]] = new int[] {this.startIndices[0],this.startIndices[1],0,1,0};
    }

    public void getSolution() {
        int cost = getLowestCostToEnd();
        System.out.println("Solution 1: " + cost);
    }
    private int getLowestCostToEnd() {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s[4]));
        pq.add(this.dpCosts[this.startIndices[0]][this.startIndices[1]]);
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currentRow = current[0];
            int currentColumn = current[1];
            int[] currentDirection = {current[2],current[3]};
            int currentCost = current[4];
            if (currentRow == this.endIndices[0] && currentColumn == this.endIndices[1]) {
                return currentCost;
            }
            int[][] moves = {{0,1},{-1,0},{0,-1},{1,0}};
            for (int[] move : moves) {
                if (isMoveValid(currentRow,currentColumn,move)) {
                    int[] moveOutput = makeMove(currentRow,currentColumn,currentDirection,move);
                    if (this.dpCosts[moveOutput[0]][moveOutput[1]][4] > currentCost + moveOutput[4]) {
                        this.dpCosts[moveOutput[0]][moveOutput[1]] = new int[] {moveOutput[0],moveOutput[1],moveOutput[2],moveOutput[3],currentCost + moveOutput[4]};
                        pq.add(this.dpCosts[moveOutput[0]][moveOutput[1]]);
                    }
                }
            }
        }
        return -1;
    }
    private int[] makeMove(int currentRow, int currentColumn, int[] currentDirection, int[] move) {
        int cost = getMoveCost(currentDirection,move);
        int newRow = currentRow + move[0];
        int newCol = currentColumn + move[1];
        int[] output = new int[] {newRow,newCol,move[0],move[1],cost};
        return output;
    }
    private int getMoveCost(int[] currentDirection, int[] move) {
        int currVertDir = currentDirection[0];
        int currHorDir = currentDirection[1];
        int moveVertDir = move[0];
        int moveHorDir = move[1];
        if (currVertDir == moveVertDir && currHorDir == moveHorDir) {
            return 1;
        }
        else if (currVertDir == moveVertDir && currHorDir != moveHorDir) {
            return 2001;
        }
        else if (currVertDir != moveVertDir && currHorDir == moveHorDir) {
            return 2001;
        }
        else {
            return 1001;
        }
    }

    private boolean isMoveValid(int currentRow, int currentColumn, int[] move) {
        return this.grid.get(currentRow + move[0]).get(currentColumn + move[1]) != '#';
    }
}
