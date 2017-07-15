####################
#!/bin/sh
####################

echo "Begin deploy......"

mvn clean deploy -Dmaven.test.skip=true 
