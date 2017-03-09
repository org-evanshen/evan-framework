@echo off

set module=%1%

echo Begin deploy %module% ......

call mvn clean deploy -Dmaven.test.skip=true -pl ./,%module%

cmd





