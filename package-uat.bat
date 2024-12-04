@echo off

%~d0

cd %~dp0

REM Save the current JAVA_HOME
set OLD_JAVA_HOME=%JAVA_HOME%

REM Set JAVA_HOME to JDK 17
set JAVA_HOME=C:\soft\jdk-17

REM Add the new JAVA_HOME to PATH
set PATH=%JAVA_HOME%\bin;%PATH%

REM Run Maven build
mvn clean install package -Dmaven.test.skip=true -P uat

pause
