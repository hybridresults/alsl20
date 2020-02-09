package al;
import com.yahoo.labs.samoa.instances.Instance;
import utils.ClassifierUtils;
import java.util.Random;

public class UncertaintyStrategy extends ActiveLearningStrategy {

    public double fixedThreshold = 0.3;
    public double variableThreshold = 1.0;
    public double variableThresholdStep = 0.01;

    private UncertaintyStrategyType strategy;

    public UncertaintyStrategy(UncertaintyStrategyType strategy, double budget) {
        this.strategy = strategy;
        this.budget = budget;
    }

    public UncertaintyStrategy(UncertaintyStrategyType strategy, double budget, double fixedThreshold,
                               double variableThresholdStep) {
        this.strategy = strategy;
        this.budget = budget;
        this.fixedThreshold = fixedThreshold;
        this.variableThresholdStep = variableThresholdStep;
    }

    @Override
    public boolean queryLabel(Instance instance, double[] predictionValues, double driftDetectorFactor) {
        this.currentCost = this.labeledInstances / (double) ++this.iterationCount;
        if (this.currentCost >= this.budget) return false;

        boolean label = false;
        double predictionValue = ClassifierUtils.combinePredictionsMax(predictionValues);

        switch (this.strategy) {
            case RANDOM:
                label = this.labelRandom(); break;
            case FIXED:
                label = this.labelFixed(predictionValue); break;
            case VARIABLE:
                label = this.labelVariable(predictionValue); break;
            case RAND_VARIABLE:
                label = this.labelRandomizedVariable(predictionValue); break;
            case SAMPLING:
                label = this.labelSelectiveSampling(predictionValue, instance); break;
            case CDDM:
                label = this.labelContinuousDDM(predictionValue, driftDetectorFactor, instance.numClasses()); break;
            case CEDDM:
                label = this.labelContinuousEDDM(predictionValue, driftDetectorFactor, instance.numClasses()); break;
            default:
                return label;
        }

        if (label) this.labeledInstances++;

        return label;
    }

    @Override
    public void reset() {
        super.reset();
        this.variableThreshold = 1.0;
    }

    @Override
    public double getFactor() { return this.variableThreshold; }

    private boolean labelRandom() { return (this.random.nextDouble() < this.budget); }

    private boolean labelFixed(double predictionValue) { return (predictionValue <= this.fixedThreshold); }

    private boolean labelVariable(double predictionValue) {
        if (predictionValue <= this.variableThreshold) {
            this.variableThreshold *= (1 - this.variableThresholdStep);
            return true;
        }

        this.variableThreshold *= (1 + this.variableThresholdStep);
        return false;
    }

    private boolean labelRandomizedVariable(double predictionValue) {
        predictionValue = predictionValue / (this.random.nextGaussian() + 1.0);
        return labelVariable(predictionValue);
    }

    private boolean labelSelectiveSampling(double predictionValue, Instance instance) {
        double p = Math.abs(predictionValue - 1.0 / (instance.numClasses()));
        double budget = this.budget / (this.budget + p);

        return (this.random.nextDouble() <= budget);
    }

    private boolean labelContinuousDDM(double predictionValue, double meanError, int numClasses) {
        this.variableThreshold = Math.tanh(2*(meanError + 1.0/numClasses));
        return (predictionValue <= this.variableThreshold);
    }

    private boolean labelContinuousEDDM(double predictionValue, double similarity, int numClasses) {
        this.variableThreshold = 10*(1.0/numClasses - 1)*similarity + (10 - 9.0/numClasses);
        return (predictionValue <= this.variableThreshold);
    }

    public UncertaintyStrategy setFixedThreshold(double fixedThreshold) {
        this.fixedThreshold = fixedThreshold;
        return this;
    }

    public UncertaintyStrategy setVariableThresholdStep(double variableThresholdStep) {
        this.variableThresholdStep = variableThresholdStep;
        return this;
    }
}
