#!/bin/sh
clear
javac client/*.java -Xlint:unchecked
echo "Starting server"
java client.KnockKnockClient localhost 32400