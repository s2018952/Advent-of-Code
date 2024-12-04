import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

// Tried to implement to solve second problem day 2 2024
// Didn't return correct output
public class Solution2 {
    private ArrayList<ArrayList<Integer>> records;

    public Solution2(String filepath) throws IOException {
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
            ArrayList<Integer> skipIndices = new ArrayList<>();
            if (checkRecordSafe(record,1,3,skipIndices)) {
                safes++;
            }
        }
        System.out.println("Solution 2: " + safes);
    }

    private boolean checkRecordSafe(ArrayList<Integer> record, int minDiff, int maxDiff, ArrayList<Integer> skipIndices) {
        if (record.size() <= 1) {
            return true;
        }
        ArrayList<Integer> record1 = new ArrayList<>(record);
        int skipIndex = -1;
        if (!skipIndices.isEmpty()) {
            skipIndex = skipIndices.get(0);
            if (skipIndices.size() > 1) {
                return false;
            }
        }
        if (skipIndex >= 0) {
            record1.remove(skipIndex);
        }
        boolean increasing = false;
        boolean decreasing = false;
        int previous = record1.get(0);
        for (int i = 1; i < record1.size(); i++) {
            ArrayList<Integer> prevSkipIndices = new ArrayList<>(skipIndices);
            ArrayList<Integer> currSkipIndices = new ArrayList<>(skipIndices);
            prevSkipIndices.add(i - 1);
            currSkipIndices.add(i);
            int current = record1.get(i);
            if (current > previous) {
                increasing = true;
            }
            else if (current < previous) {
                decreasing = true;
            }
            if ((increasing && decreasing) || (current == previous)) {
                return checkRecordSafe(record1,minDiff,maxDiff,prevSkipIndices) || checkRecordSafe(record,minDiff,maxDiff,currSkipIndices);
            }
            int diff = Math.abs(current - previous);
            if (diff < minDiff || diff > maxDiff) {
                return checkRecordSafe(record1,minDiff,maxDiff,prevSkipIndices) || checkRecordSafe(record,minDiff,maxDiff,currSkipIndices);
            }
            previous = current;
        }
        return true;
    }
}
