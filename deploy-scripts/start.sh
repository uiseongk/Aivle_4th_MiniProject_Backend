#!/bin/bash

echo "===== CodeDeploy ApplicationStart ====="

APP_DIR=/home/ec2-user/app
JAR_NAME=$(ls $APP_DIR/build/libs/*.jar | head -n 1)

echo "App directory: $APP_DIR"
echo "Jar file: $JAR_NAME"

# 기존 실행 중인 서버 종료 (있으면)
PID=$(pgrep -f 'java -jar')
if [ -n "$PID" ]; then
  echo "Stopping existing application (PID: $PID)"
  kill -15 $PID
  sleep 5
fi

# 새 서버 실행
echo "Starting Spring Boot application..."
nohup java -jar $JAR_NAME > app.log 2>&1 &

echo "Application started successfully"
