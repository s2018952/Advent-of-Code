import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        String example = "example.txt";
        String input = "input.txt";
        int exampleGridWidth = 7;
        int exampleGridHeight = 7;
        int inputGridWidth = 71;
        int inputGridHeight = 71;
        int exampleNumberBytesRead = 12;
        int inputNumberBytesRead = 1024;
        Solution1 solution1 = new Solution1(input,inputGridWidth,inputGridHeight,inputNumberBytesRead);
        solution1.getSolution();
        Solution2 solution2 = new Solution2(input,inputGridWidth,inputGridHeight);
        solution2.getSolution();
    }
}
