#!/bin/sh

basedir=$(pwd)
codebasename=jsoup
outputDir=$basedir/gitInspectorOutput/
coberturadir=$basedir/codebase/$codebasename/
gitinspectordir=$basedir/gitinspector/gitinspector


cd $coberturadir

mvn cobertura:cobertura -Dcobertura.report.format=xml

python $gitinspectordir/gitinspector.py -x file:test  -r -f, --file-types=java -F xml  > $basedir/gitinspectorOutput/$codebasename.xml

cd $basedir

java -jar visualization.jar $codebasename $basedir/codebase/$codebasename/target/site/cobertura/coverage.xml $basedir/gitinspectorOutput/$codebasename.xml

#<>

#<>

#<>
