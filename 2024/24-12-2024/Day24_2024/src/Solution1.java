import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Solution1 {
    private HashMap<String,Integer> wireValuesMap;
    private HashMap<Integer,Integer> zWireValuesMap;
    private ArrayList<String[]> calculations;
    private final String AND = "AND";
    private final String OR = "OR";
    private final String XOR = "XOR";

    public Solution1(String file) throws IOException {
        Path path = Paths.get(file);
        Scanner scanner = new Scanner(path);
        this.wireValuesMap = new HashMap<>();
        this.zWireValuesMap = new HashMap<>();
        this.calculations = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            line = line.strip();
            if (line.contains(":")) {
                String wire = line.split(":")[0].strip();
                int wireValue = Integer.parseInt(line.split(":")[1].strip());
                this.wireValuesMap.put(wire,wireValue);
                if (wire.startsWith("z")) {
                    int zIndex = Integer.parseInt(wire.substring(1));
                    this.zWireValuesMap.put(zIndex,wireValue);
                }
            }
            else if (line.contains("->")) {
                String first = line.split("->")[0].strip().split(" ")[0];
                String second = line.split("->")[0].strip().split(" ")[2];
                String op = line.split("->")[0].strip().split(" ")[1];
                String output = line.split("->")[1].strip();
                String[] calc = {first,op,second,output};
                this.calculations.add(calc);
            }
        }
        scanner.close();
    }

    public void getSolution() {
        computeAllCalcs();
        long val = getZNumber();
        System.out.println("Solution 1: " + val);
    }
    private long getZNumber() {
        StringBuilder s = new StringBuilder();
        for (int i = this.zWireValuesMap.keySet().size() - 1; i >= 0; i--) {
            int val = this.zWireValuesMap.get(i);
            s.append(val);
        }
        return Long.parseLong(s.toString(),2);
    }
    private void computeAllCalcs() {
        int count = 0;
        while (count < this.calculations.size()) {
            for (String[] calc : this.calculations) {
                if (checkIfCalcShouldBeComputed(calc)) {
                    computeCalc(calc);
                    count++;
                }
            }
        }
    }
    private void computeCalc(String[] calc) {
        int firstVal = this.wireValuesMap.get(calc[0]);
        int secondVal = this.wireValuesMap.get(calc[2]);
        int calcVal = -1;
        if (calc[1].equals(AND)) {
            calcVal = firstVal & secondVal;
        }
        else if (calc[1].equals(OR)) {
            calcVal = firstVal | secondVal;
        }
        else {
            calcVal = firstVal ^ secondVal;
        }
        this.wireValuesMap.put(calc[3],calcVal);
        if (calc[3].startsWith("z")) {
            int zIndex = Integer.parseInt(calc[3].substring(1));
            this.zWireValuesMap.put(zIndex,calcVal);
        }
    }
    private boolean checkIfCalcShouldBeComputed(String[] calc) {
        return this.wireValuesMap.containsKey(calc[0]) && this.wireValuesMap.containsKey(calc[2]) && !this.wireValuesMap.containsKey(calc[3]);
    }
}
