package eval;
import com.yahoo.labs.samoa.instances.Instance;
import eval.cases.al.AL_RandExperiment;
import eval.cases.al.AL_RandVarExperiment;
import eval.cases.al.AL_SamplingExperiment;
import eval.cases.blind.*;
import eval.cases.informed.DDM.SL_cDDMExperiment;
import eval.cases.informed.EDDM.SL_cEDDMExperiment;
import eval.cases.informed.SL_WindowedErrorIndicatorExperiment;
import eval.experiment.ExperimentResult;
import eval.experiment.ExperimentRow;
import eval.experiment.ExperimentStream;
import framework.Framework;
import moa.core.Utils;
import moa.streams.ArffFileStream;

import java.util.Date;

public class Evaluator {

    private static void runExperiments(String inputDir, String rootOutputDir) {
        (new AL_RandVarExperiment(inputDir, rootOutputDir + "/alrv")).run();
        (new AL_RandExperiment(inputDir, rootOutputDir + "/alr")).run();
        (new AL_SamplingExperiment(inputDir, rootOutputDir + "/als")).run();
        (new SL_FixedExperiment(inputDir, rootOutputDir + "/fixed-0.95")).run();
        (new SL_UniformExperiment(inputDir, rootOutputDir + "/uniform")).run();
        (new SL_UniformRandExperiment(inputDir, rootOutputDir + "/uniform_rand")).run();
        (new SL_InvertedUncExperiment(inputDir, rootOutputDir + "/inverted_unc")).run();
        (new SL_cDDMExperiment(inputDir, rootOutputDir + "/cDDM")).run();
        (new SL_cEDDMExperiment(inputDir, rootOutputDir + "/cEDDM")).run();
        (new SL_WindowedErrorIndicatorExperiment(inputDir, rootOutputDir + "/cWin-100")).run();
    }

    public static ExperimentResult evaluate(ExperimentRow experimentRow, ExperimentStream experimentStream) {
        System.out.println(new Date() + ": [" + experimentStream.streamName + " & " + experimentRow.label +
                (experimentRow.subLabel.isEmpty() ? "" : "-" + experimentRow.subLabel) + "]");

        ExperimentResult result = new ExperimentResult(experimentRow.label, experimentRow.subLabel);
        WindowEstimator estimator = new WindowEstimator(1000);

        ArffFileStream stream = experimentStream.stream;
        Framework framework = experimentRow.framework;
        framework.classifier.setModelContext(stream.getHeader());

        if (!framework.ready) {
            System.out.println("Framework is not prepared, you need to reset it before using");
            return result;
        }

        int i = 0;
        while (stream.hasMoreInstances()) {
            Instance instance = stream.nextInstance().getData();
            double[] votes = framework.classifier.getVotesForInstance(instance);
            boolean correct = Utils.maxIndex(votes) == (int) instance.classValue(); // testing on all
            estimator.add(correct ? 1 : 0);

            if (framework.activeLearningStrategy == null || i >= framework.initInstances) {
                double accuracy = estimator.estimation() * 100;
                result.accuracies.add(accuracy);
            }

            framework.update(instance.copy(), votes, correct, i);
            i++;
        }

        result.overallAccuracy = estimator.overallAverage();
        result.queriesFactor = (double) framework.activeLearningStrategy.labeledInstances / i;
        framework.ready = false;

        return result;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Starting for AHT: " + new Date());
        Evaluator.runExperiments("../Data/streams", "../Results/alsl/aht");
        System.out.println("Finish: " + new Date());
        System.exit(0);
    }
}
