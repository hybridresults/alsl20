package framework;

import com.yahoo.labs.samoa.instances.Instance;
import moa.classifiers.lazy.neighboursearch.KDTree;
import moa.classifiers.lazy.neighboursearch.LinearNNSearch;

public class OversamplingFramework extends Framework {

    private boolean oversampler; // windowed nn structure(s) which extends itself?

    public OversamplingFramework() {
        this.oversampler = true;
    }

    @Override
    public void reset() {

    }

    @Override
    public void update(Instance instance, double[] votes, boolean correct, int i) {
        // check with random AL if to be labeled
        //  insert new labeled sample
        //  generate new synth objects
        // (if not try self-labeling)
    }
}
