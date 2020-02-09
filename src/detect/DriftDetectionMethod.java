package detect;

public interface DriftDetectionMethod {
    void update(boolean isPredictionCorrect);
    double checkState();
    double getDetectorIndicator();
    void reset();
}
