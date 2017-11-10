import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sakther on 9/14/2017.
 */
public class Config {
    public static String[] ADL_labels = {
            "Brush_teeth",
            "Climb_stairs",
            "Climb_stairs_MODEL",
            "Comb_hair",
            "Descend_stairs",
            "Drink_glass",
            "Drink_glass_MODEL",
            "Eat_meat",
            "Eat_soup",
            "Getup_bed",
            "Getup_bed_MODEL",
            "Liedown_bed",
            "Pour_water",
            "Pour_water_MODEL",
            "Sitdown_chair",
            "Sitdown_chair_MODEL",
            "Standup_chair",
            "Standup_chair_MODEL",
            "Use_telephone",
            "Walk",
            "Walk_MODEL"
    };

    public static String[] EXTRASENSORY_labels = {
            "LYING DOWN",
            "SLEEPING",
            "CLEANING",
            "COMPUTER WORK",
            "COOKING",
            "DOING LAUNDRY",
            "EATING",
            "LAB WORK",
            "SHOPPING",
            "WASHING DISHES",
            "WATCHING TV",
            "STAIRS - GOING DOWN",
            "STAIRS - GOING UP",
            "STROLLING",
            "walking",
            "exercise",
            "running",
            "BICYCLING"

    };
    public static String[] EXTRASENSORY_labels_Simple = {
            "LYING DOWN",
            "SLEEPING",
            "CLEANING",
            "COMPUTER WORK",
            "COOKING",
            "DOING LAUNDRY",
            "EATING",
            "LAB WORK",
            "SHOPPING",
            "WASHING DISHES",
//            "WATCHING TV",
            "STAIRS - GOING DOWN",
            "STAIRS - GOING UP",
//            "STROLLING",
            "walking",
            "exercise",
            "running",
            "BICYCLING"

    };

    static String NO_ACT = "NO_ACT";
    static String LOW = "LOW";
    static String MOD = "MODERATE";
    static String HIGH = "HIGH";

    public static Map<String, String> createEXTRASENSORYMap() {
        Map<String, String> myMap = new HashMap<String, String>();
        myMap.put("LYING DOWN", NO_ACT);
        myMap.put("SLEEPING", NO_ACT);
        myMap.put("CLEANING", LOW);
        myMap.put("COMPUTER WORK", LOW);
        myMap.put("COOKING", LOW);
        myMap.put("DOING LAUNDRY", LOW);
        myMap.put("EATING", LOW);
        myMap.put("LAB WORK", LOW);
        myMap.put("SHOPPING", LOW);
        myMap.put("WASHING DISHES", LOW);
        myMap.put("WATCHING TV", LOW);
        myMap.put("STAIRS - GOING DOWN", MOD);
        myMap.put("STAIRS - GOING UP", MOD);
        myMap.put("STROLLING", MOD);
        myMap.put("walking", MOD);
        myMap.put("exercise", HIGH);
        myMap.put("running", HIGH);
        myMap.put("BICYCLING", HIGH);
        return myMap;
    }

    public static Map<String, String> EXTRASENSORY_labelMap = createEXTRASENSORYMap();

    public static Map<String, String> createMap() {
        Map<String, String> myMap = new HashMap<String, String>();
        myMap.put("Brush_teeth", LOW);
        myMap.put("Climb_stairs", MOD);
        myMap.put("Climb_stairs_MODEL", MOD);
        myMap.put("Comb_hair", LOW);
        myMap.put("Descend_stairs", MOD);
        myMap.put("Drink_glass", LOW);
        myMap.put("Drink_glass_MODEL", LOW);
        myMap.put("Eat_meat", LOW);
        myMap.put("Eat_soup", LOW);
        myMap.put("Getup_bed", LOW);
        myMap.put("Getup_bed_MODEL", LOW);
        myMap.put("Liedown_bed", NO_ACT);
        myMap.put("Pour_water", LOW);
        myMap.put("Pour_water_MODEL", LOW);
        myMap.put("Sitdown_chair", NO_ACT);
        myMap.put("Sitdown_chair_MODEL", NO_ACT);
        myMap.put("Standup_chair", LOW);
        myMap.put("Standup_chair_MODEL", LOW);
        myMap.put("Use_telephone", LOW);
        myMap.put("Walk", MOD);
        myMap.put("Walk_MODEL", MOD);
        return myMap;
    }

    public static Map<String, String> ADL_labelMap = createMap();

    public static void fileWrite(String filename, String text) {

        try {
            FileWriter fw = new FileWriter(filename, true); //the true will append the new data
            fw.write(text + "\n");//appends the string to the file
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }


}
