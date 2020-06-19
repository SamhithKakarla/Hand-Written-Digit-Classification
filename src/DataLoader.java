import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DataLoader {

    private static String normalizeLineBreaks(String s) {
        s= s.replace('\u00A0',' '); // remove non-breaking whitespace characters
        s= s.replace('\u2007',' ');
        s= s.replace('\u202F',' ');
        s= s.replace('\uFEFF',' ');

        return s.replace("\r\n", "\n").replace('\r', '\n');
    }

    public static String readFileAsString(String filepath) {
        ClassLoader classLoader = DataLoader.class.getClassLoader();
        File file = new File(classLoader.getResource(filepath).getFile());

        // Read File Content
        String content = "";
        try {
            content = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            System.err.println("FILE NOT FOUND: " + filepath);
            e.printStackTrace();
        }

        return content;
    }

    public static List<DataPoint> loadHeartDiseaseData(String filepath) {
        /* NOTE:  THIS IS ONLY FOR LOADING HEART DISEASE DATA; NOT FOR HAND-WRITTEN DIGIT RECOGNITION */
        String data = normalizeLineBreaks(readFileAsString(filepath));
        String[] lines = data.split("\n");

        // create storage for data
        ArrayList<DataPoint> dataset = new ArrayList<>();

        for (int a = 0; a < lines.length; a++) {
            // TODO: create DataPoint objects here!
        }

        return dataset;
    }

    public static void splitDataIntoTrainingAndTest(List<DataPoint> allData, List<DataPoint> emptyTrainingList, List<DataPoint> emptyTestList, double percentTraining) {
        Collections.shuffle(allData);   // This randomizes the order of allData

        for (int i = 0; i < (int)(allData.size()*percentTraining); i++) {
            emptyTrainingList.add(allData.get(i));
        }

        for (int i = (int)(allData.size()*percentTraining);i < allData.size(); i++) {
            emptyTestList.add(allData.get(i));
        }
    }

    public static List<DataPoint> loadMNistData(String filepath) {
        String data = normalizeLineBreaks(readFileAsString(filepath));
        String[] lines = data.split("\n");

        // create storage for data
        ArrayList<DataPoint> dataset = new ArrayList<>();

        for (int a = 0; a < lines.length; a++) {
            String[] coords = lines[a].split(",");
            String label = coords[0];
            double[] featureVector = new double[coords.length - 1];
            for (int i = 0; i < featureVector.length; i++) {
                featureVector[i] = Double.parseDouble(coords[i + 1]);
            }

            DataPoint point = new DataPoint(label, featureVector);
            dataset.add(point);
        }

        return dataset;
    }
}