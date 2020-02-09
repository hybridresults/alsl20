package output;

import eval.experiment.ExperimentResult;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LinePlotVisualizer extends Visualizer {

    public LinePlotVisualizer(final String name, final String title, String dirPath) {
        super(name, title, dirPath);
    }

    @Override
    public Dataset createDataset(final List<ExperimentResult> results, int logGranularity) {
        final XYSeriesCollection dataset = new XYSeriesCollection();

        List<List<Double>> data = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<String> subLabels = new ArrayList<>();

        for (ExperimentResult result : results) {
            List<Double> accuracies = new ArrayList<>();
            for (int i = 0; i < result.accuracies.size(); i++) {
                if (i % logGranularity == 0) accuracies.add(result.accuracies.get(i));
            }
            data.add(accuracies);
            labels.add(result.label);
            subLabels.add(result.subLabel);
        }

        for (int i = 0; i < data.size(); i++) {
            final String subLabel = subLabels.get(i);
            final XYSeries series = new XYSeries(labels.get(i) + (subLabel.isEmpty() ? "" : " - " + subLabel));
            final List<Double> seriesData = data.get(i);

            for (int j = 0; j < seriesData.size(); j++) {
                series.add(j, seriesData.get(j));
            }

            dataset.addSeries(series);
        }

        return dataset;
    }

    @Override
    public Dataset createDataset(String filepath) {
        return null;
    }

    @Override
    public JFreeChart createChart(final Dataset dataset, final String title) {
        final JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Sample (* granularity)",
                "Accuracy (%)",
                (XYDataset) dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        chart.setBackgroundPaint(Color.white);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(false);
        rangeAxis.setUpperMargin(0.1);
        rangeAxis.setLowerMargin(0.3);

        final XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseShapesVisible(false);
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesPaint(2, Color.GREEN);
        renderer.setSeriesPaint(3, Color.ORANGE);
        renderer.setSeriesPaint(4, Color.CYAN);
        renderer.setSeriesPaint(5, Color.MAGENTA);
        renderer.setSeriesPaint(6, Color.DARK_GRAY);
        plot.setRenderer(renderer);

        LegendTitle legend = chart.getLegend();
        legend.setPosition(RectangleEdge.RIGHT);

        return chart;
    }
}
