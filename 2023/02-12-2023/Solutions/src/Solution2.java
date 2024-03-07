import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class Solution2 {

    private final String filepath;
    private int output;
    public Solution2(String filepath) {
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
            HashMap<String,Integer> counts = new HashMap<>();
            String[] subsets = line.split(":")[1].split(";");
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
                    String colour = s.substring(index + 1);
                    if (counts.containsKey(colour)) {
                        counts.put(colour, Math.max(counts.get(colour),num));
                    }
                    else {
                        counts.put(colour,num);
                    }
                }
            }
            int product = 1;
            for (Integer value : counts.values()) {
                product *= value;
            }
            output += product;
        }
        scanner.close();
    }

}
