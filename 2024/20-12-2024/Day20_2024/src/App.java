import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        String input = "input.txt";
        String example = "example.txt";
        String file = example;
        Solution1 solution1 = new Solution1(input);
        solution1.getSolution();
        Solution2 solution2 = new Solution2(input);
        solution2.getSolution();
    }
}
