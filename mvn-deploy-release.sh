####################
#!/bin/sh
####################

version=$1

if [ ! -n "$version" ]; then
    echo -e "Please input deploy version: \c"
	read version
fi

echo "Deploy version is $version"

mvn versions:set -pl ./ -DnewVersion=$version
mvn clean deploy -Dmaven.test.skip=true
mvn versions:set -pl ./ -DnewVersion=$version-SNAPSHOT
mvn versions:commit

echo "end"
