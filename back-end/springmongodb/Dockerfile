FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /opt/app
COPY .mvn/ .mvn
COPY mvnw mvnw
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install
 
FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/app
# Add Open Telemetry agent
ADD bin/opentelemetry-javaagent.jar /opt/app/opentelemetry-javaagent.jar
# JMX
EXPOSE 9010
# JVM debugging port
EXPOSE 8081
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
ENV JAVA_TOOL_OPTIONS="\
  -Dsun.management.jmxremote.level=FINEST \
  -Dsun.management.jmxremote.handlers=java.util.logging.ConsoleHandler \
  -Djava.util.logging.ConsoleHandler.level=FINEST \
  -Dcom.sun.management.jmxremote.local.only=false \
  -Dcom.sun.management.jmxremote.ssl=false \
  -Dcom.sun.management.jmxremote.authenticate=false \
  -Dcom.sun.management.jmxremote.port=9010 \
  -Dcom.sun.management.jmxremote.rmi.port=9010 \
  -Dcom.sun.management.jmxremote.host=0.0.0.0 \
  -Djava.rmi.server.hostname=0.0.0.0 \
  -Dotel.jmx.service.url=service:jmx:rmi:///jndi/rmi://api:9010/jmxrmi \
  -Dotel.jmx.target.system=jvm \
  -Dotel.jmx.interval.milliseconds=5000 \
  -Dotel.jmx.remote.registry.ssl=false \
  -Dotel.metrics.exporter=otlp \  
  -Dotel.exporter.otlp.endpoint=http://otel-collector:4318 \
  -Dotel.service.name=booksapi \
  -Dspring.profiles.active=docker"
ENTRYPOINT ["java", "-javaagent:/opt/app/opentelemetry-javaagent.jar", "-jar", "/opt/app/*.jar" ]
