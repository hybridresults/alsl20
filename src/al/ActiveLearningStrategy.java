package al;

import com.yahoo.labs.samoa.instances.Instance;

import java.util.Random;

public abstract class ActiveLearningStrategy {

    public double budget = 1.0;
    public int labeledInstances = 0;
    public double currentCost = 0.0;
    public int iterationCount = 0;
    protected Random random = new Random();

    public void reset() {
        this.labeledInstances = 0;
        this.currentCost = 0.0;
        this.iterationCount = 0;
    }

    abstract public boolean queryLabel(Instance instance, double[] predictionValues, double driftDetectorFactor);

    abstract public double getFactor();
}
