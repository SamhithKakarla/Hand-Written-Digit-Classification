import java.util.ArrayList;
import java.util.List;

public class TestHeartDiseaseClassifier {
    public static void main(String[] args) {
        Classifier classifier;
        String prediction = "";

        classifier = new Classifier(5);
        List<DataPoint> allData = DataLoader.loadHeartDiseaseData("heart.csv");
        List<DataPoint> training = new ArrayList<>();
        List<DataPoint> test = new ArrayList<>();
        DataLoader.splitDataIntoTrainingAndTest(allData, training, test, .6);

        classifier.addTrainingData(training);

        classifier.test(test);
    }
}