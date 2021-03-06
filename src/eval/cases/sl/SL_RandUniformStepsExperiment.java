package eval.cases.sl;

import al.UncertaintyStrategy;
import al.UncertaintyStrategyType;
import eval.experiment.Experiment;
import eval.experiment.ExperimentRow;
import eval.experiment.ExperimentStream;
import framework.BlindFramework;
import moa.classifiers.Classifier;
import moa.classifiers.trees.HoeffdingAdaptiveTree;
import sl.SelfLabelingStrategy;
import sl.SelfLabelingStrategyType;
import java.util.ArrayList;
import java.util.List;


public class SL_RandUniformStepsExperiment extends Experiment {

    public SL_RandUniformStepsExperiment(String inputDir, String outputDir) {
        this.inputDir = inputDir;
        this.outputDir = outputDir;
    }

    @Override
    public void run() {
        this.conduct(this.createExperimentRows(), ExperimentStream.createExperimentStreams("../Data"), this.outputDir);
    }

    @Override
    public List<ExperimentRow> createExperimentRows() {
        Classifier cls = new HoeffdingAdaptiveTree();
        List<ExperimentRow> rows = new ArrayList<>();
        rows.add(new ExperimentRow(
                new BlindFramework(
                        cls.copy(),
                        new UncertaintyStrategy(UncertaintyStrategyType.RAND_VARIABLE, 0.1).setVariableThresholdStep(0.01),
                        new SelfLabelingStrategy(SelfLabelingStrategyType.UNIFORM_RAND).setVariableThresholdStep(0.1)
                ),
                "RandUni", "0.1"
        ));
        rows.add(new ExperimentRow(
                new BlindFramework(
                        cls.copy(),
                        new UncertaintyStrategy(UncertaintyStrategyType.RAND_VARIABLE, 0.1).setVariableThresholdStep(0.01),
                        new SelfLabelingStrategy(SelfLabelingStrategyType.UNIFORM_RAND).setVariableThresholdStep(0.01)
                ),
                "RandUni", "0.01"
        ));
        rows.add(new ExperimentRow(
                new BlindFramework(
                        cls.copy(),
                        new UncertaintyStrategy(UncertaintyStrategyType.RAND_VARIABLE, 0.1).setVariableThresholdStep(0.01),
                        new SelfLabelingStrategy(SelfLabelingStrategyType.UNIFORM_RAND).setVariableThresholdStep(0.001)
                ),
                "RandUni", "0.001"
        ));

        return rows;
    }
}
