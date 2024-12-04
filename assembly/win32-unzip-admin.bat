@echo off

%~d0

cd %~dp0


REM Define variables
set ZIP_FILE=../process-admin-web-starter/target/process-admin-web-starter-1.0.0-win.zip
set EXTRACT_DIR=servers/process-admin

REM Check if the ZIP file exists
if not exist %ZIP_FILE% (
    echo ZIP file %ZIP_FILE% does not exist.
    goto :end
)

REM Check if the EXTRACT_DIR exists and delete it
if exist %EXTRACT_DIR% (
    echo Deleting existing directory %EXTRACT_DIR%...
    rmdir /S /Q %EXTRACT_DIR%
    if %errorlevel% neq 0 (
        echo Failed to delete directory %EXTRACT_DIR%.
        goto :end
    )
)

REM Unzip the file
echo Unzipping %ZIP_FILE%...
7z x -y %ZIP_FILE% -o%EXTRACT_DIR%

REM Check if the unzip was successful
if %errorlevel% neq 0 (
    echo Failed to unzip %ZIP_FILE%.
    goto :end
)

echo Unzip successful. Files extracted to %EXTRACT_DIR%.

:end pause
