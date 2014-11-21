#!/bin/sh

basedir=$(pwd)
codebasename=elasticSearch
elasticSearchDir=$basedir/codebase/elasticSearch/
outputDir=$basedir/gitInspectorOutput/
coberturadir=$basedir/codebase/$codebasename/
gitinspectordir=$basedir/gitinspector/gitinspector


cd $coberturadir

mvn cobertura:cobertura -Dcobertura.report.format=xml

python $gitinspectordir/gitinspector.py -x file:test  -r -f, --file-types=java -F xml  > $basedir/gitinspectorOutput/$codebasename.xml



#<>

#<>

#<>
