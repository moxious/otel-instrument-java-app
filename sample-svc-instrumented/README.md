## Instrumenting a Java Container

### Download auto-instrumentation JAR from OpenTelemetry (one provided in this repo)

Follow OpenTelemetry [autoinstrumentation for Java instructions here](https://opentelemetry.io/docs/instrumentation/java/automatic/)

We'll need this file to build the instrumented container.

### Rebuild container, adding this file as the java agent 

```
docker build --platform=linux/amd64 -t moxious/sample-svc-instrumented .
```

### Customize .env for your backend

We will be reporting otel logs, metrics, and traces to Grafana Cloud as an endpoint; you must customize your endpoint
information in the `.env` file.

### Run the instrumented container with environment variables specified

```
docker run -p 8080:8080 --env-file ./.env moxious/sample-svc-instrumented 
```
