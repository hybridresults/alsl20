package framework;
import al.ActiveLearningStrategy;
import com.yahoo.labs.samoa.instances.Instance;
import detect.DriftDetectionMethod;
import detect.StreamStateType;
import moa.classifiers.Classifier;
import moa.core.Utils;
import sl.SelfLabelingStrategy;

public class InformedFramework extends BlindFramework {

    public DriftDetectionMethod driftDetector;
    private boolean discrete = false;

    public InformedFramework(Classifier classifier, ActiveLearningStrategy activeLearningStrategy,
                     SelfLabelingStrategy selfLabelingStrategy, DriftDetectionMethod driftDetector, boolean discrete) {
        super(classifier, activeLearningStrategy, selfLabelingStrategy);
        this.driftDetector = driftDetector;
        this.discrete = discrete;
    }

    public InformedFramework(Classifier classifier, ActiveLearningStrategy activeLearningStrategy,
                             DriftDetectionMethod driftDetector, boolean discrete) {
        super(classifier, activeLearningStrategy);
        this.driftDetector = driftDetector;
        this.discrete = discrete;
    }

    @Override
    public void reset() {
        if (this.driftDetector != null) this.driftDetector.reset();
        super.reset();
    }

    @Override
    public void update(Instance instance, double[] votes, boolean correct, int instanceNum) {
        if (instanceNum < this.initInstances) {
            this.classifier.trainOnInstance(instance);
            return;
        }

        double driftDetectorFactor = this.driftDetector.getDetectorIndicator();
        double activeLearningFactor = -1;
        boolean learnActively = false;

        if (this.activeLearningStrategy != null) {
            activeLearningFactor = this.activeLearningStrategy.getFactor();
            learnActively = this.activeLearningStrategy.queryLabel(instance, votes, driftDetectorFactor);
        }

        if (learnActively) {
            this.driftDetector.update(correct);
            if ((int) this.driftDetector.checkState() == StreamStateType.DRIFT.ordinal()) {
                if (this.discrete) this.classifier.resetLearning();
                this.driftDetector.reset();
            }
            this.classifier.trainOnInstance(instance);
        }
        else if (this.selfLabelingStrategy != null && this.selfLabelingStrategy.assignLabel(instance, votes,
                activeLearningFactor, driftDetectorFactor)) {
            instance.setClassValue(Utils.maxIndex(votes));
            this.classifier.trainOnInstance(instance);
        }
    }
}
