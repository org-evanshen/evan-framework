#####################
#!/bin/sh
#####################

module=$1

echo "Begin deploy ancun-$module ……"

mvn clean deploy -Dmaven.test.skip=true -pl ./,$module