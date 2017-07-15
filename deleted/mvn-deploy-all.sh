#####################
#!/bin/sh
#####################
echo "Begin deploy yourong-framework ……"
mvn clean deploy -Dmaven.test.skip=true
