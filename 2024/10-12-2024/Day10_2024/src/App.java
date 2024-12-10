import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        String example = "example.txt";
        String input = "input.txt";
        String example2 = "example2.txt";
        String file = input;
        Solution1 solution1 = new Solution1(file);
        solution1.getSolution();
        Solution2 solution2 = new Solution2(file);
        solution2.getSolution();
    }
}
