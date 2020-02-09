#!/bin/bash
PROJECT_DIR="Projects/MasterThesis/Data/synthetic/WAVEFORM"
cd /opt/moa-2014

# WAVEFORM stream 1:
# ALL: gradual

java -cp ./:moa.jar:weka.jar -javaagent:sizeofag.jar moa.DoTask \
"WriteStreamToARFFFile -s (generators.WaveformGeneratorDrift -d 10 -n)
-f $HOME/$PROJECT_DIR/WAVEFORM_1.arff -m 500000"

sed -i 's/,$//' $HOME/$PROJECT_DIR/*.arff
