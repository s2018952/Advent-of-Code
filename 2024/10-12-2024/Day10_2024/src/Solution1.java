import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution1 {
    ArrayList<ArrayList<Integer>> grid;
    ArrayList<int[]> zeroLocations;
    public Solution1(String file) throws IOException {
        this.grid = new ArrayList<>();
        this.zeroLocations = new ArrayList<>();
        Path path = Paths.get(file);
        Scanner scanner = new Scanner(path);
        int rowIndex = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ArrayList<Integer> lineNumbers = new ArrayList<>();
            for (int j = 0; j < line.length(); j++) {
                int number = Integer.parseInt(String.valueOf(line.charAt(j)));
                lineNumbers.add(number);
                if (number == 0) {
                    int[] indices = {rowIndex,j};
                    this.zeroLocations.add(indices);
                }
            }
            this.grid.add(lineNumbers);
            rowIndex++;
        }
        scanner.close();
    }

    public void getSolution() {
        int count = getAllPathsToTop();
        System.out.println("Solution 1: " + count);
    }
    private int getAllPathsToTop() {
        int total = 0;
        for (int[] indices : this.zeroLocations) {
            int toAdd = reachableTops(indices[0],indices[1]).size();
            total += toAdd;
        }
        return total;
    }

    private ArrayList<int[]> reachableTops(int rowIndex, int colIndex) {
        ArrayList<int[]> output = new ArrayList<>();
        if (rowIndex < 0 || rowIndex >= this.grid.size() || colIndex < 0 || colIndex >= this.grid.get(0).size()) {
            return output;
        }
        if (this.grid.get(rowIndex).get(colIndex) == 9) {
            int[] endIndices = {rowIndex, colIndex};
            output.add(endIndices);
            return output;
        }
        for (int row = rowIndex - 1; row <= rowIndex + 1; row += 2) {
            if (row >= 0 && row < this.grid.size() && this.grid.get(row).get(colIndex) == this.grid.get(rowIndex).get(colIndex) + 1) {
                for (int[] indices : reachableTops(row,colIndex)) {
                    boolean contains = false;
                    for (int[] found : output) {
                        if (indices[0] == found[0] && indices[1] == found[1]) {
                            contains = true;
                            break;
                        }
                    }
                    if (!contains) {
                        output.add(indices);
                    }
                }
            }
        }
        for (int col = colIndex - 1; col <= colIndex + 1; col += 2) {
            if (col >= 0 && col < this.grid.get(0).size() && this.grid.get(rowIndex).get(col) == this.grid.get(rowIndex).get(colIndex) + 1) {
                for (int[] indices : reachableTops(rowIndex,col)) {
                    boolean contains = false;
                    for (int[] found : output) {
                        if (indices[0] == found[0] && indices[1] == found[1]) {
                            contains = true;
                            break;
                        }
                    }
                    if (!contains) {
                        output.add(indices);
                    }
                }
            }
        }
        return output;
    }
}
