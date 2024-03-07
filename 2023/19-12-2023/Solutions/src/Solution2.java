import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Solution2 {
    private HashMap<String, String[]> workflows;
    private ArrayList<HashMap<Character,Integer[]>> accepted;
    private long output;
    private Integer[] initialRanges;
    HashMap<Character,Integer[]> initial;

    public Solution2(String filepath) throws IOException {
        this.initialRanges = new Integer[2];
        this.initialRanges[0] = 1;
        this.initialRanges[1] = 4000;
        this.output = 0;
        this.workflows = new HashMap<>();
        this.accepted = new ArrayList<>();
        this.initial = new HashMap<>();
        this.initial.put('x',this.initialRanges);
        this.initial.put('m',this.initialRanges);
        this.initial.put('a',this.initialRanges);
        this.initial.put('s',this.initialRanges);
        Path path = Paths.get(filepath);
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) {
                break;
            }
            String workFlowName = line.split("\\{")[0];
            String[] rules = line.split("\\{")[1].split("}")[0].split(",");
            this.workflows.put(workFlowName,rules);
        }
        scanner.close();
    }

    public void getOutput() {
        compute();
        System.out.println("Part 2: " + output);
    }
    private void compute() {
        useWorkFlow(workflows.get("in"),initial,0);
        for (HashMap<Character,Integer[]> possible : accepted) {
            long product = 1;
            for (Character category : possible.keySet()) {
                product *= (possible.get(category)[1] - possible.get(category)[0] + 1);
            }
            if (product < 0) {
                continue;
            }
            output += product;
        }
    }

    private void useWorkFlow(String[] workFlow, HashMap<Character,Integer[]> possible, int index) {
        String rule = workFlow[index];
        if (rule.split(":").length == 1) {
            if (rule.equals("A")) {
                accepted.add(possible);
            }
            else if (!rule.equals("R")){
                useWorkFlow(workflows.get(rule),possible,0);
            }
        }
        else {
            String condition = rule.split(":")[0];
            String nextOutput = rule.split(":")[1];
            Character conditionCategory = condition.charAt(0);
            String comparator = condition.substring(1,2);
            int compareValue = Integer.parseInt(condition.split(comparator)[1]);
            HashMap<Character,Integer[]> possible1 = new HashMap<>();
            HashMap<Character,Integer[]> possible2 = new HashMap<>();
            for (Character category : possible.keySet()) {
                Integer[] range1 = new Integer[2];
                Integer[] range2 = new Integer[2];
                if (category.equals(conditionCategory)) {
                    if (comparator.equals("<")) {
                        range1[0] = possible.get(category)[0];
                        range1[1] = compareValue - 1;
                        range2[0] = compareValue;
                        range2[1] = possible.get(category)[1];
                    }
                    else {
                        range1[0] = compareValue + 1;
                        range1[1] = possible.get(category)[1];
                        range2[0] = possible.get(category)[0];
                        range2[1] = compareValue;
                    }
                }
                else {
                    range1[0] = possible.get(category)[0];
                    range1[1] = possible.get(category)[1];
                    range2[0] = possible.get(category)[0];
                    range2[1] = possible.get(category)[1];
                }
                possible1.put(category,range1);
                possible2.put(category,range2);
            }
            if (nextOutput.equals("A")) {
                accepted.add(possible1);
            }
            else if (!nextOutput.equals("R")) {
                useWorkFlow(workflows.get(nextOutput),possible1,0);
            }
            useWorkFlow(workFlow,possible2,index + 1);
        }
    }
}
