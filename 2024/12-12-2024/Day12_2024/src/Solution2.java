import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Solution2 {
    private ArrayList<ArrayList<Character>> grid;
    private ArrayList<ArrayList<int[]>> regions;

    public Solution2(String file) throws IOException {
        this.grid = new ArrayList<>();
        Path path = Paths.get(file);
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ArrayList<Character> lineChars = new ArrayList<>();
            for (int j = 0; j < line.length(); j++) {
                lineChars.add(line.charAt(j));
            }
            this.grid.add(lineChars);
        }
        scanner.close();
        this.regions = new ArrayList<>();
        identifyRegions();
    }

    public void getSolution() {
        int price = getTotalPrice();
        System.out.println("Solution 2: " + price);
    }
    private int getTotalPrice() {
        int total = 0;
        for (ArrayList<int[]> region : this.regions) {
            int price = getPriceForRegion(region);
            total += price;
        }
        return total;
    }
    private int getPriceForRegion(ArrayList<int[]> region) {
        return getArea(region) * getNumberOfSides(region);
    }
    private int getNumberOfSides(ArrayList<int[]> region) {
        int sides = 0;
        boolean[][] visited = new boolean[this.grid.size()][this.grid.get(0).size()];
        sides += bfsSide(region.get(0)[0],region.get(0)[1],region,visited);
        return sides;
    }
    private int bfsSide(int row, int col, ArrayList<int[]> region, boolean[][] visited) {
        ArrayList<int[]> queue = new ArrayList<>();
        int counter = 0;
        queue.add(new int[] {row,col,4});
        int sideNum = 0;
        ArrayList<int[]> sides = new ArrayList<>();
        while (counter < queue.size()) {
            int x = queue.get(counter)[0];
            int y = queue.get(counter)[1];
            int dir = queue.get(counter)[2];
            counter++;
            if (x < 0 || x >= this.grid.size() || y < 0 || y >= this.grid.get(0).size() || !isIndicesInArrayList(new int[] {x,y},region)) {
                if (!(isIndicesInArrayList(new int[] {x+1,y,dir},sides) || isIndicesInArrayList(new int[] {x-1,y,dir},sides) || isIndicesInArrayList(new int[] {x,y+1,dir},sides) || isIndicesInArrayList(new int[] {x,y-1,dir},sides))) {
                    sideNum += 1;
                }
                if ((isIndicesInArrayList(new int[] {x+1,y,dir},sides) && isIndicesInArrayList(new int[] {x-1,y,dir},sides)) || (isIndicesInArrayList(new int[] {x,y+1,dir},sides) && isIndicesInArrayList(new int[] {x,y-1,dir},sides))) {
                    sideNum -= 1;
                }
                sides.add(new int[] {x,y,dir});
                continue;
            }
            if (visited[x][y]) {
                continue;
            }
            visited[x][y] = true;
            queue.add(new int[] {x + 1,y,0});
            queue.add(new int[] {x,y - 1,1});
            queue.add(new int[] {x - 1,y,2});
            queue.add(new int[] {x,y + 1,3});
        }
        return sideNum;
    }

    private int getArea(ArrayList<int[]> region) {
        int area = region.size();
        return area;
    }
    private void identifyRegions() {
        boolean[][] visited = new boolean[this.grid.size()][this.grid.get(0).size()];
        for (int i = 0; i < this.grid.size(); i++) {
            for (int j = 0; j < this.grid.get(0).size(); j++) {
                if (!visited[i][j]) {
                    ArrayList<int[]> region = new ArrayList<>();
                    dfs(i,j,visited,region);
                    this.regions.add(region);
                }
            }
        }
    }

    private void dfs(int row, int col, boolean[][] visited, ArrayList<int[]> region) {
        visited[row][col] = true;
        region.add(new int[] {row,col});
        for (int[] neighbour : getNeighbours(row,col)) {
            if (!visited[neighbour[0]][neighbour[1]] && this.grid.get(row).get(col) == this.grid.get(neighbour[0]).get(neighbour[1])) {
                dfs(neighbour[0],neighbour[1],visited,region);
            }
        }
    }

    private ArrayList<int[]> getNeighbours(int row, int col) {
        ArrayList<int[]> neighbours = new ArrayList<>();
        int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            if (newRow >= 0 && newRow < this.grid.size() && newCol >= 0 && newCol < this.grid.get(0).size()) {
                int[] neighbour = {newRow, newCol};
                neighbours.add(neighbour);
            }
        }
        return neighbours;
    }

    private boolean isIndicesInArrayList(int[] indices, ArrayList<int[]> list) {
        for (int[] ins : list) {
            int trueCount = 0;
            for (int i = 0; i < indices.length; i++) {
                if (ins[i] == indices[i]) {
                    trueCount++;
                }
            }
            if (trueCount == indices.length) {
                return true;
            }
        }
        return false;
    }
}
