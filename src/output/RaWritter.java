package output;
import eval.experiment.ExperimentResult;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class RaWritter {

    public static String seriesFilename = "series.data";
    public static String averagesFilename = "averages.data";

    public static void writeResultsToFile(List<ExperimentResult> results, String dirPath, int logGranularity) {
        if (dirPath == null) return;

        BufferedWriter seriesOutputWriter, overallOutputWriter;
        try {
            seriesOutputWriter = new BufferedWriter(new FileWriter(dirPath + "/" + seriesFilename));
            overallOutputWriter = new BufferedWriter(new FileWriter(dirPath + "/" + averagesFilename));

            for (ExperimentResult result : results) {
                String label = result.label + (result.subLabel.isEmpty() ? "" : "-" + result.subLabel);

                seriesOutputWriter.write(label);
                for (int i = 0; i < result.accuracies.size(); i++) {
                    if ((i % logGranularity) == 0) seriesOutputWriter.write(
                            String.format(",%.4f", result.accuracies.get(i) / 100));
                }
                seriesOutputWriter.newLine();

                overallOutputWriter.write(label);
                overallOutputWriter.write(String.format(",%.4f", result.overallAccuracy));
                overallOutputWriter.newLine();

                //outputWriter.write("Queries:" + String.format("%.4f", result.queriesFactor));
            }

            seriesOutputWriter.flush(); overallOutputWriter.flush();
            seriesOutputWriter.close(); overallOutputWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
