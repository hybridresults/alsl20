# Info

This is a repository with experiments that evaluate a hybrid framework combining active learning and self-labeling strategies for data streams classification. The project includes: source codes for the investigated configurations and experiments and data streams for tests.

# Running experiments
* Install Java 1.8
* Unzip _data.7z_ into _data_ directory
* The experiments start in _eval/Evaluator_.
* Results can be post-processed using _output/ResultProcessor_
* One can change a classifier in _eval/experiment/ExperimentRow_ class or data streams in _eval/experiment/ExperimentStream_

**Warning**: All data streams must be properly unzipped into the mentioned directory. Otherwise the experiments will fail. Also, all experiments must be finished before running the post-processing.

# Dependencies
* MOA 17.06 (https://moa.cms.waikato.ac.nz/new-release-of-moa-17-06/) -> moa.jar and sizeofag.jar
* jfreechart 1.0.19 (http://www.jfree.org/jfreechart/download.html) -> jfreechart-1.0.19.jar and jcommon-1.0.23.jar