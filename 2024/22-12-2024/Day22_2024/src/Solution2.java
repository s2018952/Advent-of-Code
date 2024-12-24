import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Solution2 {
    private ArrayList<Long> initialSecrets;
    private int[][][][] priceSums;
    public Solution2(String file) throws IOException {
        this.initialSecrets = new ArrayList<>();
        this.priceSums = new int[19][19][19][19];
        Path path = Paths.get(file);
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            this.initialSecrets.add(Long.parseLong(scanner.nextLine()));
        }
        scanner.close();
    }

    public void getSolution() {
        long total = 0;
        populate(2000);
        int maxCount = 0;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                for (int k = 0; k < 19; k++) {
                    for (int l = 0; l < 19; l++) {
                        maxCount = Math.max(maxCount,this.priceSums[i][j][k][l]);
                    }
                }
            }
        }
        System.out.println("Solution 2: " + maxCount);
    }

    private void populate(int numChanges) {
        for (Long secretNum : this.initialSecrets) {
            ArrayList<Integer> current = new ArrayList<>();
            boolean[][][][] encountered = new boolean[19][19][19][19];
            long prev = secretNum;
            for (int i = 0; i < numChanges; i++) {
                long next = getNextSecretNumber(prev);
                int nextPrice = Math.floorMod(next,10);
                int prevPrice = Math.floorMod(prev,10);
                int diff = nextPrice - prevPrice;
                if (current.size() == 4) {
                    current.remove(0);
                }
                current.add(diff);
                if (current.size() == 4) {
                    int[] indices = {current.get(0) + 9, current.get(1) + 9, current.get(2) + 9, current.get(3) + 9};
                    if (!encountered[indices[0]][indices[1]][indices[2]][indices[3]]) {
                        this.priceSums[indices[0]][indices[1]][indices[2]][indices[3]] += nextPrice;
                        encountered[indices[0]][indices[1]][indices[2]][indices[3]] = true;
                    }
                }
                prev = next;
            }
        }
    }
    private long getNextSecretNumber(long x) {
        // multiplying by 64 is left shift of binary rep of x by 6
        long temp = x * 64;
        //
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
