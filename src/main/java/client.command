#!/bin/sh
clear
javac client/*.java -Xlint:unchecked
echo "Starting server"
java client.Client localhost 32400