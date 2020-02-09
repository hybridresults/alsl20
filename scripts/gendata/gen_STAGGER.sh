#!/bin/bash
PROJECT_DIR="Projects/MasterThesis/data/synthetic/STAGGER"
cd /opt/moa-2014

# STAGGER stream 0:
# I->II 25000 10000, II->III 50000 50, III->II 51000 50, II->I 76000 10000
# I->II: gradual/incremental, II<->III: blip, II->I: gradual/incremental

java -cp ./:moa.jar:weka.jar -javaagent:sizeofag.jar moa.DoTask \
"WriteStreamToARFFFile 
-s (ConceptDriftStream 
  -s (generators.STAGGERGenerator -f 1)
  -p 25000 -w 10000
  -d (ConceptDriftStream 
    -s (generators.STAGGERGenerator -f 2)
    -p 25000 -w 50
    -d (ConceptDriftStream 
      -s (generators.STAGGERGenerator -f 3)
      -p 1000 -w 50
      -d (ConceptDriftStream 
        -s (generators.STAGGERGenerator -f 2)
        -d (generators.STAGGERGenerator -f 1)
        -p 25000 -w 10000
      )
    )
  )
) -f $HOME/$PROJECT_DIR/STAGGER_0.arff -m 100000"

sed -i 's/,$//' $HOME/$PROJECT_DIR/STAGGER_0.arff
