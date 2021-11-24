del /s bin\*
javac -d bin epos\*.java
jar cvfm epos.jar manifest.txt sqlite-jdbc-3.5.9-universal.jar -C bin epos
java -jar epos.jar
