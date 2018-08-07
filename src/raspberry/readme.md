Running in command line
cd /src/demo 
javac -classpath "..:../mqtt/org.eclipse.paho.client.mqttv3-1.0.2.jar:" Main.java
java -classpath "..:../mqtt/org.eclipse.paho.client.mqttv3-1.0.2.jar:" demo.Main

Running in raspberry
cd /src/raspberry 
javac -classpath "..:../mqtt/org.eclipse.paho.client.mqttv3-1.0.2.jar:/opt/pi4j/lib/*" Main.java
java -classpath "..:../mqtt/org.eclipse.paho.client.mqttv3-1.0.2.jar:/opt/pi4j/lib/*" raspberry.Main
sudo poweroff
