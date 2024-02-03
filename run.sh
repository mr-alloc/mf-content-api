#run java application code for shell

exec /usr/bin/java \
    -Duser.timezone="UTC" \
    -Dspring.profiles.active=local \
    $JAVA_OPTS \
    -jar /app/application.jar