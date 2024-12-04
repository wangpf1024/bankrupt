#!/bin/sh

# ==========================
# Initialization
# ==========================
# Determine the base directory
BASEDIR=$(dirname "$0")

# Determine the jar file
JARFILE="process-admin-web-starter-1.0.0.jar"

# Define the name of the application
APP_NAME="process-admin-web-starter"

# Define the name of the application
Main_CLASS="com.pansome.AdminWebApplication"

# Define the PID file
PID_FILE="$BASEDIR/$APP_NAME.pid"

# Define log file
LOG_FILE="$BASEDIR/$APP_NAME.log"

# Define custom JVM options
JVM_OPTS="-Xms1024m -Xmx2028m -Dfile.encoding=UTF-8"

# ==========================
# Functions
# ==========================
check_java() {
    if ! command -v java > /dev/null 2>&1; then
        echo "Java is not installed. Please install Java and try again."
        exit 1
    fi
}

start_app() {
    if [ -f "$PID_FILE" ] && kill -9 "$(cat "$PID_FILE")"; then
        echo "Application is already running."
        exit 1
    fi

    echo "Starting application..."
    nohup java $JVM_OPTS -cp "$JARFILE:lib/*" $Main_CLASS --spring.config.location=file:config/application.yml,file:config/application-druid.yml> $LOG_FILE 2>&1 &
    echo $! > "$PID_FILE"
    echo "Application started with PID $(cat "$PID_FILE")"
}


stop_app() {
    if [ ! -f "$PID_FILE" ] || ! kill -0 "$(cat "$PID_FILE")" 2>/dev/null; then
        echo "Application is not running or PID file is missing."
        exit 1
    fi

    echo "Stopping application..."
    kill "$(cat "$PID_FILE")"
    TIMEOUT=10
    COUNT=0

    while kill -0 "$(cat "$PID_FILE")" 2>/dev/null; do
        sleep 1
        COUNT=$((COUNT + 1))
        if [ "$COUNT" -ge "$TIMEOUT" ]; then
            echo "Application did not stop gracefully. Force stopping..."
            kill -9 "$(cat "$PID_FILE")"
            break
        fi
    done

    rm -f "$PID_FILE"
    echo "Application stopped."
}



status_app() {
    if [ ! -f "$PID_FILE" ]; then
        echo "PID file not found. Application is not running."
        return
    fi

    PID=$(cat "$PID_FILE")
    if kill -0 "$PID" 2>/dev/null; then
        echo "Application is running with PID $PID"
    else
        if [ $? -eq 1 ]; then
            echo "Application is not running. Process with PID $PID is not found."
        elif [ $? -eq 2 ]; then
            echo "Insufficient permissions to check the process with PID $PID."
        fi
    fi
}



# ==========================
# Main
# ==========================
case "$1" in
    start)
        check_java
        start_app
        ;;
    stop)
        stop_app
        ;;
    status)
        status_app
        ;;
    restart)
        stop_app
        start_app
        ;;
    *)
        echo "Usage: $0 {start|stop|status|restart}"
        exit 1
        ;;
esac

exit 0