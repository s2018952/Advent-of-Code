import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution2 {
    ArrayList<ArrayList<Integer>> grid;
    ArrayList<int[]> zeroLocations;
    public Solution2(String file) throws IOException {
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
        System.out.println("Solution 2: " + count);
    }
    private int getAllPathsToTop() {
        int total = 0;
        for (int[] indices : this.zeroLocations) {
            total += numberPathsToTop(indices[0],indices[1]);
        }
        return total;
    }

    private int numberPathsToTop(int rowIndex, int colIndex) {
        if (rowIndex < 0 || rowIndex >= this.grid.size() || colIndex < 0 || colIndex >= this.grid.get(0).size()) {
            return 0;
        }
        if (this.grid.get(rowIndex).get(colIndex) == 9) {
            return 1;
        }
        int count = 0;
        for (int row = rowIndex - 1; row <= rowIndex + 1; row += 2) {
            if (row >= 0 && row < this.grid.size() && this.grid.get(row).get(colIndex) == this.grid.get(rowIndex).get(colIndex) + 1) {
                count += numberPathsToTop(row,colIndex);
            }
        }
        for (int col = colIndex - 1; col <= colIndex + 1; col += 2) {
            if (col >= 0 && col < this.grid.get(0).size() && this.grid.get(rowIndex).get(col) == this.grid.get(rowIndex).get(colIndex) + 1) {
                count += numberPathsToTop(rowIndex,col);
            }
        }
        return count;
    }
}
