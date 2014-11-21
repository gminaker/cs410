#!/bin/sh

set dir %~dp0
set elasticSearchDir %~dp0\codebase\elasticSearch\
set outputDir %~dp0\gitInspectorOutput\

mvn cobertura:cobertura -Dcobertura.report.format=xml

python %dir%\gitinspector\gitinspector\gitinspector.py  -r -f, --file-types=java -F xml %elasticSearchDir% > %outputDir%/output.xml



