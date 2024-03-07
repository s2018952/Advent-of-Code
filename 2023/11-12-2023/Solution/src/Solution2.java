import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Solution2 {
    private ArrayList<ArrayList<Character>> grid;
    private ArrayList<Integer[]> galaxyIndices;
    private ArrayList<Integer> emptyRows;
    private ArrayList<Integer> emptyColumns;
    private long output;
    private long factor;
    public Solution2(String filepath,long factor) throws IOException {
        this.factor = factor;
        this.output = 0;
        this.grid = new ArrayList<>();
        this.galaxyIndices = new ArrayList<>();
        this.emptyRows = new ArrayList<>();
        this.emptyColumns = new ArrayList<>();
        Path path = Paths.get(filepath);
        Scanner scanner = new Scanner(path);
        int rowNum = 0;
        HashSet<Integer> nonEmptyCols = new HashSet<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ArrayList<Character> row = new ArrayList<>();
            boolean galaxyInRow = false;
            for (int i = 0; i < line.length(); i++) {
                row.add(line.charAt(i));
                if (line.charAt(i) == '#') {
                    galaxyInRow = true;
                    Integer[] index = new Integer[2];
                    index[0] = rowNum;
                    index[1] = i;
                    nonEmptyCols.add(i);
                    this.galaxyIndices.add(index);
                }
            }
            grid.add(row);
            if (!galaxyInRow) {
                this.emptyRows.add(rowNum);
            }
            rowNum++;
        }
        for (int i = 0; i < grid.get(0).size(); i++) {
            if (!nonEmptyCols.contains(i)) {
                this.emptyColumns.add(i);
            }
        }
    }

    public void printOutput() {
        //reformatGrid();
        for (Integer[] galaxy1 : galaxyIndices) {
            for (Integer[] galaxy2 : galaxyIndices) {
                output += getDistance(galaxy1,galaxy2);
            }
        }
        output /= 2;
        System.out.println("Part 2: " + output);
    }
    private long getDistance(Integer[] galaxy1, Integer[] galaxy2) {
        int minRow = Math.min(galaxy1[0],galaxy2[0]);
        int maxRow = Math.max(galaxy1[0],galaxy2[0]);
        int minCol = Math.min(galaxy1[1],galaxy2[1]);
        int maxCol = Math.max(galaxy1[1],galaxy2[1]);
        int emptyRowCount = 0;
        int emptyColCount = 0;
        for (int i = minRow; i < maxRow; i++) {
            if (emptyRows.contains(i)) {
                emptyRowCount++;
            }
        }
        for (int i = minCol; i < maxCol; i++) {
            if (emptyColumns.contains(i)) {
                emptyColCount++;
            }
        }
        //int factor = 1000000;
        return Math.abs(maxRow - minRow - emptyRowCount + emptyRowCount * factor) + Math.abs(maxCol - minCol - emptyColCount + emptyColCount * factor);
    }
}
