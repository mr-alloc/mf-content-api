FROM azul/zulu-openjdk-alpine:17-latest
LABEL authors="devisitem"
WORKDIR /app
COPY build/libs/*.jar /app/application.jar
RUN java -Djarmode=layertools -jar application.jar extract

COPY run.sh ./
ENTRYPOINT ["/bin/sh", "run.sh"]