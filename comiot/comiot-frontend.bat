@echo off
title COMIOT FRONTEND (TOMCAT WEB SERVER)
set APP_PATH=comiot-webapp\target

cls
echo ComIoT Frontend web server starting...
::pause
::java -jar %APP_PATH%\comiot-webapp-1.0.0-SNAPSHOT.war
cd comiot-webapp
mvn tomcat7:run