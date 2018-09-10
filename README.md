# ComIT FINAL PROJECT

## Sumary:
This project is giving me an opportunity to gain knowledge about The Internet of Things (IoT). The Internet of Things (IoT) is the network of physical devices, vehicles, home appliances, and other items embedded with electronics, software, sensors, actuators, and connectivity which enables these things to connect and exchange data, creating opportunities for more direct integration of the physical world into computer-based systems, resulting in efficiency improvements, economic benefits, and reduced human exertions. (https://en.wikipedia.org/wiki/Internet_of_things) 

The main goal is allowing a user to control or to get information from remote devices. For instance get the temperature in certain place of their home, turn on/off lights, etc. It should include a website/application where users could see a list of their remote devices and its information such as status and data.

![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comit/master/FinalProject/Diagrams/DGeneralDescription.puml)

### MQTT
http://mqtt.org/
https://en.wikipedia.org/wiki/MQTT

## The information required to store is:
- List of users, with is login information.
- List of places/location where a user could have remote devices (house, work, car, living room, etc.)
- List of remote devices and its configuration data.
- Connection information (MQTT server credentials).

![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comit/master/FinalProject/Diagrams/DUsesCases.puml)

## Features:
- Create new users.
- Users could recovery their password.
- Users can log in to the application with their username and password, to gain access.
- Users can add new remote devices.
- Users can edit the information of remote devices.
- Users can delete remote devices.
- Users can assign remote devices to a place.
- Users can view all the information of their remote devices.
- Application have to update the information received from the remote devices.
(Not implemented yet)
- Users can add new places/locations. 
- Users can edit places.

## Implementation details

### Main processes
![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comit/master/FinalProject/Diagrams/DSeqLogin.puml)

![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comit/master/FinalProject/Diagrams/DSeqDeviceSendCmd.puml)

![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comit/master/FinalProject/Diagrams/DSeqUserSendCmd.puml)

![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comit/master/FinalProject/Diagrams/DSeqMQTTSendCmd.puml)

![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comit/master/FinalProject/Diagrams/DSeqDeviceSendCmd.puml)

![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comit/master/FinalProject/Diagrams/DSeqUserReceiveCmd.puml)

### Database
![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comit/master/FinalProject/Diagrams/DDatabase.puml)

### BACKEND (UserModel background service)
![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comit/master/FinalProject/Diagrams/DDeviceServer.puml)

### Device client
![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comit/master/FinalProject/Diagrams/DDeviceClient.puml)



![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comit/master/FinalProject/Diagrams/DEntities.puml)

![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comit/master/FinalProject/Diagrams/DProtocol.puml)





MQTT: http://mqtt.org/

Devices send and receive commands in order to inform or perform actions.

Because MQTT uses strings to send and receive messages, in order to avoid the problem with raw data where the number zero interpreted as a string end character by several libraries, this application is going to use to communicate this command definition:

## DEVICE COMMAND
A command is string compound by an ID and a value [ID + Value]

- Device ID: 		IXX 		where X is a number (hexadecimal) from 0 to F.
- Command ID: 	    TXX 		where X is a number (hexadecimal) from 0 to F.
- Command Value:	VXXXX		where X is a number (hexadecimal) from 0 to F.

Example : [I0AT1BV1B3F] where I0A is the device ID, T1B is the command ID and V1B3F is the command value.

List of available commands are defined in enum DeviceCommands.

## DEVICE COMMANDS LIST 
#### * NONE (C00)
#### * RESET(0xFF)
#### * GET_STATUS (C01)
#### * SET_VALUE (C02)
#### * OFF (C03)
#### * ON (C04)
#### * UP (C05)
#### * DOWN (C06)
