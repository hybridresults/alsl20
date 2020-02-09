#!/bin/bash
PROJECT_DIR="Projects/MasterThesis/Data/synthetic/TREE"
cd /opt/moa-2014

# TREE stream 0:
# I->II 300000 50, II->III 600000 5000
# I->II: sudden, II->III: gradual/incremental

java -cp ./:moa.jar:weka.jar -javaagent:sizeofag.jar moa.DoTask \
"WriteStreamToARFFFile 
-s (ConceptDriftStream 
  -s (generators.RandomTreeGenerator -c 2 -o 0 -u 2 -r 1)
  -p 150000 -w 50
  -d (ConceptDriftStream 
    -s (generators.RandomTreeGenerator -c 2 -o 0 -u 2 -r 2)
    -d (generators.RandomTreeGenerator -c 2 -o 0 -u 2 -r 3)
    -p 150000 -w 5000
  )
) -f $HOME/$PROJECT_DIR/TREE_0.arff -m 500000"

# TREE stream 1:
# I->II 250000 100, II->III 500000 100, II->III 750000 100
# I->II: abrupt, II->III: abrupt, III->IV: abrupt

java -cp ./:moa.jar:weka.jar -javaagent:sizeofag.jar moa.DoTask \
"WriteStreamToARFFFile 
-s (ConceptDriftStream 
  -s (generators.RandomTreeGenerator -c 5 -o 0 -u 15 -r 1)
  -p 250000 -w 100
  -d (ConceptDriftStream 
    -s (generators.RandomTreeGenerator -c 5 -o 0 -u 15 -r 2)
    -p 250000 -w 100
    -d (ConceptDriftStream 
      -s (generators.RandomTreeGenerator -c 5 -o 0 -u 15 -r 3)
      -d (generators.RandomTreeGenerator -c 5 -o 0 -u 15 -r 4)
      -p 250000 -w 100
    )
  )
) -f $HOME/$PROJECT_DIR/TREE_1.arff -m 1000000"

# TREE stream 2:
# I->II 300000 30000, II->III 300000 30000
# I->II: gradual, II->III: gradual

java -cp ./:moa.jar:weka.jar -javaagent:sizeofag.jar moa.DoTask \
"WriteStreamToARFFFile 
-s (ConceptDriftStream 
  -s (generators.RandomTreeGenerator -c 5 -o 0 -u 15 -r 1)
  -p 300000 -w 30000
  -d (ConceptDriftStream 
    -s (generators.RandomTreeGenerator -c 5 -o 0 -u 15 -r 2)
    -d (generators.RandomTreeGenerator -c 5 -o 0 -u 15 -r 3)
    -p 300000 -w 30000
  )
) -f $HOME/$PROJECT_DIR/TREE_2.arff -m 1000000"

# TREE stream 3:
# I->II 300000 100000, II->III 300000 100000
# I->II: gradual/incremental, II->III: gradual/incremental

java -cp ./:moa.jar:weka.jar -javaagent:sizeofag.jar moa.DoTask \
"WriteStreamToARFFFile 
-s (ConceptDriftStream 
  -s (generators.RandomTreeGenerator -c 5 -o 0 -u 15 -r 1)
  -p 300000 -w 100000
  -d (ConceptDriftStream 
    -s (generators.RandomTreeGenerator -c 5 -o 0 -u 15 -r 2)
    -d (generators.RandomTreeGenerator -c 5 -o 0 -u 15 -r 3)
    -p 300000 -w 100000
  )
) -f $HOME/$PROJECT_DIR/TREE_3.arff -m 1000000"

# TREE stream 4:
# I->II 250000 300000, II->III 650000 300000
# I->II: gradual/incremental, II->III: gradual/incremental

java -cp ./:moa.jar:weka.jar -javaagent:sizeofag.jar moa.DoTask \
"WriteStreamToARFFFile 
-s (ConceptDriftStream 
  -s (generators.RandomTreeGenerator -c 5 -o 0 -u 15 -r 1)
  -p 250000 -w 300000
  -d (ConceptDriftStream 
    -s (generators.RandomTreeGenerator -c 5 -o 0 -u 15 -r 2)
    -d (generators.RandomTreeGenerator -c 5 -o 0 -u 15 -r 3)
    -p 400000 -w 300000
  )
) -f $HOME/$PROJECT_DIR/TREE_4.arff -m 1000000"

sed -i 's/,$//' $HOME/$PROJECT_DIR/*.arff
