import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        String example = "example.txt";
        String example2 = "example2.txt";
        String input = "input.txt";
        Solution1 solution1 = new Solution1(input);
        solution1.getSolution();
        Solution2 solution2 = new Solution2(input);
        solution2.getSolution();
    }
}
