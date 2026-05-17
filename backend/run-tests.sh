#!/bin/bash
# ============================================================
# StudySync — Offline Test Runner (Assignment 12)
# ============================================================
# Runs all 108 JUnit tests without Maven Central access.
# Requires: openjdk-21-jdk, libspring-*-java, junit5, libmockito-java
#
# To install dependencies (Ubuntu/Debian):
#   sudo apt-get install openjdk-21-jdk junit5 libmockito-java \
#     libspring-web-java libspring-core-java libspring-beans-java \
#     libspring-context-java libspring-aop-java
# ============================================================

set -e
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
BUILD_DIR="$SCRIPT_DIR/build-offline"
STUBS_JAR="$SCRIPT_DIR/lib/studysync-stubs.jar"

# Verify JARs exist
for jar in /usr/share/java/junit-jupiter-api-5.10.1.jar \
           /usr/share/java/mockito-core-2.23.0.jar \
           /usr/share/java/spring3-web.jar; do
    if [ ! -f "$jar" ]; then
        echo "ERROR: Missing $jar — run: sudo apt-get install junit5 libmockito-java libspring-web-java libspring-core-java libspring-beans-java libspring-context-java libspring-aop-java"
        exit 1
    fi
done

CLASSPATH="/usr/share/java/spring3-core.jar:\
/usr/share/java/spring3-web.jar:\
/usr/share/java/spring3-beans.jar:\
/usr/share/java/spring3-context.jar:\
/usr/share/java/spring3-aop.jar:\
/usr/share/java/commons-logging.jar:\
/usr/share/java/junit-jupiter-api-5.10.1.jar:\
/usr/share/java/junit-jupiter-engine-5.10.1.jar:\
/usr/share/java/junit-platform-commons.jar:\
/usr/share/java/junit-platform-engine.jar:\
/usr/share/java/junit-platform-launcher.jar:\
/usr/share/java/opentest4j.jar:\
/usr/share/java/apiguardian-api.jar:\
/usr/share/java/mockito-core-2.23.0.jar:\
/usr/share/java/byte-buddy-1.14.13.jar:\
/usr/share/java/byte-buddy-agent.jar:\
/usr/share/java/objenesis-3.3.jar:\
/usr/share/java/hamcrest.jar:\
$STUBS_JAR"

echo "==> Compiling main sources..."
mkdir -p "$BUILD_DIR/classes" "$BUILD_DIR/test-classes"
SRC=$(find "$SCRIPT_DIR/src/main/java" -name "*.java" | tr '\n' ' ')
javac --release 21 -cp "$CLASSPATH" -d "$BUILD_DIR/classes" $SRC

echo "==> Compiling test sources..."
TSRC=$(find "$SCRIPT_DIR/src/test/java" -name "*.java" | tr '\n' ' ')
javac --release 21 -cp "$CLASSPATH:$BUILD_DIR/classes" -d "$BUILD_DIR/test-classes" $TSRC

echo "==> Running tests..."
java -javaagent:/usr/share/java/byte-buddy-agent.jar \
  -cp "$CLASSPATH:$BUILD_DIR/classes:$BUILD_DIR/test-classes:/usr/share/java/junit-platform-console-standalone-1.9.1.jar" \
  org.junit.platform.console.ConsoleLauncher \
  --scan-classpath="$BUILD_DIR/test-classes" \
  --include-engine=junit-jupiter
