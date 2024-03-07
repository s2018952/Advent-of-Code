import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Solution2 {
    private String[] instructions;
    private final int num_boxes = 256;
    private LinkedHashMap[] boxes;
    public Solution2(String filepath) throws IOException {
        this.boxes = new LinkedHashMap[this.num_boxes];
        for (int i = 0; i < this.num_boxes; i++) {
            this.boxes[i] = new LinkedHashMap<>();
        }
        Path path = Paths.get(filepath);
        Scanner scanner = new Scanner(path);
        String line = scanner.nextLine();
        scanner.close();
        this.instructions = line.split(",");
    }

    public void getOutput() {
        int output = 0;
        for (String instruction : instructions) {
            executeInstruction(instruction);
        }
        for (int i = 0; i < num_boxes; i++) {
            output += getBoxFocusingPowerSum(boxes[i],i);
        }
        System.out.println("Part 2: " + output);
    }

    private void executeInstruction(String instruction) {
        String label = instruction.substring(0,getOperationIndex(instruction));
        char operator = instruction.charAt(getOperationIndex(instruction));
        int boxNum = computeHash(label);
        if (operator == '=') {
            int focal_length = Integer.parseInt(instruction.substring(getOperationIndex(instruction) + 1));
            boxes[boxNum].put(label,focal_length);
        }
        else {
            boxes[boxNum].remove(label);
        }
    }

    private int getBoxFocusingPowerSum(LinkedHashMap<String,Integer> box, int boxNum) {
        int sum = 0;
        int slotNum = 0;
        for (String label : box.keySet()) {
            slotNum++;
            int power = 1 + boxNum;
            power *= (slotNum * box.get(label));
            sum += power;
        }
        return sum;
    }

    private int getOperationIndex(String instruction) {
        for (int i = 0; i < instruction.length(); i++){
            if (instruction.charAt(i) == '-' || instruction.charAt(i) == '=') {
                return i;
            }
        }
        return -1;
    }

    private int computeHash(String instruction) {
        int output = 0;
        for (int i = 0; i < instruction.length(); i++) {
            output += (int) instruction.charAt(i);
            output *= 17;
            output %= 256;
        }
        return output;
    }
}
