import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        String example = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\11-12-2023\\example.txt";
        String input = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\11-12-2023\\input.txt";
        Solution1 solution1 = new Solution1(input);
        solution1.printOutput();
        Solution2 solution2 = new Solution2(input,1000000);
        solution2.printOutput();
    }
}
