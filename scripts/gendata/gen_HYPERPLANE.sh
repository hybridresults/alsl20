#!/bin/bash
PROJECT_DIR="Projects/MasterThesis/Data/synthetic/HYPERPLANE"
cd /opt/moa-2014

# All: incremental/gradual

java -cp ./:moa.jar:weka.jar -javaagent:sizeofag.jar moa.DoTask \
"WriteStreamToARFFFile -s (generators.HyperplaneGenerator -a 2 -k 1 -t 0.001 -n 0 -s 1)
-f $HOME/$PROJECT_DIR/HYPERPLANE_0.arff -m 500000"

java -cp ./:moa.jar:weka.jar -javaagent:sizeofag.jar moa.DoTask \
"WriteStreamToARFFFile -s (generators.HyperplaneGenerator -a 15 -k 7 -c 5 -t 0.001 -n 5 -s 1)
-f $HOME/$PROJECT_DIR/HYPERPLANE_1.arff -m 500000"

java -cp ./:moa.jar:weka.jar -javaagent:sizeofag.jar moa.DoTask \
"WriteStreamToARFFFile -s (generators.HyperplaneGenerator -a 15 -k 7 -c 5 -t 0.01 -n 5 -s 1)
-f $HOME/$PROJECT_DIR/HYPERPLANE_2.arff -m 500000"

sed -i 's/,$//' $HOME/$PROJECT_DIR/*.arff

