#!/bin/sh
clear
javac server/*.java -Xlint:unchecked
echo "Starting server"
java server.KKMultiServer 32400