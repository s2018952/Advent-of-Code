import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution2 {
    private ArrayList<String> grid;
    private Integer[] startIndices;
    private final int number_of_steps;

    public Solution2(String filepath,int number_of_steps) throws IOException {
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
        System.out.println("Part 2: " + poly((long) Math.floor(number_of_steps / grid.size())));
    }

    // Found that number of possible plots visited after x steps, x + n steps, x + 2n steps, ... x + kn steps etc are on polynomial curve
    // where n is number of rows of given map. For 26501365 steps and length n = 131, we know 26501365 % 131 is 65 and so we found number
    // of possible plots after 65 steps (3814); 65 + 131 steps (33952) and 65 + 2*131 steps (94138). Found polynomial with points (0,3814),
    // (1,33952) and (2,94138) which is 15024x^2 + 15114x + 3814 and so we need to then compute poly(floor(26501365 / 131))
    private long poly(long n) {
        return 15024*n*n + 15114*n + 3814;
    }
    public int getNumberOfPossiblePlotsVisited(int step_num) {
        ArrayList<Integer[]> positions = new ArrayList<>();
        positions.add(startIndices);
        for (int i = 0; i < step_num; i++) {
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
        return positions.size();
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
            if (grid.get(Math.floorMod(position1[0],grid.size())).charAt(Math.floorMod(position1[1],grid.get(0).length())) != '#') {
                nexts.add(position1);
            }
            if (grid.get(Math.floorMod(position2[0],grid.size())).charAt(Math.floorMod(position2[1],grid.get(0).length())) != '#') {
                nexts.add(position2);
            }
        }
        return nexts;
    }
}
