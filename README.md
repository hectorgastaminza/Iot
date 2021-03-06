# ComIT FINAL PROJECT: C⊙mI⊙T

## C⊙mI⊙T : Sumary
This project is giving me an opportunity to gain knowledge about The Internet of Things (IoT). The Internet of Things (IoT) is the network of physical devices, vehicles, home appliances, and other items embedded with electronics, software, sensors, actuators, and connectivity which enables these things to connect and exchange data, creating opportunities for more direct integration of the physical world into computer-based systems, resulting in efficiency improvements, economic benefits, and reduced human exertions. (https://en.wikipedia.org/wiki/Internet_of_things) 

The main goal is allowing a user to control or to get information from remote devices. For instance get the temperature in certain place of their home, turn on/off lights, etc. It should include a website/application where users could see a list of their remote devices and its information such as status and data.

## Basic description

![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comiot/master/comiot/Diagrams/DGeneralDescription.puml)

Users access to the system through the frontend, a web-app. This server communicates with a backend server, which is in charge of the logic of the system, besides it is connected to the database. The system uses a mqtt_broker for the connection between the backend and the devices. The mqtt protocol is broadly used on IOT applications. When a user is logged in, the backend retrieves all the user's information from the database as well as connects to the mqtt_broker in order to listen to messages from the user's devices. In another side of the system, the devices are connected to the mqtt_broker in order to listen to the requests from the user as well as inform changes in their state or values.

### MQTT
* http://mqtt.org/
* https://en.wikipedia.org/wiki/MQTT
* http://www.dioty.co/ (MQTT broker used for test the system)

## Features:
* Create new users.
* Users could recovery their password.
* Users can log in to the application with their username and password, to gain access.
* Users can add new remote devices.
* Users can edit the information of remote devices.
* Users can delete remote devices.
* Users can view all the information of their remote devices.
* Application have to update the information received from the remote devices.
* (Not implemented yet)
    * Users can add new places/locations. 
    * Users can edit places.
    * Users can assign remote devices to a place.

![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comiot/master/comiot/Diagrams/DUsesCases.puml)

## The information required to store is:
* List of users, with is login information.
* List of places/location where a user could have remote devices (house, work, car, living room, etc.)
* List of remote devices and its configuration data.
* Connection information (MQTT broker credentials).

### Database
![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comiot/master/comiot/Diagrams/DDatabase.puml)

[Database creation script](comiot/Resources/database_create_script.sql)

## Implementation details

### Main processes

#### User login
![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comiot/master/comiot/Diagrams/DSeqLogin.puml)

#### User sending a command to a device

![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comiot/master/comiot/Diagrams/DSeqUserSendCmd.puml)

![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comiot/master/comiot/Diagrams/DSeqMQTTSendCmd.puml)

#### Device sending information to the server

![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comiot/master/comiot/Diagrams/DSeqDeviceSendCmd.puml)

![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comiot/master/comiot/Diagrams/DSeqUserReceiveCmd.puml)

### BACKEND (UserModel background service)
![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comiot/master/comiot/Diagrams/DDeviceServer.puml)

### Device client
![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comiot/master/comiot/Diagrams/DDeviceClient.puml)

## Communication with devices

Devices send and receive commands in order to inform or perform actions.

![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comiot/master/comiot/Diagrams/DProtocol.puml)

Because MQTT uses strings to send and receive messages through different topics. Looking for more flexibility, C⊙mI⊙T uses its own protocol through just one main topic in the MQTT broker. It is going to use to communicate this command definition:

## DEVICE COMMANDS
A command is string compound by segments which have an ID and a value [ID + Value]. Sometimes Value is useless as in a HEADER so it is not included. There are two kind of commands implemented but depends on the requeriments it could be extended.

### REQUEST COMMAND             (User to Device)

| Segment       | Value  | Description                                   |
|:-------------:|:------:|:---------------------------------------------:|
| HEADER        | RQ     |                                               |
| Place ID      | PXX    | where X is a number (hexadecimal) from 0 to F |
| Device ID     | IXX 	 | where X is a number (hexadecimal) from 0 to F |
| Command ID    | TXX    | where X is a number (hexadecimal) from 0 to F |
| Command Value | VXXXX  | where X is a number (hexadecimal) from 0 to F |

Example : [RQP01I0AT02V1B3F] where RQ is a request command, P01 is the place, I0A is the device ID, T02 is the command ID and V1B3F is the command value.

#### Request Command List
List of available commands are defined in enum [eDeviceCommands](comiot/comiot-core/src/main/java/comiot/core/device/command/eDeviceCommands.java)

| COMMAND     | VALUE  |
|:-----------:|:------:|
| RESET       | 0xFF   |
| GET_STATUS  | 0x01   |
| SET_VALUE   | 0x02   |
| OFF         | 0x03   |
| ON          | 0x04   |
| UP          | 0x05   |
| DOWN        | 0x06   |

### REFRESH STATE COMMAND       (Device to user)

| Segment   | Value  | Description                                   |
|:---------:|:------:|:---------------------------------------------:|
| HEADER    | RS     |                                               |
| Place ID  | PXX    | where X is a number (hexadecimal) from 0 to F |
| Device ID | IXX 	 | where X is a number (hexadecimal) from 0 to F |
| State ID  | SXX    | where X is a number (hexadecimal) from 0 to F |
| Value     | VXXXX  | where X is a number (hexadecimal) from 0 to F |

#### Device States List
List of available states are defined in enum [eDeviceCommands](comiot/comiot-core/src/main/java/comiot/core/device/eDeviceStates.java)

| COMMAND      | VALUE  |
|:------------:|:------:|
| DISCONNECTED | 0x00   |
| OFF          | 0x01   |
| ON           | 0x02   |
| ON_VALUE     | 0x03   |

## ADDING NEW DEVICES
This project is made to be extended. User / Server side and device side could be connected through the communication protocol. Devices could be implemented without any restriction of programming language.

### Creating a new java device
It could be easy to implement a new device extending the class [Device](comiot/comiot-core/src/main/java/comiot/core/device/Device.java). The communication to or from the User/Server is solved by this class. 
Typically overwriting some of these methods could be enough.

![PUML](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/hectorgastaminza/comiot/master/comiot/Diagrams/DClassDevice.puml)

Otherwise, more behaviors could be added to a class device, and new types of commands could be added to eDeviceCommands, also new states could be added to eDeviceStates. In most of the cases, with this changes the most of devices could work with C⊙mI⊙T. 

See for example [comiot-client-raspberry project](comiot/comiot-client-raspberry/src/main/java/comiot/client/raspberry/device/DeviceRaspberryGpio.java).

## REPOSITORY
### comiot-core (maven project)
Implements the main code of the system. It is used for the other projects.
* application
    * common: implements [AppConnection](comiot/comiot-core/src/main/java/comiot/core/application/common/AppConnection.java)  a facade class used by client and server to communicate with a mqtt broker.
    * client: implements [DeviceClient](comiot/comiot-core/src/main/java/comiot/core/application/client/DeviceClient.java) which simplify create command-line application for devices.
    * server: implements classes required in the server side of the system as [User](comiot/comiot-core/src/main/java/comiot/core/application/server/User.java), [Place](comiot/comiot-core/src/main/java/comiot/core/application/server/Place.java), and a facade class to simplify the server side control [DeviceServer](comiot/comiot-core/src/main/java/comiot/core/application/server/DeviceServer.java)
* database: [DBConnector](comiot/comiot-core/src/main/java/comiot/core/database/DBConnector.java) implements the SQL code. It is the interface with the Database. (java.sql)
* device: Implements the [Device](comiot/comiot-core/src/main/java/comiot/core/device/Device.java) base class
    * command: Implements the C⊙mI⊙T [DeviceCommand](comiot/comiot-core/src/main/java/comiot/core/device/command/DeviceCommand.java) and among others [DeviceCommandDispatcher](comiot/comiot-core/src/main/java/comiot/core/device/command/DeviceCommandDispatcher.java)
    * protocol: Implements [low-level](comiot/comiot-core/src/main/java/comiot/core/device/protocol/ProtocolSegment.java) classes to compound a command.
* email: Implements [EmailSender](comiot/comiot-core/src/main/java/comiot/core/email/EmailSender.java) using javax.mail
* protocol
    * [mqtt](comiot/comiot-core/src/main/java/comiot/core/protocol/mqtt/MqttConnection.java): Implements the communication with MQTT brokers using eclipse.paho.client.mqttv3
### comiot-backend (maven project)
* [Controller](comiot/comiot-backend/src/main/java/comiot/backend/controller/DeviceController.java): Contains the rest server code. Receives and responses request from frontend which are performed by UserModel. (spring-boot, @RestController, @Autowired, @RequestMapping, @RequestParam, @RequestBody)
* [UserModel](comiot/comiot-backend/src/main/java/comiot/backend/UserModel.java): This server runs a thread with the UserModel which implement the logic of the server side. (HashMap, concurrent.ExecutorService)
### comiot-webapp (maven project)
* servlet: implements servlets to communicate with backend (javax.servlet, apache.http, springframework, [RestTemplate](comiot/comiot-webapp/src/main/webapp/servlet/HomeServlet.java), jackson)
* WEB-INF: implements user interface. (Bootstrap, html, css, [javascript](comiot/comiot-webapp/src/main/webapp/WEB-INF/views/home.jsp))
### comiot-client-raspberry (maven project)
Device application for raspberry pi
* [DeviceRaspberryDS18B20](comiot/comiot-client-raspberry/src/main/java/comiot/client/raspberry/device/DeviceRaspberryDS18B20.java): temperature sensor.
* [DeviceRaspberryGpio](comiot/comiot-client-raspberry/src/main/java/comiot/client/raspberry/device/DeviceRaspberryGpio.java): general in/out (leds, reles, etc) (pi4j.io.gpio).
### comiot-client-virtual (maven project)
Device simulated in order to test the application. Sends a receive commands.
