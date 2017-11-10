import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by nsaleheen on 9/14/2017.
 */
public class ActivityFeatures {
    public static void doProcess(String dir, String featureFileName, int windowSize) {

        for (String label : Config.EXTRASENSORY_labels_Simple) {
            String labelDir = dir + label;
            System.out.println(label);
            File dataFolders = new File(labelDir);
            int cnt = 0;
            int maxCnt = 5000;
            if ("LOW".equals(Config.EXTRASENSORY_labelMap.get(label)))
                maxCnt = 1000;

            for (File dataFolder : dataFolders.listFiles()) {
                if (!dataFolder.isDirectory()) continue;

                if (cnt>= maxCnt) break;
                cnt++;
                List<double[]> txyz = parseDataFileTXYZ(labelDir + "\\" + dataFolder.getName() + "\\right-wrist-accelxyz.csv");
//                if (label.endsWith("_MODEL"))
//                    computeFeatures(txyz, featureFileName, windowSize, label.substring(0, label.length() - 6), Config.ADL_labelMap.get(label));
//                else
                computeFeatures(txyz, featureFileName, windowSize, label, Config.EXTRASENSORY_labelMap.get(label));
            }
        }
    }

    private static void computeFeatures(List<double[]> txyz, String featureFileName, int windowSize, String label, String activityLabel) {

        List<double[]> subList = new ArrayList<>();

        for (int i = 0; i < txyz.size(); i++) {
            subList.add(txyz.get(i));
            if (txyz.get(i)[0] - subList.get(0)[0] >= windowSize) {
                double[] f = doFeatureCalculations(subList);
                printFeatures(featureFileName, subList.get(0)[0], f, label, activityLabel);
                subList = new ArrayList<>();
            }
        }
    }

    private static void printFeatures(String featureFileName, double time, double[] f, String label, String activityLabel) {
        String str = f[0] + "";
        for (int i = 1; i < f.length; i++)
            str = str + "," + f[i];
        str = str + "," + label + "," + activityLabel;

        Config.fileWrite(featureFileName, str);
    }

    /*
    *
    * */
    private static double[] doFeatureCalculations(List<double[]> data) {

        double[] t = new double[data.size()];
        double[] x = new double[data.size()];
        double[] y = new double[data.size()];
        double[] z = new double[data.size()];

        double[] mag = new double[data.size()];

        for (int i = 0; i < data.size(); i++) {
            double[] val = data.get(i);
            mag[i] = magnitude(val[1], val[2], val[3]);

            t[i] = val[0];
            x[i] = val[1];
            y[i] = val[2];
            z[i] = val[3];
        }

        double mean = StatisticsMathUtil.avg(mag);
        double median = StatisticsMathUtil.median(mag);
        double sd = StatisticsMathUtil.stdev(mag);
        double sd_x = StatisticsMathUtil.stdev(x);
        double sd_y = StatisticsMathUtil.stdev(y);
        double sd_z = StatisticsMathUtil.stdev(z);
        double skew = StatisticsMathUtil.skew(mag);
        double kurt = StatisticsMathUtil.kurt(mag);
        double rateOfChange = getRateOfChange(t, mag);

        return new double[]{mean, median, sd, sd_x, sd_y, sd_z, skew, kurt, rateOfChange};
    }

    private static double getRateOfChange(double[] t, double[] data) {
        double roc = 0;
        for (int i = 1; i < data.length; i++)
            roc += ((data[i] - data[i - 1]) / (t[i] - t[i - 1]));
        return roc / (data.length - 1);
    }

    private static double magnitude(double x, double y, double z) {
        return Math.sqrt(x * x + y * y + z * z);
    }

    private static List<double[]> parseDataFileTXYZ(String filename) {
        List<double[]> txyz = new ArrayList<>();
        try {
            Scanner in = new Scanner(new File(filename));
            while (in.hasNext()) {
                String line = in.nextLine();
                String[] toks = line.split(",");
                double[] val = new double[]{Double.parseDouble(toks[0]), Double.parseDouble(toks[1]), Double.parseDouble(toks[2]), Double.parseDouble(toks[3])};
                txyz.add(val);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return txyz;
    }


}
