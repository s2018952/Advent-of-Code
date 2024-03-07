import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Solution1 {
    private ArrayList<ArrayList<Character>> grid;
    private ArrayList<Integer[]> galaxyIndices;
    private ArrayList<Integer> emptyRows;
    private ArrayList<Integer> emptyColumns;
    private int output;
    public Solution1(String filepath) throws IOException {
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
        reformatGrid();
        for (Integer[] galaxy1 : galaxyIndices) {
            for (Integer[] galaxy2 : galaxyIndices) {
                output += getDistance(galaxy1,galaxy2);
            }
        }
        output /= 2;
        System.out.println("Part 1: " + output);
    }
    private int getDistance(Integer[] galaxy1, Integer[] galaxy2) {
        return Math.abs(galaxy2[0] - galaxy1[0]) + Math.abs(galaxy2[1] - galaxy1[1]);
    }
    private void reformatGrid() {
        for (Integer rowNum : emptyRows) {
            addRow(rowNum);
        }
        for (Integer colNum : emptyColumns) {
            addCol(colNum);
        }
    }

    private void addRow(Integer rowIndex) {
        ArrayList<Character> row = new ArrayList<>();
        for (int i = 0; i < grid.get(0).size(); i++) {
            row.add('.');
        }
        grid.add(rowIndex,row);
        for (int i = 0; i < emptyRows.size(); i++) {
            if (emptyRows.get(i) > rowIndex) {
                emptyRows.set(i,emptyRows.get(i) + 1);
            }
        }
        for (Integer[] galaxyIndex : galaxyIndices) {
            if (galaxyIndex[0] > rowIndex) {
                galaxyIndex[0]++;
            }
        }
    }

    private void addCol(Integer colIndex) {
        for (ArrayList<Character> characters : grid) {
            characters.add(colIndex, '.');
        }
        for (int i = 0; i < emptyColumns.size(); i++) {
            if (emptyColumns.get(i) > colIndex) {
                emptyColumns.set(i, emptyColumns.get(i) + 1);
            }
        }
        for (Integer[] galaxyIndex : galaxyIndices) {
            if (galaxyIndex[1] > colIndex) {
                galaxyIndex[1]++;
            }
        }
    }
}
