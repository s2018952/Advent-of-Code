import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        String example = "example.txt";
        String input = "input.txt";
        int exampleWidth = 11;
        int exampleHeight = 7;
        int inputWidth = 101;
        int inputHeight = 103;
        Solution1 solution1 = new Solution1(input,inputWidth,inputHeight);
        solution1.getSolution();
        Solution2 solution2 = new Solution2(input,inputWidth,inputHeight);
        solution2.getSolution();
    }
}
