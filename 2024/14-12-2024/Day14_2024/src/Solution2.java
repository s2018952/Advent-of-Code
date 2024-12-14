import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution2 {
    private ArrayList<int[]> robots;
    private int width;
    private int height;
    public Solution2(String file, int width, int height) throws IOException {
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
        int time = getTimeForAllUnique();
        System.out.println("Solution 2: " + time);
    }
    private int getTimeForAllUnique() {
        for (int time = 0; time < 10000; time++) {
            boolean[][] robotHere = new boolean[this.height][this.width];
            for (int[] robot : this.robots) {
                int[] pos = getRobotPositionAfterTime(robot,time);
                robotHere[pos[1]][pos[0]]= true;
            }
            int uniques = 0;
            for (int i = 0; i < this.height; i++) {
                for (int j = 0; j < this.width; j++) {
                    if (robotHere[i][j]) {
                        uniques++;
                    }
                }
            }
            if (uniques == this.robots.size()) {
                return time;
            }
        }
        return -1;
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
