#!/bin/bash
javac -d . src/main/*.java
java main.MainMenu

#if this doesnt run try these first
#chmod +x runApp.sh
#sed -i 's/\r$//' runApp.sh