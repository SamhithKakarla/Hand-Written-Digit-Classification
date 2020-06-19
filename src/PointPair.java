public class PointPair {
    private DataPoint d1, d2;
    private double distance;

    public PointPair(DataPoint d1, DataPoint d2, double distance) {
        this.d1 = d1;
        this.d2 = d2;
        this.distance = distance;
    }

    public DataPoint getD1() {
        return d1;
    }

    public DataPoint getD2() {
        return d2;
    }

    public double getDistance() {
        return distance;
    }


}
