import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        String input = "input.txt";
        String example = "example.txt";
        String file = input;
        Solution1 solution1 = new Solution1(file);
        solution1.getSolution();
        Solution2 solution2 = new Solution2(file);
        solution2.getSolution();
        Solution3 solution3 = new Solution3(file);
        solution3.getSolution();
    }
}
