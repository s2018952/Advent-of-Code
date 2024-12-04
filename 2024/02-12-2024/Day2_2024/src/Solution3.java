import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

// Solves second problem day 2 2024
public class Solution3 {
    private ArrayList<ArrayList<Integer>> records;

    public Solution3(String filepath) throws IOException {
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
            if (checkRecordSafe(record,1,3,-1)) {
                safes++;
            }
            else {
                for (int j = 0; j < record.size(); j++) {
                    if (checkRecordSafe(record,1,3,j)) {
                        safes++;
                        break;
                    }
                }
            }
        }
        System.out.println("Solution 3: "+ safes);
    }

    private boolean checkRecordSafe(ArrayList<Integer> record, int minDiff, int maxDiff, int removeIndex) {
        if (record.size() <= 1) {
            return true;
        }
        boolean increasing = false;
        boolean decreasing = false;
        int prevIndex = (removeIndex != 0) ? 0 : 1;
        int previous = record.get(prevIndex);
        for (int i = prevIndex + 1; i < record.size(); i++) {
            if (i == removeIndex) {
                continue;
            }
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
