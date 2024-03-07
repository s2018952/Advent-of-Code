import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution2 {
    private ArrayList<ArrayList<Integer>> sequences;
    private int output;
    public Solution2(String filepath) throws IOException {
        this.output = 0;
        this.sequences = new ArrayList<>();
        Path path = Paths.get(filepath);
        Scanner scanner =new Scanner(path);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] numbers = line.split(" ");
            ArrayList<Integer> sequence = new ArrayList<>();
            for (String number : numbers) {
                sequence.add(Integer.parseInt(number));
            }
            this.sequences.add(sequence);
        }
        scanner.close();
    }

    public void getOutput() {
        this.computeOutput();
        System.out.println("Part 2: " + output);
    }
    private void computeOutput() {
        for (ArrayList<Integer> sequence : sequences) {
            output += getPreviousNumber(sequence);
        }
    }

    private int getPreviousNumber(ArrayList<Integer> sequence) {
        if (isZeroSequence(sequence)) {
            return 0;
        }
        else {
            ArrayList<Integer> diffs = getDifferenceSequence(sequence);
            int diffsPrevious = getPreviousNumber(diffs);
            return sequence.get(0) - diffsPrevious;
        }
    }

    private ArrayList<Integer> getDifferenceSequence(ArrayList<Integer> sequence) {
        ArrayList<Integer> diffs = new ArrayList<>();
        for (int i = 0; i < sequence.size() - 1; i++) {
            int diff = sequence.get(i + 1) - sequence.get(i);
            diffs.add(diff);
        }
        return diffs;
    }

    private boolean isZeroSequence(ArrayList<Integer> sequence) {
        for (Integer num : sequence) {
            if (num != 0) {
                return false;
            }
        }
        return true;
    }
}
