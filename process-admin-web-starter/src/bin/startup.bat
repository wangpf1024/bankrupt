@echo off
setlocal

REM ==========================
REM Initialization
REM ==========================
REM Determine the base directory
%~d0

cd %~dp0

set JAVA_HOME=C:\soft\jdk-17

set PATH=%JAVA_HOME%\bin;%PATH%

set "JVM_OPTS=-Xms512m -Xmx1024m -Dfile.encoding=UTF-8"

set "APP_NAME=process-admin-web-starter"

set "LOG_FILE=%APP_NAME%.log"

java %JVM_OPTS% -cp "process-admin-web-starter-1.0.0.jar;lib/*" com.pansome.AdminWebApplication --spring.config.location="file:config/application.yml,file:config/application-druid.yml" > "%LOG_FILE%" 2>&1

endlocal
