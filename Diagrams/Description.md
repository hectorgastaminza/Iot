Devices send and receive commands in order to inform or perform actions.

Because MQTT uses strings to send and receive messages, in order to avoid the problem with raw data where the number zero interpreted as a string end character by several libraries, this application is going to use to communicate this command definition:

COMMAND
A command is string compound by an ID and a value [ID + Value]

Command ID: 	CJJJJ 		where J is a number (hexadecimal) from 0 to F.
Command Value:	XLLLL		where L is a number (hexadecimal) from 0 to F.

Example : [C001BX1B3F] where C001B is the command ID and X1B3F is the command value.

List of available commands are defined in enum DeviceCommands.

