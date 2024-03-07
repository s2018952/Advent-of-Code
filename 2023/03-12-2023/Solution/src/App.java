import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        String input_filepath = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\03-12-2023\\input.txt";
        String example_filepath = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\03-12-2023\\example.txt";
        Solution2 solution2 = new Solution2(input_filepath);
        solution2.printOutput();
    }
}
