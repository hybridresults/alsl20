#!/bin/bash
PROJECT_DIR="Projects/MasterThesis/Data/synthetic/LED"
cd /opt/moa-2014

# LED stream 0:
# ALL: gradual

java -cp ./:moa.jar:weka.jar -javaagent:sizeofag.jar moa.DoTask \
"WriteStreamToARFFFile -s (generators.LEDGeneratorDrift -n 5 -d 3 -s)
-f $HOME/$PROJECT_DIR/LED_1.arff -m 500000"

# LED stream 1:
# ALL: gradual

java -cp ./:moa.jar:weka.jar -javaagent:sizeofag.jar moa.DoTask \
"WriteStreamToARFFFile -s (generators.LEDGeneratorDrift -n 10 -d 3 -s)
-f $HOME/$PROJECT_DIR/LED_2.arff -m 500000"

sed -i 's/,$//' $HOME/$PROJECT_DIR/*.arff

