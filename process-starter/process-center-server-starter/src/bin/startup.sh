#!/bin/sh

# ==========================
# Initialization
# ==========================
# Determine the base directory
BASEDIR=$(dirname "$0")

# Determine the jar file
JARFILE="process-center-server-starter-1.0.0.jar"

# Define the name of the application
APP_NAME="process-center-server-starter"

# Define the PID file
PID_FILE="$BASEDIR/$APP_NAME.pid"

# Define log file
LOG_FILE="$BASEDIR/$APP_NAME.log"

# Define custom JVM options
JVM_OPTS="-Xms512m -Xmx1024m"

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
    if [ -f "$PID_FILE" ] && kill -0 "$(cat "$PID_FILE")"; then
        echo "Application is already running."
        exit 1
    fi

    echo "Starting application..."
    nohup java $JVM_OPTS -cp "$JARFILE;lib/*" cn.bankrupt.workflow.CenterApplication > "$LOG_FILE" 2>&1 &
    echo $! > "$PID_FILE"
    echo "Application started with PID $(cat "$PID_FILE")"
}

stop_app() {
    if [ ! -f "$PID_FILE" ] || ! kill -0 "$(cat "$PID_FILE")"; then
        echo "Application is not running."
        exit 1
    fi

    echo "Stopping application..."
    kill "$(cat "$PID_FILE")"
    rm -f "$PID_FILE"
    echo "Application stopped."
}

status_app() {
    if [ -f "$PID_FILE" ] && kill -0 "$(cat "$PID_FILE")"; then
        echo "Application is running with PID $(cat "$PID_FILE")"
    else
        echo "Application is not running."
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