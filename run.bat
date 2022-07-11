@ECHO OFF
CLS
"%JAVA_HOME%\bin\javac" -d classes src\*.java
"%JAVA_HOME%\bin\java" -cp classes csvParsing
