import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        String example = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\17-12-2023\\example.txt";
        String input = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\17-12-2023\\input.txt";
        Solution1 solution1 = new Solution1(example,1,3);
        solution1.getOutput();
    }
}
