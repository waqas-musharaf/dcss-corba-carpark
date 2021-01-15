#!/bin/bash
echo "Local Server Start Up:"
echo "Please note, CompanyHQ must be running to establish a connection."
echo "Please enter the ORBInitialPort number for LocalServer connection:"
read portNumber

if [[ -n ${portNumber//[0-9]/} ]] || [[ -z "$portNumber" ]]; then
	echo "Error: Invalid Port. Exiting."
	exit 1
else
	echo "Please enter a name for this LocalServer:"
	read serverName

	if [[ -z "$serverName" ]]; then
		echo "Error: Server name cannot be blank. Exiting."
		exit 1
	else
		echo "Please enter the number of spaces for this car park:"
		read spaces

		if [[ -n ${spaces//[0-9]/} ]] || [[ -z "$spaces" ]]; then
			echo "Error: Invalid car park spaces input. Exiting."
			exit 1
		else
			javac LocalServer.java
			java LocalServer -ORBInitialPort $portNumber $serverName $spaces
			exit 0
		fi
	fi
fi
