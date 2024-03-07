import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Solution2 {
    private ArrayList<Long> seeds;
    private ArrayList<Long[]> seed_to_soil;
    private ArrayList<Long[]> soil_to_fertilizer;
    private ArrayList<Long[]> fertilizer_to_water;
    private ArrayList<Long[]> water_to_light;
    private ArrayList<Long[]> light_to_temperature;
    private ArrayList<Long[]> temperature_to_humidity;
    private ArrayList<Long[]> humidity_to_location;
    private HashMap<Long,Long> seed_to_location;
    private String seedsLine;

    public Solution2 (String filepath) throws IOException {
        seeds = new ArrayList<>();
        seed_to_soil = new ArrayList<>();
        soil_to_fertilizer = new ArrayList<>();
        fertilizer_to_water = new ArrayList<>();
        water_to_light = new ArrayList<>();
        light_to_temperature = new ArrayList<>();
        temperature_to_humidity = new ArrayList<>();
        humidity_to_location = new ArrayList<>();
        seed_to_location = new HashMap<>();

        Path path = Paths.get(filepath);
        Scanner scanner = new Scanner(path);
        ArrayList<String> text = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            text.add(line);
        }
        scanner.close();
        int caseNum = 0;
        for (int i = 0; i < text.size(); i++) {
            String line = text.get(i);
            if (i == 0) {
                this.seedsLine = line;
                //String[] numStrings = line.split(":")[1].trim().split(" ");
                //for (int k = 0; k < numStrings.length; k += 2) {
                //    //System.out.println(numString);
                //    long start = Long.parseLong(numStrings[k]);
                //    long startRange = Long.parseLong(numStrings[k + 1]);
                //    for (long m = start; m < start + startRange; m++) {
                //        seeds.add(m);
                //    }
                //}
            }
            else if (line.trim().isEmpty()) {
                i++;
                caseNum++;
            }
            else {
                Long[] numMap = new Long[3];
                //System.out.println("new numMap");
                for (int j = 0; j < 3; j++) {
                    numMap[j] = Long.parseLong(line.split(" ")[j]);
                    //System.out.println(numMap[j]);
                }
                switch (caseNum) {
                    case 1 -> seed_to_soil.add(numMap);
                    case 2 -> soil_to_fertilizer.add(numMap);
                    case 3 -> fertilizer_to_water.add(numMap);
                    case 4 -> water_to_light.add(numMap);
                    case 5 -> light_to_temperature.add(numMap);
                    case 6 -> temperature_to_humidity.add(numMap);
                    case 7 -> humidity_to_location.add(numMap);
                }
            }
        }
    }

    public void getOutput() {
        for (Long seed : seeds) {
            long location = getLocation(seed);
            seed_to_location.put(seed,location);
        }
        long minLocation = Long.MAX_VALUE;
        String[] numStrings = seedsLine.split(":")[1].trim().split(" ");
        for (int i = 0; i < numStrings.length; i += 2) {
            long start = Long.parseLong(numStrings[i]);
            long startRange = Long.parseLong(numStrings[i + 1]);
            for (long m = start; m < start + startRange; m++) {
                long location = getLocation(m);
                minLocation = Math.min(minLocation,location);
            }
        }
        //long minLocation = Collections.min(seed_to_location.values());
        System.out.println(minLocation);
    }

    private long getMapping(Long source, ArrayList<Long[]> maps) {
        for (Long[] map : maps) {
            long destination_start = map[0];
            long source_start = map[1];
            long range = map[2];
            if (source >= source_start && source < source_start + range) {
                long diff = source - source_start;
                return destination_start + diff;
            }
        }
        return source;
    }

    private long getLocation(Long seed) {
        long soil = getMapping(seed,seed_to_soil);
        long fertilizer = getMapping(soil,soil_to_fertilizer);
        long water = getMapping(fertilizer,fertilizer_to_water);
        long light = getMapping(water,water_to_light);
        long temperature = getMapping(light,light_to_temperature);
        long humidity = getMapping(temperature,temperature_to_humidity);
        long location = getMapping(humidity,humidity_to_location);
        //System.out.println(soil);
        return location;
    }
}
