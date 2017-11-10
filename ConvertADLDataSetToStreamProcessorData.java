import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by sakther on 9/13/2017.
 */
public class ConvertADLDataSetToStreamProcessorData {

    /*
    * inDir = input directory -- ADL dataset
    * outDir = output directory -- stream-processor like data
    * */
    public static void doConvert(String inDir, String outDir) {

        File filefolder = new File(inDir);

        for (final File labelDir : filefolder.listFiles()) {
            String dirName = labelDir.getName() + "\\";
            if (!labelDir.isDirectory()) continue;

            File fileList = new File(inDir + dirName);

            for (File f : fileList.listFiles()) {
                if (f.isDirectory()) continue;

                String fName = f.getName();
                String fileNameWtDir = inDir + dirName + fName;

                List<double[]> xyz = readDataAndGetSIValues(fileNameWtDir);
                List<double[]> txyz = changeSamplingRateAndAddTIme(xyz); // 32hz to 16hz
                createIfNotExist(outDir + dirName);
                wristeIntoFiles(txyz, outDir + dirName + fName.substring(0, fName.length()-3) + "\\");

            }
        }
    }

    private static void wristeIntoFiles(List<double[]> txyz, String dir) {
        createIfNotExist(dir);
        for (int i=0; i<txyz.size(); i++) {
            double[] vals = txyz.get(i);
            Config.fileWrite(dir+"right-wrist-accelx.csv", (int)vals[0] + ","+ vals[1]);
            Config.fileWrite(dir+"right-wrist-accely.csv", (int)vals[0] + ","+ vals[2]);
            Config.fileWrite(dir+"right-wrist-accelz.csv", (int)vals[0] + ","+ vals[3]);
            Config.fileWrite(dir+"right-wrist-accelxyz.csv", (int)vals[0] + ","+ vals[1] + ","+ vals[2] + ","+ vals[3]);
        }
    }

    public static void createIfNotExist(String outputPath) {
        File f = new File(outputPath);
        if (!f.exists()) f.mkdir();
    }

    private static List<double[]> changeSamplingRateAndAddTIme(List<double[]> xyz) {
        List<double[]> txyz = new ArrayList<>();
        double t = 0;
        double delta = 1000.0/16;
        for (int i=0; i<xyz.size(); i++) {
            if (i%2==1) continue;
            double[] values = new double[]{t, xyz.get(i)[0], xyz.get(i)[1], xyz.get(i)[2]};
            txyz.add(values);
            t+=delta;
        }
        return txyz;
    }

    private static List<double[]> readDataAndGetSIValues(String fileName) {
        List<double[]> xyz = new ArrayList<>();
        try {
            Scanner in = new Scanner(new File(fileName));
            while (in.hasNext()) {
                int x = in.nextInt();
                int y = in.nextInt();
                int z = in.nextInt();
                double[] values = new double[]{convertSI(x), convertSI(y), convertSI(z) };
                xyz.add(values);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return xyz;
    }

    private static double convertSI(int coded_val) {
        return -1.5 + (1.0*coded_val/63)*3;

    }
}
