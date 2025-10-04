@ECHO OFF
@REM ----------------------------------------------------------------------------
@REM Maven Start Up Batch script for Windows
@REM ----------------------------------------------------------------------------

SETLOCAL

set ERROR_CODE=0

set MAVEN_BATCH_ECHO=
set MAVEN_OPTS=%MAVEN_OPTS% -Xms256m -Xmx1024m

set WRAPPER_JAR="%~dp0.mvn\wrapper\maven-wrapper.jar"
set WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

set JAVA_EXE=java

if exist %WRAPPER_JAR% goto runmvn
  echo Wrapper jar not found at %WRAPPER_JAR%
  echo Downloading Maven Wrapper...
  powershell -Command "[Net.ServicePointManager]::SecurityProtocol = 'Tls12'; $ProgressPreference = 'SilentlyContinue'; Invoke-WebRequest -UseBasicParsing 'https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar' -OutFile %WRAPPER_JAR%" || goto error

:runmvn
%JAVA_EXE% %JVM_CONFIG_MAVEN_PROPS% %MAVEN_OPTS% -classpath %WRAPPER_JAR% %WRAPPER_LAUNCHER% %*
if ERRORLEVEL 1 goto error
goto end

:error
set ERROR_CODE=1

:end
ENDLOCAL & set ERROR_CODE=%ERROR_CODE%
cmd /C exit /B %ERROR_CODE% 