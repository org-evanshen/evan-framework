#####################
#!/bin/sh
#####################

echo "Begin deploy ancun-core ……"

mvn clean deploy -Dmaven.test.skip=true

