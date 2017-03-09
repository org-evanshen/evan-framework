@echo off

set version=%1%

if not defined version (
	set /p version=Please input version:
)

echo Update version is %version%

call mvn versions:set -DnewVersion=%version% -pl ./

call mvn versions:commit

cmd



