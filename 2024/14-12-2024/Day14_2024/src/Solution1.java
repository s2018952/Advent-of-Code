import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution1 {
    private ArrayList<int[]> robots;
    private int width;
    private int height;
    public Solution1(String file, int width, int height) throws IOException {
        this.width = width;
        this.height = height;
        this.robots = new ArrayList<>();
        Path path = Paths.get(file);
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int[] robot = new int[4];
            robot[0] = Integer.parseInt(line.strip().split(" ")[0].split("=")[1].split(",")[0]);
            robot[1] = Integer.parseInt(line.strip().split(" ")[0].split("=")[1].split(",")[1]);
            robot[2] = Integer.parseInt(line.strip().split(" ")[1].split("=")[1].split(",")[0]);
            robot[3] = Integer.parseInt(line.strip().split(" ")[1].split("=")[1].split(",")[1]);
            this.robots.add(robot);
        }
        scanner.close();
    }

    public void getSolution() {
        int[] counts = getQuadrantCountsAfterTime(100);
        int output = 1;
        for (int i = 0; i < 4; i++) {
            output *= counts[i];
        }
        System.out.println("Solution 1: " + output);
    }
    private int[] getQuadrantCountsAfterTime(int time) {
        int[] counts = new int[4];
        int middleRowIndex = (this.height - 1)/ 2;
        int middleColIndex = (this.width - 1)/2;
        for (int[] robot : this.robots) {
            int[] position = getRobotPositionAfterTime(robot,time);
            if (position[0] == middleColIndex || position[1] == middleRowIndex) {
                continue;
            }
            else if (position[0] < middleColIndex && position[1] < middleRowIndex) {
                counts[0]++;
            }
            else if (position[0] > middleColIndex && position[1] < middleRowIndex) {
                counts[1]++;
            }
            else if (position[0] < middleColIndex && position[1] > middleRowIndex) {
                counts[2]++;
            }
            else {
                counts[3]++;
            }
        }
        return counts;
    }
    private int[] getRobotPositionAfterTime(int[] robot, int time) {
        int current_x = robot[0];
        int current_y = robot[1];
        int dir_x = robot[2];
        int dir_y = robot[3];
        int[] finalPosition = new int[2];
        finalPosition[0] = Math.floorMod(current_x + time*dir_x,this.width);
        finalPosition[1] = Math.floorMod(current_y + time*dir_y,this.height);
        return finalPosition;
    }
}
