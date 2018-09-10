Running in command line
cd /src/demo 
javac -classpath "..:../mqtt/*:" Main.java
java -classpath "..:../mqtt/*:" demo.Main

Running in raspberry
cd /src/raspberry 
find .. -name "*.class" -type f
find .. -name "*.class" -type f -delete
javac -classpath "..:../mqtt/*:/opt/pi4j/lib/*" Main.java
java -Dpi4j.linking=dynamic -classpath "..:../mqtt*:/opt/pi4j/lib/*" raspberry.Main
sudo poweroff
