package output;
import eval.experiment.ExperimentResult;
import utils.FileUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResultProcessor {

    private String outputDir = "";
    private String title = "";
    private boolean groupable = false;

    public ResultProcessor(String outputDir, String streamName) {
        this.outputDir = outputDir + "/" + streamName;
        this.title = "Results for " + streamName;
    }

    public void writeResults(List<ExperimentResult> results, int logGranularity) {
        this.groupable = !results.get(0).subLabel.isEmpty();
        this.writePlots(results, logGranularity);
        this.writeRaw(results, logGranularity);
    }

    private void writePlots(List<ExperimentResult> results, int logGranularity) {
        BarPlotVisualizer barVis = new BarPlotVisualizer("Bar plot", this.title, this.outputDir, this.groupable);
        barVis.plot(results, "averages", logGranularity, false);

        LinePlotVisualizer lineVis = new LinePlotVisualizer("Line plot", this.title, this.outputDir);
        if (this.groupable) {
            Map<String, List<ExperimentResult>> groupedResults = this.groupResultsByLabel(results);
            for (List<ExperimentResult> labelResults : groupedResults.values()) {
                lineVis.plot(labelResults, labelResults.get(0).label + "_series", logGranularity, false);
            }
        }
        else {
            lineVis.plot(results, "all_series", logGranularity, false);
        }
    }

    private void writeRaw(List<ExperimentResult> results, int logGranularity) {
        RaWritter.writeResultsToFile(results, this.outputDir, logGranularity);
    }

    private Map<String, List<ExperimentResult>> groupResultsByLabel(List<ExperimentResult> results) {
        return results.stream().collect(Collectors.groupingBy(w -> w.label));
    }

    private static void mergeResults(String rootDir, String[] resultPaths, String outputDir) {
        for (String resultDir : resultPaths) {
            File[] streamDirs = new File(rootDir + "/" + resultDir).listFiles(File::isDirectory);
            assert streamDirs != null;

            for (File streamDir : streamDirs) {
                String inputPath = streamDir.getPath() + "/" + RaWritter.averagesFilename;
                String outputPath = rootDir + "/" + outputDir + "/" + streamDir.getName() + "/" + RaWritter.averagesFilename;

                System.out.println("Appending " + inputPath + " to " + outputPath);
                FileUtils.appendFile(inputPath, outputPath);
            }
        }

        File[] outputStreamDirs = new File(rootDir + "/" + outputDir).listFiles(File::isDirectory);
        assert outputStreamDirs != null;

        for (File outputStreamDir : outputStreamDirs) {
            String streamName = outputStreamDir.getName();
            String dataFilepath = outputStreamDir.getPath() + "/" + RaWritter.averagesFilename;
            BarPlotVisualizer barVis = new BarPlotVisualizer("Bar plot", "Results for " + streamName,
                    outputStreamDir.getPath(), false);

            System.out.println("Generating a bar chart for " + dataFilepath + " in " + outputStreamDir.getPath());
            barVis.plot(dataFilepath, "averages", false);
        }
    }

    private static void generateOutputFromSummary(String rootDir, String outputDir, OutputType outputType) {
        File[] streamDirs = new File(rootDir + "/" + "cmp").listFiles(File::isDirectory);
        assert streamDirs != null;

        for (File streamDir : streamDirs) {
            String inputPath = streamDir.getPath() + "/" + RaWritter.averagesFilename;
            String outputPath = null;

            switch (outputType) {
                case LATEX:
                    outputPath = rootDir + "/" + outputDir + "/" + streamDir.getName() + "_latex.txt";
                    System.out.println("Generating LaTex table file for " + inputPath + " in " + outputPath);
                    FileUtils.parseRawResultToLatexTable(inputPath, outputPath);
                case CSV:
                    outputPath = rootDir + "/" + outputDir + "/" + "summary.csv";
                    System.out.println("Generating CSV file for " + inputPath + " in " + outputPath);
                    FileUtils.parseRawResultToCSV(inputPath, outputPath);
            }
        }
    }

    enum OutputType {LATEX, CSV}

    public static void main(String[] args) throws Exception {
        ResultProcessor.mergeResults("../Results/alsl/aht",
                new String[]{"al", "alr", "als", "fixed-0.95", "uniform", "uniform_rand", "inverted_unc", "cDDM",
                        "cEDDM", "cWin-100"}, "cmp");
        ResultProcessor.generateOutputFromSummary("../Results/alsl/aht", "latex_tables", OutputType.LATEX);
        ResultProcessor.generateOutputFromSummary("../Results/alsl/aht", "summary_csv", OutputType.CSV);
        System.exit(0);
    }
}
