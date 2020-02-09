#!/bin/bash
PROJECT_DIR="Projects/MasterThesis/Data/synthetic/RBF"
cd /opt/moa-2014

# RBF stream 0:
# I->II 150000 5000, II->III 300000 100
# I->II: gradual/incremental, II->III: sudden

java -cp ./:moa.jar:weka.jar -javaagent:sizeofag.jar moa.DoTask \
"WriteStreamToARFFFile 
-s (ConceptDriftStream 
  -s (generators.RandomRBFGenerator -a 2 -c 2 -n 5 -r 1)
  -p 150000 -w 5000
  -d (ConceptDriftStream 
    -s (generators.RandomRBFGenerator -a 2 -c 2 -n 5 -r 2)
    -d (generators.RandomRBFGenerator -a 2 -c 2 -n 5 -r 3)
    -p 150000 -w 50
  )
) -f $HOME/$PROJECT_DIR/RBF_0.arff -m 500000"

# RBF stream 1:
# I->II 250000 100, II->III 500000 100, II->III 750000 100
# I->II: abrupt, II->III: abrupt, III->IV: abrupt

java -cp ./:moa.jar:weka.jar -javaagent:sizeofag.jar moa.DoTask \
"WriteStreamToARFFFile 
-s (ConceptDriftStream 
  -s (generators.RandomRBFGenerator -a 15 -c 5 -n 5 -r 1)
  -p 250000 -w 100
  -d (ConceptDriftStream 
    -s (generators.RandomRBFGenerator -a 15 -c 5 -n 5 -r 2)
    -p 250000 -w 100
    -d (ConceptDriftStream 
      -s (generators.RandomRBFGenerator -a 15 -c 5 -n 5 -r 3)
      -d (generators.RandomRBFGenerator -a 15 -c 5 -n 5 -r 4)
      -p 250000 -w 100
    )
  )
) -f $HOME/$PROJECT_DIR/RBF_1.arff -m 1000000"

# RBF stream 2:
# I->II 300000 30000, II->III 300000 30000
# I->II: gradual, II->III: gradual

java -cp ./:moa.jar:weka.jar -javaagent:sizeofag.jar moa.DoTask \
"WriteStreamToARFFFile 
-s (ConceptDriftStream 
  -s (generators.RandomRBFGenerator -a 15 -c 5 -n 5 -r 1)
  -p 300000 -w 30000
  -d (ConceptDriftStream 
    -s (generators.RandomRBFGenerator -a 15 -c 5 -n 5 -r 2)
    -d (generators.RandomRBFGenerator -a 15 -c 5 -n 5 -r 3)
    -p 300000 -w 30000
  )
) -f $HOME/$PROJECT_DIR/RBF_2.arff -m 1000000"

# RBF stream 3:
# I->II 300000 100000, II->III 300000 100000
# I->II: gradual/incremental, II->III: gradual/incremental

java -cp ./:moa.jar:weka.jar -javaagent:sizeofag.jar moa.DoTask \
"WriteStreamToARFFFile 
-s (ConceptDriftStream 
  -s (generators.RandomRBFGenerator -a 15 -c 5 -n 5 -r 1)
  -p 300000 -w 100000
  -d (ConceptDriftStream 
    -s (generators.RandomRBFGenerator -a 15 -c 5 -n 5 -r 2)
    -d (generators.RandomRBFGenerator -a 15 -c 5 -n 5 -r 3)
    -p 300000 -w 100000
  )
) -f $HOME/$PROJECT_DIR/RBF_3.arff -m 1000000"

# RBF stream 4:
# I->II 250000 300000, II->III 650000 300000
# I->II: gradual/incremental, II->III: gradual/incremental

java -cp ./:moa.jar:weka.jar -javaagent:sizeofag.jar moa.DoTask \
"WriteStreamToARFFFile 
-s (ConceptDriftStream 
  -s (generators.RandomRBFGenerator -a 15 -c 5 -n 5 -r 1)
  -p 250000 -w 300000
  -d (ConceptDriftStream 
    -s (generators.RandomRBFGenerator -a 15 -c 5 -n 5 -r 2)
    -d (generators.RandomRBFGenerator -a 15 -c 5 -n 5 -r 3)
    -p 400000 -w 300000
  )
) -f $HOME/$PROJECT_DIR/RBF_4.arff -m 1000000"

sed -i 's/,$//' $HOME/$PROJECT_DIR/*.arff
