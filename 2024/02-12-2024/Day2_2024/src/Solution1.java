import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

// Solves first problem day 2 2024
public class Solution1 {
    private ArrayList<ArrayList<Integer>> records;

    public Solution1(String filepath) throws IOException {
        Path path = Paths.get(filepath);
        Scanner scanner = new Scanner(path);
        this.records = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(" ");
            ArrayList<Integer> record = new ArrayList<>();
            for (String num : line) {
                record.add(Integer.parseInt(num));
            }
            this.records.add(record);
        }
        scanner.close();
    }

    public void getSolution() {
        int safes = 0;
        for (ArrayList<Integer> record : this.records) {
            if (checkRecordSafe(record,1,3)) {
                safes++;
            }
        }
        System.out.println("Solution 1: "+ safes);
    }

    private boolean checkRecordSafe(ArrayList<Integer> record, int minDiff, int maxDiff) {
        if (record.size() <= 1) {
            return true;
        }
        boolean increasing = false;
        boolean decreasing = false;
        int previous = record.get(0);
        for (int i = 1; i < record.size(); i++) {
            int current = record.get(i);
            if (current > previous) {
                increasing = true;
            }
            else if (current < previous) {
                decreasing = true;
            }
            if ((increasing && decreasing) || (current == previous)) {
                return false;
            }
            int diff = Math.abs(current - previous);
            if (diff < minDiff || diff > maxDiff) {
                return false;
            }
            previous = current;
        }
        return true;
    }
}
