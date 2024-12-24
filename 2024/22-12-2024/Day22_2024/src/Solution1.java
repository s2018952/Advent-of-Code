import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution1 {

    private ArrayList<Long> initialSecrets;
    public Solution1(String file) throws IOException {
        this.initialSecrets = new ArrayList<>();
        Path path = Paths.get(file);
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            this.initialSecrets.add(Long.parseLong(scanner.nextLine()));
        }
        scanner.close();
    }

    public void getSolution() {
        long total = 0;
        for (Long num : this.initialSecrets) {
            total += getNextNthSecretNumber(num,2000);
        }
        System.out.println("Solution 1: " + total);
    }
    private long getNextNthSecretNumber(long x, long N) {
        long temp = x;
        for (int i = 0; i < N; i++) {
            temp = getNextSecretNumber(temp);
        }
        return temp;
    }
    private long getNextSecretNumber(long x) {
        // multiplying by 64 is left shift of binary rep of x by 6
        long temp = x * 64;
        long x1 = prune(mix(temp,x));
        // dividing by 32 is right shift of binary rep by 5
        long temp1 = x1 / 32;
        long x2 = prune(mix(temp1,x1));
        // multiplying by 2048 is left shift of binary rep by 11
        long temp2 = x2 * 2048;
        long x3 = prune(mix(temp2,x2));
        return x3;
    }

    // bitwise XOR
    private long mix(long x, long y) {
        return x ^ y;
    }

    // 16777216 is 2**24
    // prune removes values from the 2**24 position onwards in binary rep
    // therefore highest power of 2 in binary rep is 2**23
    // so binary rep at most 24 digits long after prune
    private long prune(long x) {
        return Math.floorMod(x,16777216);
    }
}
