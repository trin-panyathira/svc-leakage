#!/bin/sh
# ----------------------------------------------------------------------------
# Maven Start Up script for POSIX compliant systems
# ----------------------------------------------------------------------------

WRAPPER_JAR="$(dirname "$0")/.mvn/wrapper/maven-wrapper.jar"
WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

JAVA_EXE="java"

if [ ! -f "$WRAPPER_JAR" ]; then
  echo "Wrapper jar not found at $WRAPPER_JAR"
  echo "Downloading Maven Wrapper..."
  curl -sSfLo "$WRAPPER_JAR" "https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar" || exit 1
fi

exec "$JAVA_EXE" $JVM_CONFIG_MAVEN_PROPS $MAVEN_OPTS -classpath "$WRAPPER_JAR" $WRAPPER_LAUNCHER "$@" 