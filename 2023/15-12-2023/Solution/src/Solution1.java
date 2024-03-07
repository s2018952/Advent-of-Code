import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution1 {
    private String[] instructions;
    public Solution1(String filepath) throws IOException {
        Path path = Paths.get(filepath);
        Scanner scanner = new Scanner(path);
        String line = scanner.nextLine();
        scanner.close();
        this.instructions = line.split(",");
    }

    public void getOutput() {
        int output = 0;
        for (String instruction : instructions) {
            output += computeHash(instruction);
        }
        System.out.println("Part 1: " + output);
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
