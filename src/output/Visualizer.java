package output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import eval.experiment.ExperimentResult;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


public abstract class Visualizer extends ApplicationFrame {

    private String title = "";
    private String dirPath = "";

    public Visualizer(final String name, final String title, String dirPath) {
        super(name);
        this.title = title;
        this.dirPath = dirPath;
    }

    public void plot(final List<ExperimentResult> results, String fileName, int logGranularity, boolean show) {
        final Dataset dataset = createDataset(results, logGranularity);
        final JFreeChart chart = createChart(dataset, title);
        this.setPlot(chart, fileName);
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(show);
    }

    public void plot(String filepath, String fileName, boolean show) {
        final Dataset dataset = createDataset(filepath);
        final JFreeChart chart = createChart(dataset, title);
        this.setPlot(chart, fileName);
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(show);
    }

    private void setPlot(JFreeChart chart, String fileName) {
        if (dirPath != null) {
            try {
                new File(dirPath).mkdirs();
                OutputStream out = new FileOutputStream(dirPath + "/" + fileName + ".png");
                ChartUtilities.writeChartAsPNG(out, chart, 1260, 760);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 540));
        setContentPane(chartPanel);
    }

    abstract public Dataset createDataset(final List<ExperimentResult> results, int logGranularity);

    abstract public Dataset createDataset(final String filepath);

    abstract public JFreeChart createChart(final Dataset dataset, final String title);
}
