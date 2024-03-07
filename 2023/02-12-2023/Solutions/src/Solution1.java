import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Solution1 {
    public final int max_green = 13;
    public final int max_red = 12;
    public final int max_blue = 14;
    private final String filepath;
    private int output;
    public Solution1(String filepath) {
        this.filepath = filepath;
        this.output = 0;
    }

    public void printOutput() throws IOException {
        this.compute();
        System.out.println(output);
    }
    private void compute() throws IOException {
        Path path = Paths.get(filepath);
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] subsets = line.split(":")[1].split(";");
            int id = Integer.parseInt(line.split(":")[0].substring(5));
            boolean broken = false;
            for (String subset : subsets) {
                String[] colourStrings = subset.split(",");
                for (String colourString : colourStrings) {
                    String s = colourString.replaceAll("\\s", "");
                    int index = 0;
                    for (int i = 0; i < s.length(); i++) {
                        if (!Character.isDigit(s.charAt(i))) {
                            index = i - 1;
                            break;
                        }
                    }
                    int num = Integer.parseInt(s.substring(0,index + 1));
                    if (!checkColourNum(num,s.substring(index + 1))) {
                        broken = true;
                        break;
                    }
                }
                if (broken) {
                    break;
                }
            }
            if (!broken) {
                output += id;
            }
        }
        scanner.close();
    }

    private boolean checkColourNum(int num, String colour) {
        if (colour.equals("green") && num > max_green) {
            return false;
        }
        else if (colour.equals("red") && num > max_red) {
            return false;
        }
        else return !colour.equals("blue") || num <= max_blue;
    }
}
