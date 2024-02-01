#run java application code for shell

exec /usr/bin/java \
    -Duser.timezone="UTC" \
    $JAVA_OPTS \
    -jar /app/application.jar