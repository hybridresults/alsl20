package eval.experiment;
import framework.Framework;
import moa.classifiers.Classifier;
import moa.classifiers.trees.HoeffdingAdaptiveTree;
import moa.classifiers.meta.OnlineSmoothBoost;
import moa.classifiers.meta.AdaptiveRandomForest;
import moa.classifiers.meta.AccuracyWeightedEnsemble;
import moa.classifiers.meta.RCD;
import moa.classifiers.trees.RandomHoeffdingTree;
import moa.classifiers.trees.iadem.Iadem2;
import moa.classifiers.meta.AccuracyUpdatedEnsemble;
import moa.options.ClassOption;

public class ExperimentRow {

    public Framework framework;
    public String label;
    public String subLabel;

    public ExperimentRow(Framework framework, String label) {
        this.framework = framework;
        this.label = label;
        this.subLabel = "";
    }

    public ExperimentRow(Framework framework, String label, String subLabel) {
        this.framework = framework;
        this.label = label;
        this.subLabel = subLabel;
    }

    public static Classifier getExperimentClassifier() {
        //return new RandomHoeffdingTree();
        //return new RCD();
//        AccuracyWeightedEnsemble cls = new AccuracyWeightedEnsemble();
//        cls.learnerOption = new ClassOption("learner", 'l', "Classifier to train.", Classifier.class, "functions.Perceptron");
//        return cls;
        return new HoeffdingAdaptiveTree();
    }
}
