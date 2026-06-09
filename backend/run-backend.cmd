@echo off
set "JAVA_HOME=D:\develop tools\Java\jdk1.8.0_281"
set "PATH=%JAVA_HOME%\bin;E:\dev tools\apache-maven-3.8.8\bin;%PATH%"

cd /d "%~dp0"
"E:\dev tools\apache-maven-3.8.8\bin\mvn.cmd" spring-boot:run
