import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution1 {
    private ArrayList<ArrayList<String>> patterns;
    private int rowFactor;
    private int colFactor;
    public Solution1(String filepath,int rowFactor,int colFactor) throws IOException {
        this.rowFactor = rowFactor;
        this.colFactor = colFactor;
        this.patterns = new ArrayList<>();
        Path path = Paths.get(filepath);
        Scanner scanner = new Scanner(path);
        ArrayList<String> text = new ArrayList<>();
        int patternCount = 1;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            text.add(line);
            if (line.trim().isEmpty()) {
                patternCount++;
            }
        }
        for (int i = 0; i < patternCount; i++) {
            this.patterns.add(new ArrayList<>());
        }
        int patternCount1 = 1;
        for (String line : text) {
            if (line.trim().isEmpty()) {
                patternCount1++;
            }
            else {
                this.patterns.get(patternCount1 - 1).add(line);
            }
        }
    }

    public void getOutput() {
        int numRowsAbove = 0;
        int numColsLeft = 0;
        for (ArrayList<String> pattern : patterns) {
            ArrayList<Integer[]> horizontals = getHorizontalMirrors(pattern);
            ArrayList<Integer[]> verticals = getVerticalMirrors(pattern);
            for (Integer[] horizontal : horizontals) {
                numRowsAbove += horizontal[1];
            }
            for (Integer[] vertical : verticals) {
                numColsLeft += vertical[1];
            }
        }
        int output = rowFactor * numRowsAbove + colFactor * numColsLeft;
        System.out.println("Part 1: " + output);
    }

    private ArrayList<Integer[]> getVerticalMirrors(ArrayList<String> pattern) {
        ArrayList<Integer[]> verticals = new ArrayList<>();
        ArrayList<String> transpose = getTranspose(pattern);
        for (int i = 0; i < transpose.size() - 1; i++) {
            Integer[] indices = new Integer[2];
            indices[0] = i;
            indices[1] = i + 1;
            if (isMirror(indices,transpose)) {
                verticals.add(indices);
            }
        }
        return verticals;
    }
    private ArrayList<Integer[]> getHorizontalMirrors(ArrayList<String> pattern) {
        ArrayList<Integer[]> horizontals = new ArrayList<>();
        for (int i = 0; i < pattern.size() - 1; i++) {
            Integer[] indices = new Integer[2];
            indices[0] = i;
            indices[1] = i + 1;
            if (isMirror(indices,pattern)) {
                horizontals.add(indices);
            }
        }
        return horizontals;
    }

    private boolean isMirror(Integer[] rowIndexes, ArrayList<String> pattern) {
        int lower = rowIndexes[0];
        int higher = rowIndexes[1];
        while (lower >= 0 && higher < pattern.size()) {
            if (!pattern.get(lower).equals(pattern.get(higher))) {
                return false;
            }
            lower--;
            higher++;
        }
        return true;
    }
    private ArrayList<String> getTranspose(ArrayList<String> pattern) {
        ArrayList<String> transpose = new ArrayList<>();
        for (int j = 0; j < pattern.get(0).length(); j++) {
            StringBuilder line = new StringBuilder();
            for (String s : pattern) {
                line.append(s.charAt(j));
            }
            transpose.add(line.toString());
        }
        return transpose;
    }
}
