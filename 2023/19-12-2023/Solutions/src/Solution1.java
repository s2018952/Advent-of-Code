import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Solution1 {
    private HashMap<String, String[]> workflows;
    private ArrayList<HashMap<Character,Integer>> partRatings;
    private int output;

    public Solution1(String filepath) throws IOException {
        this.output = 0;
        this.workflows = new HashMap<>();
        this.partRatings = new ArrayList<>();
        Path path = Paths.get(filepath);
        Scanner scanner = new Scanner(path);
        boolean newLineFound = false;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) {
                newLineFound = true;
            }
            else if (!newLineFound) {
                String workFlowName = line.split("\\{")[0];
                String[] rules = line.split("\\{")[1].split("}")[0].split(",");
                this.workflows.put(workFlowName,rules);
            }
            else if (newLineFound) {
                String[] assignments = line.split("\\{")[1].split("}")[0].split(",");
                HashMap<Character,Integer> partRating = new HashMap<>();
                for (String assignment : assignments) {
                    Character category = assignment.charAt(0);
                    Integer rating = Integer.parseInt(assignment.split("=")[1]);
                    partRating.put(category,rating);
                }
                this.partRatings.add(partRating);
            }
        }
        scanner.close();
    }

    public void getOutput() {
        compute();
        System.out.println("Part 1: " + output);
    }
    private void compute() {
        for (HashMap<Character,Integer> partRating : partRatings) {
            String returnStr = "in";
            while (!returnStr.equals("A") && !returnStr.equals("R")) {
                returnStr = applyWorkFlowToPart(workflows.get(returnStr),partRating);
            }
            if (returnStr.equals("A")) {
                for (Character category : partRating.keySet()) {
                    output += partRating.get(category);
                }
            }
        }
    }
    private String applyWorkFlowToPart(String[] workflow, HashMap<Character,Integer> partRating) {
        for (String rule : workflow) {
            if (rule.split(":").length == 1) {
                return rule;
            }
            String condition = rule.split(":")[0];
            String nextOutput = rule.split(":")[1];
            Character conditionCategory = condition.charAt(0);
            String comparator = condition.substring(1,2);
            int compareValue = Integer.parseInt(condition.split(comparator)[1]);
            if (comparator.equals("<") && partRating.get(conditionCategory) < compareValue) {
                return nextOutput;
            }
            else if (comparator.equals(">") && partRating.get(conditionCategory) > compareValue) {
                return nextOutput;
            }
        }
        return "";
    }
}
