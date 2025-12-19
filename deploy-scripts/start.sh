#!/bin/bash

APP_DIR=/opt/app
JAR=$(ls $APP_DIR/*.jar | head -n 1)

echo "Starting app with $JAR"

nohup java -jar $JAR > /opt/app/app.log 2>&1 &
