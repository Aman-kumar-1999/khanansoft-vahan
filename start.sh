#!/bin/bash

# KhananSoft Application Startup Script
# This script starts the Spring Boot application with optimized JVM settings

# Set JVM Memory Configuration
export JAVA_OPTS="
  -Xmx2G
  -Xms1G
  -Xmn512M
  -XX:+UseG1GC
  -XX:MaxGCPauseMillis=200
  -XX:+PrintGCDetails
  -XX:+PrintGCDateStamps
  -Xloggc:gc.log
  -XX:+HeapDumpOnOutOfMemoryError
  -XX:HeapDumpPath=./heap-dump.hprof
"

echo "Starting KhananSoft Application..."
echo "JVM Options: $JAVA_OPTS"
echo ""

# Navigate to project directory
cd "$(dirname "$0")"

# Option 1: Run via Maven
echo "Building and running application..."
mvn spring-boot:run -Dspring-boot.run.jvmArguments="$JAVA_OPTS"

# Option 2: If you have a built JAR file, uncomment below:
# java $JAVA_OPTS -jar target/demo-0.0.1-SNAPSHOT.jar

echo "Application started!"
echo "Access at http://localhost:8080"
