import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Solution1 {
    private HashSet<String> first;
    private HashSet<String> second;
    private HashMap<String, HashSet<String>> connections;

    public Solution1(String filepath) throws IOException {
        first = new HashSet<>();
        second = new HashSet<>();
        connections = new HashMap<>();
        Path path = Paths.get(filepath);
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            String from = line.split(":")[0];
            String[] tos = line.split(":")[1].trim().split(" ");
            if (!connections.containsKey(from)) {
                connections.put(from,new HashSet<>());
            }
            for (String to : tos) {
                if (!connections.containsKey(to)) {
                    connections.put(to,new HashSet<>());
                }
                connections.get(from).add(to);
                connections.get(to).add(from);
            }
        }
        scanner.close();
        first.addAll(connections.keySet());
    }

    public void getOutput() {
        split();
        System.out.println("Part 1: " + first.size()*second.size());
        /*for (String key : connections.keySet()) {
            System.out.println("Key: " + key);
            for (String to : connections.get(key)) {
                System.out.print(to + " ");
            }
            System.out.println("\n");
        }*/
    }
    private void split() {
        do {
            String chosen = getMaxExternalConnections();
            first.remove(chosen);
            second.add(chosen);
        } while(getTotalExternalConnections() > 3);
    }
    private String getMaxExternalConnections() {
        int max = -1;
        String chosen = "";
        for (String key : first) {
            int count = getExternalConnectionsCount(key);
            if (count > max) {
                max = count;
                chosen = key;
            }
        }
        return chosen;
    }

    private int getTotalExternalConnections() {
        int sum = 0;
        for (String key : first) {
            sum += getExternalConnectionsCount(key);
        }
        return sum;
    }
    private int getExternalConnectionsCount(String key) {
        int count = 0;
        for (String to : connections.get(key)) {
            if (second.contains(to)) {
                count++;
            }
        }
        return count;
    }
}
