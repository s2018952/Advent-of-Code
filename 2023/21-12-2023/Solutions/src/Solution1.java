import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Solution1 {
    private ArrayList<String> grid;
    private Integer[] startIndices;
    private final int number_of_steps;

    public Solution1(String filepath,int number_of_steps) throws IOException {
        this.number_of_steps = number_of_steps;
        this.grid = new ArrayList<>();
        this.startIndices = new Integer[2];
        Path path = Paths.get(filepath);
        Scanner scanner = new Scanner(path);
        int row = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            grid.add(line);
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == 'S') {
                    this.startIndices[0] = row;
                    this.startIndices[1] = i;
                }
            }
            row++;
        }
        scanner.close();
    }
    public void getOutput() {
        ArrayList<Integer[]> positions = new ArrayList<>();
        positions.add(startIndices);
        for (int i = 0; i < number_of_steps; i++) {
            ArrayList<Integer[]> temp = new ArrayList<>();
            for (Integer[] position : positions) {
                for (Integer[] next : getNextPositions(position)) {
                    if (!check_position_reached(temp,next)) {
                        temp.add(next);
                    }
                }
            }
            positions = temp;
        }
        System.out.println("Part 1: " + positions.size());
    }

    private boolean check_position_reached(ArrayList<Integer[]> positions, Integer[] next) {
        for (Integer[] pos : positions) {
            if (pos[0].intValue() == next[0].intValue() && pos[1].intValue() == next[1].intValue()) {
                return true;
            }
        }
        return false;
    }
    private ArrayList<Integer[]> getNextPositions(Integer[] position) {
        int current_row = position[0];
        int current_col = position[1];
        ArrayList<Integer[]> nexts = new ArrayList<>();
        for (int i = -1; i <= 1; i += 2) {
            Integer[] position1 = {current_row + i, current_col};
            Integer[] position2 = {current_row, current_col + i};
            if (position1[0] >= 0 && position1[0] < grid.size() && position1[1] >= 0 && position1[1] < grid.get(0).length() && grid.get(position1[0]).charAt(position1[1]) != '#') {
                nexts.add(position1);
            }
            if (position2[0] >= 0 && position2[0] < grid.size() && position2[1] >= 0 && position2[1] < grid.get(0).length() && grid.get(position2[0]).charAt(position2[1]) != '#') {
                nexts.add(position2);
            }
        }
        return nexts;
    }
}
