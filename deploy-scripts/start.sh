#!/bin/bash

echo "===== CodeDeploy ApplicationStart ====="

APP_DIR=/opt/app
JAR_FILE=$(ls $APP_DIR/*.jar | head -n 1)

echo "App directory: $APP_DIR"
echo "Jar file: $JAR_FILE"

if [ -z "$JAR_FILE" ]; then
  echo "❌ No jar file found in $APP_DIR"
  exit 1
fi

# 기존 Spring Boot 종료
PID=$(pgrep -f 'java -jar')
if [ -n "$PID" ]; then
  echo "Stopping existing application (PID: $PID)"
  kill -15 $PID
  sleep 5
fi

# 서버 실행
echo "Starting Spring Boot application..."
nohup java -jar $JAR_FILE > $APP_DIR/app.log 2>&1 &

echo "✅ Application started"
