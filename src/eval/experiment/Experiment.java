package eval.experiment;
import eval.Evaluator;
import moa.classifiers.Classifier;
import output.BarPlotVisualizer;
import output.LinePlotVisualizer;
import output.RaWritter;
import output.ResultProcessor;

import java.util.ArrayList;
import java.util.List;

public abstract class Experiment {

    protected String inputDir;
    protected String outputDir;
    protected Classifier classifier;

    abstract public void run();

    abstract public List<ExperimentRow> createExperimentRows();

    public void conduct(List<ExperimentRow> experimentRows, List<ExperimentStream> experimentStreams, String outputDir) {
        for (ExperimentStream experimentStream : experimentStreams) {
            List<ExperimentResult> results = new ArrayList<>(experimentRows.size());

            for (ExperimentRow experimentRow : experimentRows) {
                experimentRow.framework.reset();
                experimentStream.stream.restart();
                results.add(Evaluator.evaluate(experimentRow, experimentStream));
            }

            ResultProcessor resultProcessor = new ResultProcessor(outputDir, experimentStream.streamName);
            resultProcessor.writeResults(results, experimentStream.logGranularity);
        }
    }
}
