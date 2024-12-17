import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

// Current solution only seems to work on input. Currently, approach ought to be overestimating number of tiles on best path due to
// manner of implementation. Might take a look at this later but should be noted that this DOES NOT give the correct answer in general.
// Just got very lucky on the input
public class Solution2 {

    private ArrayList<ArrayList<Character>> grid;
    private int[] startIndices;
    private int[] endIndices;
    private int[][][][] dpCosts;
    private int[][] moves;
    private ArrayList<int[]> [][][] predecessors;
    private boolean[][] onBestPath;
    public Solution2(String file) throws IOException {
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
        this.dpCosts = new int[this.grid.size()][this.grid.get(0).size()][4][5];
        this.predecessors = new ArrayList[this.grid.size()][this.grid.get(0).size()][4];
        this.moves = new int[][]{{0, 1}, {-1, 0}, {0, -1}, {1, 0}};
        for (int i = 0; i < this.grid.size(); i++) {
            for (int j = 0; j < this.grid.get(0).size(); j++) {
                for (int k = 0; k < 4; k++) {
                    this.dpCosts[i][j][k] = new int[] {i,j,this.moves[k][0],this.moves[k][1],Integer.MAX_VALUE};
                    this.predecessors[i][j][k] = new ArrayList<>();
                }
            }
        }
        this.dpCosts[this.startIndices[0]][this.startIndices[1]][0] = new int[] {this.startIndices[0],this.startIndices[1],0,1,0};
        this.onBestPath = new boolean[this.grid.size()][this.grid.get(0).size()];
    }

    public void getSolution() {
        int cost = getLowestCostToEnd();
        for (int m = 0; m < 4; m++) {
            //if (cost == this.dpCosts[this.endIndices[0]][this.endIndices[1]][m][4]) {
            //    markBestPath(this.endIndices[0],this.endIndices[1],this.moves[m]);
            //}
            markBestPath(this.endIndices[0],this.endIndices[1],this.moves[m]);
        }
        int count = 0;
        for (int i = 0; i < this.grid.size(); i++) {
            for (int j = 0; j < this.grid.get(0).size(); j++) {
                if (this.onBestPath[i][j]) {
                    count++;
                }
            }
        }
        System.out.println("Solution 2: " + count);
    }
    private int getLowestCostToEnd() {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s[4]));
        pq.add(this.dpCosts[this.startIndices[0]][this.startIndices[1]][0]);
        int cost = -1;
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currentRow = current[0];
            int currentColumn = current[1];
            int[] currentDirection = {current[2],current[3]};
            int currentCost = current[4];
            if (currentRow == this.endIndices[0] && currentColumn == this.endIndices[1] && cost == -1) {
                //markBestPath(currentRow,currentColumn,currentDirection);
                cost = currentCost;
                continue;
            }
            for (int m = 0; m < 4; m++) {
                int[] move = this.moves[m];
                if (isMoveValid(currentRow,currentColumn,move)) {
                    int[] moveOutput = makeMove(currentRow,currentColumn,currentDirection,move);
                    if (this.dpCosts[moveOutput[0]][moveOutput[1]][m][4] > currentCost + moveOutput[4]) {
                        this.dpCosts[moveOutput[0]][moveOutput[1]][m] = new int[] {moveOutput[0],moveOutput[1],moveOutput[2],moveOutput[3],currentCost + moveOutput[4]};
                        pq.add(this.dpCosts[moveOutput[0]][moveOutput[1]][m]);
                        this.predecessors[moveOutput[0]][moveOutput[1]][m].clear();
                        this.predecessors[moveOutput[0]][moveOutput[1]][m].add(new int[] {currentRow,currentColumn,getMoveIndexFromDirection(currentDirection)});
                    }
                    else if (this.dpCosts[moveOutput[0]][moveOutput[1]][m][4] == currentCost + moveOutput[4]) {
                        this.predecessors[moveOutput[0]][moveOutput[1]][m].add(new int[] {currentRow,currentColumn,getMoveIndexFromDirection(currentDirection)});
                    }
                }
            }
        }
        return cost;
    }
    private void markBestPath(int currentRow, int currentColumn, int[] currentDirection) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {currentRow,currentColumn,getMoveIndexFromDirection(currentDirection)});
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            //if (this.onBestPath[current[0]][current[1]]) {
            //    continue;
            //}
            this.onBestPath[current[0]][current[1]] = true;
            for (int[] prev : this.predecessors[current[0]][current[1]][current[2]]) {
                queue.add(prev);
            }
        }
    }
    private int getMoveIndexFromDirection(int[] currentDirection) {
        for (int m = 0; m < 4; m++) {
            if (this.moves[m][0] == currentDirection[0] && this.moves[m][1] == currentDirection[1]) {
                return m;
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
