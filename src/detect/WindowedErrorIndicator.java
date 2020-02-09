package detect;
import eval.WindowEstimator;

public class WindowedErrorIndicator implements DriftDetectionMethod {

    private double m_p = 1;
    private double m_s = 0;
    public WindowEstimator estimator = new WindowEstimator(WindowEstimator.WINDOW_ESTIMATOR_WIDTH);

    public WindowedErrorIndicator() {}

    public WindowedErrorIndicator(int windowSize) {
        this.estimator = new WindowEstimator(windowSize);
    }

    @Override
    public void update(boolean isPredictionCorrect) {
        int p = !isPredictionCorrect ? 1 : 0;
        this.estimator.add(p);
        m_p = estimator.estimation();
        m_s = Math.sqrt(m_p * (1 - m_p) / (double) this.estimator.lenWindow);
    }

    @Override
    public double checkState() {
        return StreamStateType.STATIC.ordinal();
    }

    @Override
    public double getDetectorIndicator() {
        return m_p + m_s;
    }

    @Override
    public void reset() {
        m_p = 1;
        m_s = 0;
        this.estimator = new WindowEstimator(this.estimator.sizeWindow);
    }
}
