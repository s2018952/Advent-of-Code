import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution1 {
    private ArrayList<ArrayList<Character>> grid;
    private ArrayList<ArrayList<int[]>> regions;

    public Solution1(String file) throws IOException {
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
        System.out.println("Solution 1: " + price);
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
        return getArea(region) * getPerimeter(region);
    }
    private int getPerimeter(ArrayList<int[]> region) {
        int perimeter = 0;
        for (int[] cell : region) {
            perimeter += 4;
            for (int[] neighbour : getNeighbours(cell[0],cell[1])) {
                if (isIndicesInArrayList(neighbour,region)) {
                    perimeter -= 1;
                }
            }
        }
        return perimeter;
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
            if (ins[0] == indices[0] && ins[1] == indices[1]) {
                return true;
            }
        }
        return false;
    }
}
