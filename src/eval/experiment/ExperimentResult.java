package eval.experiment;
import java.util.ArrayList;
import java.util.List;

public class ExperimentResult {

    public List<Double> accuracies;
    public double overallAccuracy;
    public double queriesFactor;
    public String label;
    public String subLabel;

    public ExperimentResult(String label, String subLabel) {
        this.accuracies = new ArrayList<>();
        this.overallAccuracy = 0.0;
        this.queriesFactor = 0.0;
        this.label = label;
        this.subLabel = subLabel;
    }
}
