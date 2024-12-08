import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Solution2 {
    private ArrayList<ArrayList<Character>> grid;
    private HashMap<Character,ArrayList<int[]>> char_to_indices;
    private boolean[][] antinode_location;
    public Solution2(String file) throws IOException {
        Path path = Paths.get(file);
        Scanner scanner = new Scanner(path);
        this.grid = new ArrayList<>();
        this.char_to_indices = new HashMap<>();
        int rowIndex = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ArrayList<Character> lineChars = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                lineChars.add(c);
                if (c != '.') {
                    int[] index = {rowIndex, i};
                    if (!this.char_to_indices.containsKey(c)) {
                        this.char_to_indices.put(c,new ArrayList<>());
                    }
                    this.char_to_indices.get(c).add(index);
                }
            }
            this.grid.add(lineChars);
            rowIndex++;
        }
        scanner.close();
        this.antinode_location = new boolean[this.grid.size()][this.grid.get(0).size()];
        for (int i = 0; i < this.grid.size(); i++) {
            for (int j = 0; j < this.grid.get(0).size(); j++) {
                this.antinode_location[i][j] = false;
            }
        }
    }

    public void getSolution() {
        int count = findAllAntinodeLocationsAndGetCount();
        System.out.println("Solution 2: " + count);
    }
    private int findAllAntinodeLocationsAndGetCount() {
        for (Character c : this.char_to_indices.keySet()) {
            findAntinodeLocations(this.char_to_indices.get(c));
        }
        int count = 0;
        for (int i = 0; i < this.grid.size(); i++) {
            for (int j = 0; j < this.grid.get(0).size(); j++) {
                if (this.antinode_location[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }
    private void findAntinodeLocations(ArrayList<int[]> antenna_locations) {
        for (int first = 0; first < antenna_locations.size(); first++) {
            for (int second = first + 1; second < antenna_locations.size(); second++) {
                int firstRow = antenna_locations.get(first)[0];
                int firstCol = antenna_locations.get(first)[1];
                int secondRow = antenna_locations.get(second)[0];
                int secondCol = antenna_locations.get(second)[1];
                int rowDiff = secondRow - firstRow;
                int colDiff = secondCol - firstCol;
                int currRow = firstRow;
                int currCol = firstCol;
                while (currRow >= 0 && currRow < this.grid.size() && currCol >= 0 && currCol < this.grid.get(0).size()) {
                    this.antinode_location[currRow][currCol] = true;
                    currRow -= rowDiff;
                    currCol -= colDiff;
                }
                currRow = secondRow;
                currCol = secondCol;
                while (currRow >= 0 && currRow < this.grid.size() && currCol >= 0 && currCol < this.grid.get(0).size()) {
                    this.antinode_location[currRow][currCol] = true;
                    currRow += rowDiff;
                    currCol += colDiff;
                }
            }
        }
    }
}
