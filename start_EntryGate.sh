#!/bin/bash
echo "Entry Gate Start Up:"
echo "Please note, CompanyHQ and a LocalServer must be running to establish a connection."
echo "Please enter the ORBInitialPort number for EntryGate connection:"
read portNumber

if [[ -n ${portNumber//[0-9]/} ]] || [[ -z "$portNumber" ]]; then
	echo "Error: Invalid Port. Exiting."
	exit 1
else
	echo "Please enter the name of the LocalServer to connect to:"
	read serverName

	if [[ -z "$serverName" ]]; then
		echo "Error: Server name cannot be blank. Exiting."
		exit 1
	else
		echo "Please enter a name for this EntryGate:"
		read gateName

		if [[ -z "$gateName" ]]; then
			echo "Error: Gate name cannot be blank. Exiting."
			exit 1
		else
			javac EntryGate.java
			java EntryGate -ORBInitialPort $portNumber $serverName $gateName
			exit 0
		fi
	fi
fi
