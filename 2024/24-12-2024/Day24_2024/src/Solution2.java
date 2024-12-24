import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/*
* To calculate z_k from x_k and y_k given carry on c_k-1 from z_k-1 calculation
* x_k XOR y_k -> t_k
* t_k XOR c_k-1 -> z_k
* Need to calculate carry on c_k
* x_k AND y_k -> u_k
* t_k AND c_k-1 -> v_k
* u_k OR v_k -> c_k
* CALCULATIONS START WITH z00 as follows:
* x_0 XOR y_0 -> z_0
* x_0 AND y_0 -> c_0
* CALCULATIONS END WITH z45 as follows:
* u_44 OR v_44 -> z_45
* */
public class Solution2 {
    private HashMap<String,Integer> wireValuesMap;
    private HashMap<Integer,Integer> zWireValuesMap;
    private ArrayList<String[]> calculations;
    private final String AND = "AND";
    private final String OR = "OR";
    private final String XOR = "XOR";

    public Solution2(String file) throws IOException {
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
        StringBuilder s = new StringBuilder();
        ArrayList<String> incorrects = getIncorrectGateOutputs();
        for (String incorrect : incorrects) {
            s.append(",");
            s.append(incorrect);
        }
        System.out.println("Solution 2: " + s.toString().substring(1));
    }

    private ArrayList<String> getIncorrectGateOutputs() {
        computeAllCalcs();
        HashSet<String> incorrects = new HashSet<>();
        String finalZ = (this.zWireValuesMap.keySet().size() <= 10) ? new StringBuilder("z0").append(this.zWireValuesMap.keySet().size() - 1).toString() : new StringBuilder("z").append(this.zWireValuesMap.keySet().size() - 1).toString();
        for (String[] calc : this.calculations) {
            String op = calc[1];
            String res = calc[3];
            String first = calc[0];
            String second = calc[2];
            if (res.startsWith("z") && !res.equals(finalZ) && !op.equals(XOR)) {
                incorrects.add(res);
            }
            else if (!res.startsWith("z") && !first.startsWith("x") && !second.startsWith("x") && !first.startsWith("y") && !second.startsWith("y") && op.equals(XOR)) {
                incorrects.add(res);
            }
            else if (op.equals(AND) && !first.equals("x00") && !second.equals("x00")) {
                for (String[] calc1 : this.calculations) {
                    if ((res.equals(calc1[0]) || res.equals(calc1[2])) && !calc1[1].equals(OR)) {
                        incorrects.add(res);
                        break;
                    }
                }
            }
            else if (op.equals(XOR)) {
                for (String[] calc1 : this.calculations) {
                    if ((res.equals(calc1[0]) || res.equals(calc1[2])) && calc1[1].equals(OR)) {
                        incorrects.add(res);
                        break;
                    }
                }
            }
        }
        ArrayList<String> list = new ArrayList<>(incorrects);
        Collections.sort(list);
        return list;
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
