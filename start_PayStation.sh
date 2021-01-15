#!/bin/bash
echo "Pay Station Start Up:"
echo "Please note, CompanyHQ and a LocalServer must be running to establish a connection."
echo "Please enter the ORBInitialPort number for PayStation connection:"
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
		echo "Please enter a name for this PayStation:"
		read stationName

		if [[ -z "$stationName" ]]; then
			echo "Error: Station name cannot be blank. Exiting."
			exit 1
		else
			javac PayStation.java
			java PayStation -ORBInitialPort $portNumber $serverName $stationName
			exit 0
		fi
	fi
fi
