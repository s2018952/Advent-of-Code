import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Solution1 {
    private ArrayList<ArrayList<Integer>> grid;
    private final Integer[] startIndex;
    private final Integer[] endIndex;
    private int[][][] costsFromStart;
    private ArrayList<Integer[]> seenNodes;
    private ArrayList<Integer[]> nodes;
    private int minStraight;
    private int maxStraight;
    private ArrayList<Integer[]> directions;
    public Solution1(String filepath,int minStraight,int maxStraight) throws IOException {
        this.seenNodes = new ArrayList<>();
        this.minStraight = minStraight;
        this.maxStraight = maxStraight;
        this.nodes = new ArrayList<>();
        this.startIndex = new Integer[2];
        this.startIndex[0] = 0;
        this.startIndex[1] = 0;
        this.endIndex = new Integer[2];
        Path path = Paths.get(filepath);
        Scanner scanner = new Scanner(path);
        this.grid = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ArrayList<Integer> row = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                row.add(Character.getNumericValue(line.charAt(i)));
            }
            this.grid.add(row);
        }
        this.endIndex[0] = this.grid.size() - 1;
        this.endIndex[1] = this.grid.get(0).size() - 1;
        this.costsFromStart = new int[this.grid.size()][this.grid.get(0).size()][4];
        for (int i = 0; i < this.grid.size(); i++) {
            for (int j = 0; j < this.grid.get(0).size(); j++) {
                for (int k = 0; k < 4; k++) {
                    this.costsFromStart[i][j][k] = 10 * this.grid.size() * this.grid.get(0).size();
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            this.costsFromStart[0][0][i] = 0;
        }
        Integer[] startNode = {0,0,0,-1};
        this.nodes.add(startNode);
        this.directions = new ArrayList<>();
        Integer[] dir0 = {0,1};
        Integer[] dir1 = {1,0};
        Integer[] dir2 = {0,-1};
        Integer[] dir3 = {-1,0};
        this.directions.add(dir0);
        this.directions.add(dir1);
        this.directions.add(dir2);
        this.directions.add(dir3);
    }
    public void getOutput() {
        int cost = search();
        System.out.println("Part 1: " + cost);
    }

    private int search() {
        while (!nodes.isEmpty()) {
            int indexToBeRemoved = 0;
            int[] node = new int[4];
            for (int i = 0; i < 4; i++) {
                node[i] = nodes.get(indexToBeRemoved)[i];
            }
            nodes.remove(indexToBeRemoved);
            int cost = node[0];
            int x = node[1];
            int y = node[2];
            int disallowedDirIndex = node[3];
            Integer[] nodeForSeen = {x,y,disallowedDirIndex};
            if (x == endIndex[0] && y == endIndex[1]) {
                return cost;
            }
            if (isNodeInSeen(nodeForSeen)) {
                continue;
            }
            seenNodes.add(nodeForSeen);
            for (int dirIndex = 0; dirIndex < directions.size(); dirIndex++) {
                int costIncrease = 0;
                if (dirIndex == disallowedDirIndex || (dirIndex + 2) % 4 == disallowedDirIndex) {
                    continue;
                }
                for (int dist = 1; dist <= maxStraight; dist++) {
                    int xx = x + directions.get(dirIndex)[0] * dist;
                    int yy = y + directions.get(dirIndex)[1] * dist;
                    if (xx >= 0 && xx <= this.grid.size() - 1 && yy >= 0 && yy <= this.grid.get(0).size() - 1) {
                        costIncrease += grid.get(xx).get(yy);
                        if (dist < minStraight) {
                            continue;
                        }
                        int newCost = cost + costIncrease;
                        if (costsFromStart[xx][yy][dirIndex] <= newCost) {
                            continue;
                        }
                        costsFromStart[xx][yy][dirIndex] = newCost;
                        Integer[] next = {newCost,xx,yy,dirIndex};
                        nodes.add(next);
                    }
                }

            }
        }
        return -1;
    }

    private boolean isNodeInSeen(Integer[] node) {
        for (Integer[] node1 : nodes) {
            if (node1[0] == node[0] && node1[1] == node[1] && node1[2] == node[2]) {
                return true;
            }
        }
        return false;
    }
}
