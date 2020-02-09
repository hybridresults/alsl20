#!/bin/bash
PROJECT_DIR="Projects/MasterThesis/Data/synthetic/SEA"
cd /opt/moa-2014

# SEA stream 1:
# I->II 150000 100, II->III 300000 100, III->IV 450000 100
# ALL: abrupt

java -cp ./:moa.jar:weka.jar -javaagent:sizeofag.jar moa.DoTask \
"WriteStreamToARFFFile 
-s (ConceptDriftStream 
  -s (generators.SEAGenerator -f 1 -n 5)
  -p 150000 -w 100
  -d (ConceptDriftStream 
    -s (generators.SEAGenerator -f 2 -n 5)
    -p 150000 -w 100
    -d (ConceptDriftStream 
      -s (generators.SEAGenerator -f 3 -n 10)
      -p 150000 -w 100
      -d (generators.SEAGenerator -f 4 -n 10)    
    )
  )
) -f $HOME/$PROJECT_DIR/SEA_1.arff -m 600000"

# SEA stream 2:
# I->II 250000 30000, II->III 500000 30000, III->IV 750000 30000
# I->II: gradual, II->III: gradual, III->IV: gradual

java -cp ./:moa.jar:weka.jar -javaagent:sizeofag.jar moa.DoTask \
"WriteStreamToARFFFile 
-s (ConceptDriftStream 
  -s (generators.SEAGenerator -f 1 -n 5)
  -p 250000 -w 30000
  -d (ConceptDriftStream 
    -s (generators.SEAGenerator -f 2 -n 5)
    -p 250000 -w 30000
    -d (ConceptDriftStream 
      -s (generators.SEAGenerator -f 3 -n 5)
      -p 250000 -w 30000
      -d (generators.SEAGenerator -f 4 -n 5)    
    )
  )
) -f $HOME/$PROJECT_DIR/SEA_2.arff -m 1000000"

# SEA stream 3:
# I->II 250000 100000, II->III 500000 100000, III->IV 750000 100000
# I->II: gradual/incremental, II->III: gradual/incremental, III->IV: gradual/incremental

java -cp ./:moa.jar:weka.jar -javaagent:sizeofag.jar moa.DoTask \
"WriteStreamToARFFFile 
-s (ConceptDriftStream 
  -s (generators.SEAGenerator -f 1 -n 5)
  -p 250000 -w 100000
  -d (ConceptDriftStream 
    -s (generators.SEAGenerator -f 2 -n 5)
    -p 250000 -w 100000
    -d (ConceptDriftStream 
      -s (generators.SEAGenerator -f 3 -n 5)
      -p 250000 -w 100000
      -d (generators.SEAGenerator -f 4 -n 5)    
    )
  )
) -f $HOME/$PROJECT_DIR/SEA_3.arff -m 1000000"

sed -i 's/,$//' $HOME/$PROJECT_DIR/SEA_*.arff

