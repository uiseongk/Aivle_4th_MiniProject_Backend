#!/bin/bash

PID=$(pgrep -f 'java -jar')

if [ -n "$PID" ]; then
  echo "Stopping application (PID: $PID)"
  kill -15 $PID
else
  echo "No application is running"
fi
