@ECHO OFF
CLS
"C:\Program Files\Java\jdk-11.0.7\bin\javac" -d classes src\*.java
"C:\Program Files\Java\jdk-11.0.7\bin\java" -cp classes csvParsing