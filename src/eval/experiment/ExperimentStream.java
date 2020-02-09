package eval.experiment;
import moa.streams.ArffFileStream;
import java.util.ArrayList;
import java.util.List;

public class ExperimentStream {

    public ArffFileStream stream;
    public String streamName;
    public int logGranularity = 1000;

    public ExperimentStream(ArffFileStream stream, String streamName, int logGranularity) {
        this.stream = stream;
        this.streamName = streamName;
        this.logGranularity = logGranularity;
    }

    public static List<ExperimentStream> createExperimentStreams(String rootDataDir) {
        List<ExperimentStream> streams = new ArrayList<>();
        streams.addAll(createRealStreams(rootDataDir + "/real"));
        streams.addAll(createSyntheticStreams(rootDataDir + "/synthetic"));
        return streams;
    }

    private static List<ExperimentStream> createRealStreams(String rootDataDir) {
        List<ExperimentStream> streams = new ArrayList<>();
        streams.add(new ExperimentStream(new ArffFileStream(rootDataDir + "/SPAM/SPAM09/SPAM.arff", 500), "SPAM", 100));
        streams.add(new ExperimentStream(new ArffFileStream(rootDataDir + "/COVERTYPE/COVERTYPE.arff", 55), "COVERTYPE", 2500));
        streams.add(new ExperimentStream(new ArffFileStream(rootDataDir + "/POKER/POKER.arff", 11), "POKER", 5000));
        streams.add(new ExperimentStream(new ArffFileStream(rootDataDir + "/ELEC/ELEC.arff", 9), "ELEC", 100));
        streams.add(new ExperimentStream(new ArffFileStream(rootDataDir + "/POWERSUPPLY/POWERSUPPLY.arff", 3), "POWERSUPPLY", 100));
        streams.add(new ExperimentStream(new ArffFileStream(rootDataDir + "/GAS/GAS.arff", 129), "GAS", 100));
        streams.add(new ExperimentStream(new ArffFileStream(rootDataDir + "/USENET/USENET.arff", 659), "USENET", 100));
        streams.add(new ExperimentStream(new ArffFileStream(rootDataDir + "/SENSOR/SENSOR.arff", 6), "SENSOR", 5000));

        return streams;
    }

    private static List<ExperimentStream> createSyntheticStreams(String rootDataDir) {
        List<ExperimentStream> streams = new ArrayList<>();

        streams.add(new ExperimentStream(new ArffFileStream(rootDataDir + "/TREE/TREE_2.arff", 16), "TREE_2", 5000));
        streams.add(new ExperimentStream(new ArffFileStream(rootDataDir + "/RBF/RBF_3.arff", 16), "RBF_3", 5000));
        streams.add(new ExperimentStream(new ArffFileStream(rootDataDir + "/SEA/SEA_1.arff", 4), "SEA_1", 3000));
        streams.add(new ExperimentStream(new ArffFileStream(rootDataDir + "/HYPERPLANE/HYPERPLANE_1.arff", 16),
                "HYPERPLANE_1", 2500));

        return streams;
    }
}
