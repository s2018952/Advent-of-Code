import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        String example = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\21-12-2023\\example.txt";
        String input = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\21-12-2023\\input.txt";
        Solution1 solution1 = new Solution1(input,64);
        solution1.getOutput();
        Solution2 solution2 = new Solution2(input,26501365);
        solution2.getOutput();
    }
}
