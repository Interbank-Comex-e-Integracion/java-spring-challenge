@REM set JAVA_HOME=C:\Program Files\Java\jdk-17.0.3.1
@REM set PATH=%JAVA_HOME%\bin;%PATH%


call antifraud\gradlew -p antifraud clean -x test build
call transaction\gradlew -p transaction clean -x test build
