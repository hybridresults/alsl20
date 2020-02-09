package framework;

import al.ActiveLearningStrategy;
import com.yahoo.labs.samoa.instances.Instance;
import moa.classifiers.Classifier;
import moa.core.Utils;
import sl.SelfLabelingStrategy;

public class BlindFramework extends Framework {

    public BlindFramework(Classifier classifier, ActiveLearningStrategy activeLearningStrategy,
                     SelfLabelingStrategy selfLabelingStrategy) {
        this.classifier = classifier; this.classifier.prepareForUse();
        this.activeLearningStrategy = activeLearningStrategy;
        this.selfLabelingStrategy = selfLabelingStrategy;
    }

    public BlindFramework(Classifier classifier, ActiveLearningStrategy activeLearningStrategy) {
        this.classifier = classifier; this.classifier.prepareForUse();
        this.activeLearningStrategy = activeLearningStrategy;
    }

    public double classify(Instance instance) {
        return Utils.maxIndex(classifier.getVotesForInstance(instance));
    }

    @Override
    public void reset() {
        this.classifier.resetLearning();
        if (this.activeLearningStrategy != null) this.activeLearningStrategy.reset();
        if (this.selfLabelingStrategy != null) this.selfLabelingStrategy.reset();
        this.ready = true;
    }

    @Override
    public void update(Instance instance, double[] votes, boolean correct, int i) {
        if (i <= this.initInstances) {
            this.classifier.trainOnInstance(instance);
            return;
        }

        double activeLearningFactor = -1;
        boolean learnActively =  false;

        if (this.activeLearningStrategy != null) {
            activeLearningFactor = this.activeLearningStrategy.getFactor();
            learnActively = this.activeLearningStrategy.queryLabel(instance, votes, 0);
        }

        if (learnActively) this.classifier.trainOnInstance(instance);
        else if (this.selfLabelingStrategy != null && this.selfLabelingStrategy.assignLabel(instance, votes,
                activeLearningFactor, 0)) {
            instance.setClassValue(Utils.maxIndex(votes));
            this.classifier.trainOnInstance(instance);
        }
    }
}
