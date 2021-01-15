#!/bin/bash
echo "Company HQ Start Up:"
echo "Please enter the ORBInitialPort number for CompanyHQ connection:"
read portNumber

if [[ -n ${portNumber//[0-9]/} ]] || [[ -z "$portNumber" ]] ; then
	echo "Error: Invalid Port. Exiting."
	exit 1
else
	javac CompanyHQ.java
	java CompanyHQ -ORBInitialPort $portNumber
	exit 0
fi


