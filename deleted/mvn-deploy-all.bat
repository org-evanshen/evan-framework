@echo off

echo Begin deploy ......

call mvn clean deploy -Dmaven.test.skip=true

cmd





