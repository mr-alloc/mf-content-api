#run java application code for shell

exec /usr/bin/java \
    -Duser.timezone="UTC" \
    -Dspring.profiles.active=dev \
    $JAVA_OPTS \
    -jar /app/application.jar