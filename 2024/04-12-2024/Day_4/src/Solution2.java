import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution2 {
    private ArrayList<String> grid;
    public Solution2(String file) throws IOException {
        Path path = Paths.get(file);
        Scanner scanner = new Scanner(path);
        this.grid = new ArrayList<>();
        while (scanner.hasNextLine()) {
            grid.add(scanner.nextLine());
        }
        scanner.close();
    }

    public void getSolution() {
        System.out.println("Solution 2: " + countGrid());
    }

    private int countGrid() {
        int count = 0;
        for (int i = 1; i < this.grid.size() - 1; i++) {
            for (int j = 1; j < this.grid.get(0).length() - 1; j++) {
                if (checkIfMAS(i,j)) {
                    count++;
                }
            }
        }
        return count;
    }
    private boolean checkIfMAS(int vertIndex, int horIndex) {
        if (vertIndex == 0 || vertIndex == this.grid.size() - 1 || horIndex == 0 || horIndex == this.grid.get(0).length() - 1) {
            return false;
        }
        if (this.grid.get(vertIndex).charAt(horIndex) != 'A') {
            return false;
        }
        // top left to bottom right diagonal
        char top_left = this.grid.get(vertIndex - 1).charAt(horIndex - 1);
        char bottom_right = this.grid.get(vertIndex + 1).charAt(horIndex + 1);
        // bottom left to top right
        char bottom_left = this.grid.get(vertIndex + 1).charAt(horIndex - 1);
        char top_right = this.grid.get(vertIndex - 1).charAt(horIndex + 1);
        if ((top_left == 'M' && bottom_right == 'S') || (top_left == 'S' && bottom_right == 'M')) {
            return (bottom_left == 'M' && top_right == 'S') || (bottom_left == 'S' && top_right == 'M');
        }
        return false;
    }
}
