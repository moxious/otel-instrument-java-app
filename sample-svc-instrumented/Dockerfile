FROM sample-svc:latest
WORKDIR /app
COPY opentelemetry-javaagent.jar /app

# Pass the service name to otel via environment variables; 
# other config will be passed from the outside, per these docs: https://opentelemetry.io/docs/instrumentation/java/automatic/#configuring-the-agent
ENV OTEL_SERVICE_NAME=sample-svc-instrumented

ENTRYPOINT ["java", "-javaagent:/app/opentelemetry-javaagent.jar", "-jar", "/app/app.jar"]
