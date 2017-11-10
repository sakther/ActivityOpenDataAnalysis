import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String inDir = "C:\\Users\\sakther\\DATA\\OPEN_DATA_SET_ACTIVITY\\ADL_Dataset\\HMP_Dataset\\";
//        String outDir = "C:\\Users\\sakther\\DATA\\OPEN_DATA_SET_ACTIVITY\\ADL_Dataset\\MD2K_format\\";
        String outDir = "C:\\Users\\sakther\\DATA\\OPEN_DATA_SET_ACTIVITY\\Extrasensory-data\\ExtraSensory\\streamprocessor-like-data\\";
//        ConvertADLDataSetToStreamProcessorData.createIfNotExist(outDir);
//        ConvertADLDataSetToStreamProcessorData.doConvert(inDir, outDir);
        System.out.println("start");

//        String featureFileName = "ADL-activity-featurefile2.csv";
        String featureFileName = "Extrasensory-activity-featurefile-all_1000_simple.csv";

        int windowSize = 10 * 1000; // 10 seconds
        String featureNames = "mean,median,sd,sd_x,sd_y,sd_z,skew,kurt,rateOfChange,actualActivity,activityLabel";
        Config.fileWrite(outDir + featureFileName, featureNames);
        ActivityFeatures.doProcess(outDir, outDir + featureFileName, windowSize);

    }

}
