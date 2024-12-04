import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution1 {
    private ArrayList<String> grid;
    private int[][] directions;
    public Solution1(String file) throws IOException {
        Path path = Paths.get(file);
        Scanner scanner = new Scanner(path);
        this.grid = new ArrayList<>();
        while (scanner.hasNextLine()) {
            grid.add(scanner.nextLine());
        }
        scanner.close();
        this.directions = new int[8][2];
        int indexCount = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == j && i == 0) {
                    continue;
                }
                this.directions[indexCount][0] = i;
                this.directions[indexCount][1] = j;
                indexCount++;
            }
        }
    }

    public void getSolution() {
        System.out.println("Solution 1: " + countWordInGrid("XMAS"));
    }
    private int countWordInGrid(String word) {
        if (word.isEmpty()) {
            return 0;
        }
        int count = 0;
        for (int[] dir : this.directions) {
            count += countDirection(dir, word);
        }
        return count;
    }
    private int countDirection(int[] dir, String word) {
        int horDir = dir[1];
        int vertDir = dir[0];
        int horStartIndex = (horDir >= 0) ? 0 : this.grid.get(0).length() - 1;
        int vertStartIndex = (vertDir >= 0) ? 0 : this.grid.size() - 1;
        int horEndIndex = (horDir == 0) ? this.grid.get(0).length() - 1 : ((horDir == 1) ? this.grid.get(0).length() - word.length() : word.length() - 1);
        int vertEndIndex = (vertDir == 0) ? this.grid.size() - 1 : ((vertDir == 1) ? this.grid.size() - word.length() : word.length() - 1);
        int count = 0;
        int vertIterator = (vertDir != 0) ? vertDir : 1;
        int horIterator = (horDir != 0) ? horDir : 1;
        for (int i = vertStartIndex;(vertDir >= 0) ? (i <= vertEndIndex) : (i >= vertEndIndex); i += vertIterator) {
            for (int j = horStartIndex; (horDir >= 0) ? (j <= horEndIndex) : (j >= horEndIndex); j += horIterator) {
                int currVertIndex = i;
                int currHorIndex = j;
                for (int k = 0; k < word.length(); k++) {
                    if (this.grid.get(currVertIndex).charAt(currHorIndex) != word.charAt(k)) {
                        break;
                    }
                    currVertIndex += vertDir;
                    currHorIndex += horDir;
                    if (k == word.length() - 1) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
