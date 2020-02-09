package framework;

import al.ActiveLearningStrategy;
import com.yahoo.labs.samoa.instances.Instance;
import moa.classifiers.Classifier;
import sl.SelfLabelingStrategy;

abstract public class Framework {

    public Classifier classifier;
    public ActiveLearningStrategy activeLearningStrategy;
    public SelfLabelingStrategy selfLabelingStrategy;
    public boolean ready = true;
    public int initInstances = 1000;

    abstract public void reset();
    abstract public void update(Instance instance, double[] votes, boolean correct, int i);
}
