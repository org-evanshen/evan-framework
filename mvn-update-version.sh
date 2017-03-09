#####################
#!/bin/sh
#####################

version=$1

if [ ! -n "$version" ]; then
    echo -e "Please input the version to update: \c"
	read version
fi

echo "Update version is $version"

mvn versions:set -DnewVersion=$version -pl ./

mvn versions:commit