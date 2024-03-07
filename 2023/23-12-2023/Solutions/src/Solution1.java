import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Solution1 {
    private ArrayList<String> grid;
    private Integer[] startIndices;
    private Integer[] endIndices;
    private ArrayList<Integer[]> visited;

    public Solution1(String filepath) throws IOException {
        visited = new ArrayList<>();
        grid = new ArrayList<>();
        startIndices = new Integer[2];
        endIndices = new Integer[2];
        Path path = Paths.get(filepath);
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            grid.add(line.trim());
        }
        scanner.close();
        startIndices[0] = 0;
        startIndices[1] = 1;
        endIndices[0] = grid.size() - 1;
        endIndices[1] = grid.get(0).length() - 2;
    }

    public void getOutput() {
        int length = getLongestPathLengthToEnd(startIndices,visited);
        System.out.println("Part 1: " + length);
    }


    private int getLongestPathLengthToEnd(Integer[] current, ArrayList<Integer[]> visited_nodes) {
        if (current[0].intValue() == endIndices[0].intValue() && current[1].intValue() == endIndices[1].intValue()) {
            return 0;
        }
        ArrayList<Integer[]> visited_nodes1 = new ArrayList<>(visited_nodes);
        visited_nodes1.add(current);
        ArrayList<Integer[]> neighbours = getPossibleNeighboursForRecursion(current,visited_nodes);
        int maxLength = -1;
        for (Integer[] neighbour : neighbours) {
            maxLength = Math.max(maxLength,getLongestPathLengthToEnd(neighbour,visited_nodes1));
        }
        return maxLength + 1;
    }
    private ArrayList<Integer[]> getPossibleNeighboursForRecursion(Integer[] current,ArrayList<Integer[]> visited_nodes) {
        int current_row = current[0];
        int current_col = current[1];
        char curr = grid.get(current_row).charAt(current_col);
        ArrayList<Integer[]> neighbours = new ArrayList<>();
        Integer[] dir1 = {current_row - 1, current_col};
        Integer[] dir2 = {current_row + 1, current_col};
        Integer[] dir3 = {current_row, current_col - 1};
        Integer[] dir4 = {current_row, current_col + 1};
        if (curr == '^') {
            if (isValid(dir1) && !isInVisitedNodes(dir1,visited_nodes)) {
                neighbours.add(dir1);
            }
        }
        else if (curr == 'v') {
            if (isValid(dir2) && !isInVisitedNodes(dir2,visited_nodes)) {
                neighbours.add(dir2);
            }
        }
        else if (curr == '<') {
            if (isValid(dir3) && !isInVisitedNodes(dir3,visited_nodes)) {
                neighbours.add(dir3);
            }
        }
        else if (curr == '>') {
            if (isValid(dir4) && !isInVisitedNodes(dir4,visited_nodes)) {
                neighbours.add(dir4);
            }
        }
        else {
            if (isValid(dir1) && !isInVisitedNodes(dir1,visited_nodes)) {
                neighbours.add(dir1);
            }
            if (isValid(dir2) && !isInVisitedNodes(dir2,visited_nodes)) {
                neighbours.add(dir2);
            }
            if (isValid(dir3) && !isInVisitedNodes(dir3,visited_nodes)) {
                neighbours.add(dir3);
            }
            if (isValid(dir4) && !isInVisitedNodes(dir4,visited_nodes)) {
                neighbours.add(dir4);
            }
        }
        return neighbours;
    }

    private boolean isInVisitedNodes(Integer[] current, ArrayList<Integer[]> visited_nodes) {
        for (Integer[] node : visited_nodes) {
            if (node[0].intValue() == current[0].intValue() && node[1].intValue() == current[1].intValue()) {
                return true;
            }
        }
        return false;
    }

    private boolean isValid(Integer[] current) {
        return current[0] >= 0 && current[0] < grid.size() && current[1] >= 0 && current[1] < grid.get(0).length() && grid.get(current[0]).charAt(current[1]) != '#';
    }
}
