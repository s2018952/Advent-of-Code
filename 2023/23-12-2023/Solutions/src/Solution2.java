import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Solution2 {
    private ArrayList<String> grid;
    private Integer[] startIndices;
    private Integer[] endIndices;
    private HashMap<Integer,Integer> distances_from_start;
    private HashSet<Integer> visited;
    private HashMap<Integer,ArrayList<Integer[]>> adjacency_maps;

    public Solution2(String filepath) throws IOException {
        adjacency_maps = new HashMap<>();
        visited = new HashSet<>();
        grid = new ArrayList<>();
        distances_from_start = new HashMap<>();
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
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(0).length(); j++) {
                if (grid.get(i).charAt(j) != '#') {
                    distances_from_start.put(getCodeFromIndices(i,j),0);
                }
            }
        }
        initialiseAdjacencyMatrix();
    }

    public void getOutput() {
        HashSet<Integer> visited1 = new HashSet<>();
        int length = getLongestPathToEnd(getCodeFromIndices(startIndices[0],startIndices[1]),visited1);
        System.out.println("Part 2: " + length);
        compute();
        System.out.println("Part 2: " + distances_from_start.get(getCodeFromIndices(endIndices[0],endIndices[1])));
    }

    private void compute() {
        ArrayList<Integer> nodes = new ArrayList<>();
        nodes.add(getCodeFromIndices(startIndices[0],startIndices[1]));
        while (!nodes.isEmpty()) {
            Integer current = pop(nodes);
            if (current.intValue() == getCodeFromIndices(endIndices[0],endIndices[1])) {
                break;
            }
            if (visited.contains(current)) {
                break;
            }
            for (Integer[] neighbour : adjacency_maps.get(current)) {
                if (!visited.contains(neighbour[0])) {
                    if (distances_from_start.get(neighbour[0]) < neighbour[1] + distances_from_start.get(current)) {
                        distances_from_start.put(neighbour[0],neighbour[1] + distances_from_start.get(current));
                        nodes.add(neighbour[0]);
                    }
                    //nodes.add(neighbour[0]);
                }
            }
            visited.add(current);
        }
    }

    private int getLongestPathToEnd(Integer current, HashSet<Integer> visited_nodes) {
        if (current.intValue() == getCodeFromIndices(endIndices[0],endIndices[1])) {
            return 0;
        }
        HashSet<Integer> visited_nodes1 = new HashSet<>(visited_nodes);
        visited_nodes1.add(current);
        int maxLength = -1;
        for (Integer[] neighbour : adjacency_maps.get(current)) {
            if (!visited_nodes.contains(neighbour[0])) {
                maxLength = Math.max(maxLength,neighbour[1] + getLongestPathToEnd(neighbour[0],visited_nodes1));
            }
        }
        return maxLength;
    }
    private Integer pop(ArrayList<Integer> ls) {
        Integer out = ls.get(ls.size() - 1).intValue();
        ls.remove(ls.size() - 1);
        return out;
    }

    private boolean isInVisitedNodes(Integer[] current, ArrayList<Integer[]> visited_nodes) {
        for (Integer[] node : visited_nodes) {
            if (node[0].intValue() == current[0].intValue() && node[1].intValue() == current[1].intValue()) {
                return true;
            }
        }
        return false;
    }
    private void initialiseAdjacencyMatrix() {
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(0).length(); j++) {
                if (grid.get(i).charAt(j) != '#') {
                    ArrayList<Integer[]> neighbours = getPossibleNeighbours(i,j);
                    ArrayList<Integer[]> adjacents = new ArrayList<>();
                    for (Integer[] neighbour : neighbours) {
                        Integer[] adjacent = {getCodeFromIndices(neighbour[0],neighbour[1]),1};
                        adjacents.add(adjacent);
                    }
                    adjacency_maps.put(getCodeFromIndices(i,j),adjacents);
                }
            }
        }
        while (getTwoNeighboursCount() > 50) {
            for (int i = 0; i < grid.size() * grid.get(0).length(); i++) {
                if (!adjacency_maps.containsKey(i)) {
                    continue;
                }
                if (adjacency_maps.get(i).size() == 2) {
                    Integer node0 = adjacency_maps.get(i).get(0)[0];
                    Integer step0 = adjacency_maps.get(i).get(0)[1];
                    Integer node1 = adjacency_maps.get(i).get(1)[0];
                    Integer step1 = adjacency_maps.get(i).get(1)[1];
                    Integer step = step0 + step1;
                    boolean changed = false;
                    ArrayList<Integer[]> node0list = new ArrayList<>(adjacency_maps.get(node0));
                    for (int j = 0; j < node0list.size(); j++) {
                        Integer[] node2 = node0list.get(j);
                        if (node2[0].intValue() == i) {
                            node0list.remove(node2);
                        }
                        if (node2[0].intValue() == node1.intValue()) {
                            node2[1] = Math.max(step.intValue(),node2[1].intValue());
                            changed = true;
                        }
                    }
                    if (!changed) {
                        Integer[] newNode = {node1.intValue(),step.intValue()};
                        node0list.add(newNode);
                    }
                    adjacency_maps.put(node0,node0list);
                    ArrayList<Integer[]> node1list = new ArrayList<>(adjacency_maps.get(node1));
                    boolean changed1 = false;
                    for (int j = 0; j < node1list.size(); j++) {
                        Integer[] node3 = node1list.get(j);
                        if (node3[0].intValue() == i) {
                            node1list.remove(node3);
                        }
                        if (node3[0].intValue() == node0.intValue()) {
                            node3[1] = Math.max(step.intValue(),node3[1].intValue());
                            changed1 = true;
                        }
                    }
                    if (!changed1) {
                        Integer[] newNode1 = {node0.intValue(),step.intValue()};
                        node1list.add(newNode1);
                    }
                    adjacency_maps.put(node1,node1list);
                    adjacency_maps.remove(i);
                }
            }
        }
    }

    private int getTwoNeighboursCount() {
        int count = 0;
        for (Integer node : adjacency_maps.keySet()) {
            if (adjacency_maps.get(node).size() == 2) {
                count++;
            }
        }
        return count;
    }
    private ArrayList<Integer[]> getPossibleNeighbours(int current_row, int current_col) {
        ArrayList<Integer[]> neighbours = new ArrayList<>();
        Integer[] dir1 = {current_row - 1, current_col};
        Integer[] dir2 = {current_row + 1, current_col};
        Integer[] dir3 = {current_row, current_col - 1};
        Integer[] dir4 = {current_row, current_col + 1};
        if (isValid(dir1)) {
            neighbours.add(dir1);
        }
        if (isValid(dir2)) {
            neighbours.add(dir2);
        }
        if (isValid(dir3)) {
            neighbours.add(dir3);
        }
        if (isValid(dir4)) {
            neighbours.add(dir4);
        }
        return neighbours;
    }

    private boolean isValid(Integer[] current) {
        return current[0] >= 0 && current[0] < grid.size() && current[1] >= 0 && current[1] < grid.get(0).length() && grid.get(current[0]).charAt(current[1]) != '#';
    }
    private int getCodeFromIndices(int row, int col) {
        return grid.get(0).length() * row + col;
    }

    private int[] getIndicesFromCode(int code) {
        int[] indices = new int[2];
        indices[1] = code % grid.get(0).length();
        indices[0] = (code - (code % grid.get(0).length())) / grid.get(0).length();
        return indices;
    }
}
