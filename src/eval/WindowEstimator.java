package eval;
import java.util.ArrayList;
import java.util.List;

public class WindowEstimator {

    public static final int WINDOW_ESTIMATOR_WIDTH = 1000;
    public int lenWindow;
    public int sizeWindow;

    protected List<Double> all;
    protected double[] window;
    protected int posWindow;
    protected double sum;

    public WindowEstimator(int sizeWindow) {
        this.all = new ArrayList<>();
        this.window = new double[sizeWindow];
        this.sizeWindow = sizeWindow;
        this.posWindow = 0;
        this.lenWindow = 0;
        this.sum = 0.0;
    }

    public void add(double value) {
        this.all.add(value);
        this.sum -= this.window[this.posWindow];
        this.sum += value;
        this.window[this.posWindow] = value;
        this.posWindow++;

        if (this.posWindow == this.sizeWindow) {
            this.posWindow = 0;
        }

        if (this.lenWindow < this.sizeWindow) {
            this.lenWindow++;
        }
    }

    public double estimation() {
        return this.sum / (double)this.lenWindow;
    }

    public double overallAverage() {
        double sum = 0.0;
        for (Double value : this.all) {
            sum += value;
        }
        return sum / this.all.size();
    }
}
