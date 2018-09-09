@echo off
title COMIOT BACKEND (REST SERVER)
set APP_PATH=comiot-backend\target

cls
echo ComIoT Backend server starting...
::pause
java -jar %APP_PATH%\comiot-backend-1.0.0-SNAPSHOT.jar