package eval.cases.al;

import al.UncertaintyStrategy;
import al.UncertaintyStrategyType;
import eval.experiment.Experiment;
import eval.experiment.ExperimentRow;
import eval.experiment.ExperimentStream;
import framework.BlindFramework;
import moa.classifiers.Classifier;

import java.util.ArrayList;
import java.util.List;

public class AL_SamplingExperiment extends Experiment {

    public AL_SamplingExperiment(String inputDir, String outputDir) {
        this.inputDir = inputDir;
        this.outputDir = outputDir;
    }

    @Override
    public void run() {
        this.conduct(this.createExperimentRows(), ExperimentStream.createExperimentStreams(this.inputDir), this.outputDir);
    }

    @Override
    public List<ExperimentRow> createExperimentRows() {
        Classifier cls = ExperimentRow.getExperimentClassifier();
        List<ExperimentRow> rows = new ArrayList<>();
        rows.add(new ExperimentRow(
                new BlindFramework(
                        cls.copy(),
                        new UncertaintyStrategy(UncertaintyStrategyType.SAMPLING, 0.01).setVariableThresholdStep(0.01)
                ),
                "ALS", "0.01"
        ));
        rows.add(new ExperimentRow(
                new BlindFramework(
                        cls.copy(),
                        new UncertaintyStrategy(UncertaintyStrategyType.SAMPLING, 0.05).setVariableThresholdStep(0.01)
                ),
                "ALS", "0.05"
        ));
        rows.add(new ExperimentRow(
                new BlindFramework(
                        cls.copy(),
                        new UncertaintyStrategy(UncertaintyStrategyType.SAMPLING, 0.1).setVariableThresholdStep(0.01)
                ),
                "ALS", "0.1"
        ));
        rows.add(new ExperimentRow(
                new BlindFramework(
                        cls.copy(),
                        new UncertaintyStrategy(UncertaintyStrategyType.SAMPLING, 0.2).setVariableThresholdStep(0.01)
                ),
                "ALS", "0.2"
        ));
        rows.add(new ExperimentRow(
                new BlindFramework(
                        cls.copy(),
                        new UncertaintyStrategy(UncertaintyStrategyType.SAMPLING, 0.5).setVariableThresholdStep(0.01)
                ),
                "ALS", "0.5"
        ));

        return rows;
    }

}
