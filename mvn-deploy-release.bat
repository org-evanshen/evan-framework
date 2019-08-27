@echo off

set version=%1%

if not defined version (
	set /p version=Please input deploy version:
)

echo Deploy version is %version%

call mvn versions:set -pl ./ -DnewVersion=%version%
call mvn clean deploy -Dmaven.test.skip=true
call mvn versions:set -pl ./ -DnewVersion=%version%-SNAPSHOT
call mvn versions:commit

cmd





