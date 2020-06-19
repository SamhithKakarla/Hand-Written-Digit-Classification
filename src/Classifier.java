import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Classifier {
    private ArrayList<DataPoint> trainingData;
    private int k;

    public Classifier(int k) {
        this.k = k;
        trainingData = new ArrayList<DataPoint>();
    }

    public void addTrainingData(List<DataPoint> points) {
        for (int i = 0; i < points.size(); i++) {
            trainingData.add(points.get(i));
        }
    }

    public void addTrainingData(DataPoint point) {
        trainingData.add(point);
    }

    public String classify(double[] featureVector) {
        if (trainingData.size() == 0) return "no training data";

        DataPoint testPoint = new DataPoint("test", featureVector);

        double minDist = Double.MAX_VALUE;
        ArrayList<PointPair> neighbors = new ArrayList<>();

        for(DataPoint point: this.trainingData){
            double dist = distance(featureVector, point.getData());
            if(neighbors.size() < k){
                neighbors.add(new PointPair(testPoint,point, dist));

            }else{
                PointPair neighbor = neighbors.get(neighbors.size() - 1);

                if(dist < neighbor.getDistance()){
                    PointPair newClosePoint = new PointPair(testPoint, point, dist);
                    insertNewClosest(newClosePoint, neighbors);
                }
            }
        }


        return getMajority(neighbors);  // replace this line
    }

    private void insertNewClosest(PointPair newClosePoint, ArrayList<PointPair> neighbors) {
        for (int i = 0; i < neighbors.size(); i++) {
            PointPair closePoint = neighbors.get(i);
            if(newClosePoint.getDistance() < closePoint.getDistance()){
                neighbors.add(i, newClosePoint);
                if(neighbors.size() > k) {
                    neighbors.remove(neighbors.size() - 1);
                }

                return;
            }
        }
    }

    private String getMajority(ArrayList<PointPair> neighbors) {
        int max = 0;
        String maj = " ";

        for(PointPair p : neighbors){
            String label = p.getD2().getLabel();

            int count = count(label, neighbors);
            if(count > max){
                max = count;
                maj = p.getD2().getLabel();
            }
        }

        return maj;
    }

    private int count(String label, ArrayList<PointPair> neighbors) {
        int counter = 0;

        for(PointPair p : neighbors){
            if(p.getD2().getLabel().equals(label)) counter++;
        }

        return counter;

    }

    public double distance(double[] d1, double[] d2) {

        double diffsSquared = 0;
        for(int i = 0; i < d1.length; i++){
            double diff = d1[i] - d2[i];
            diffsSquared += diff * diff;
        }

        return diffsSquared;
    }

    public void test(List<DataPoint> test) {
        ArrayList<DataPoint> correct = new ArrayList<>();
        ArrayList<DataPoint> wrong = new ArrayList<>();

        int i = 0;
        for (DataPoint p : test) {
            String predict = classify(p.getData());
            System.out.print("#" + i + " REAL:\t" + p.getLabel() + " predicted:\t" + predict);
            if (predict.equals(p.getLabel())) {
                correct.add(p);
                System.out.print(" Correct ");
            } else {
                wrong.add(p);
                System.out.print(" WRONG ");
            }

            i++;
            System.out.println(" % correct: " + ((double) correct.size() / i));
        }

        System.out.println(correct.size() + " correct out of " + test.size());
        System.out.println(wrong.size() + " wrong out of " + test.size());
        System.out.println("% Error: " + (double) wrong.size() / test.size());
    }
}
