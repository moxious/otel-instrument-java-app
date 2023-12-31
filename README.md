## Example Instrumentation of a Containerized Java App for OpenTelemetry and Grafana Cloud

This repo contains a simple sample spring boot app with a REST endpoint (which resides in `sample-svc`).  We dockerize this app and
run it without instrumentation, and then show how to instrument logs, metrics, and traces with OpenTelemetry, and report them to Grafana
Cloud, to illustrate the basics of how to instrument opaque java apps without changing their code.

### Build Sample Java App

```
cd sample-svc
./mvnw package
```

### Dockerize Java App

```
cd sample-svc 
docker build --platform=linux/amd64 -t moxious/sample-svc .
```

### Run Sample App

```
docker run -p 8080:8080 moxious/sample-svc
```

### Invoke Sample App

```
$ curl http://localhost:8080/
{"lucky":false,"hotStreak":false,"sides":6,"diceroll":1}
```

### Instrument Sample App!

The magic is in the Dockerfile, with the inclusion of open-telemetry auto-instrumentation JARs,
and including them as the java agent at runtime.

```
cd sample-svc-instrumented
docker build --platform=linux/amd64 -t moxious/sample-svc-instrumented .
```

### Configure Reporting to Grafana Cloud

The magic is in `sample-svc-instrumented/.env` which contains all of the [otel
environment variables](https://opentelemetry.io/docs/specs/otel/configuration/sdk-environment-variables/) necessary to report to Grafana Cloud.

**You must customize with your values**.  Place the file in `sample-svc-instrumented/.env`

```
OTEL_EXPORTER_OTLP_PROTOCOL=http/protobuf
OTEL_EXPORTER_OTLP_ENDPOINT=https://otlp-gateway-prod-us-east-0.grafana.net/otlp
OTEL_EXPORTER_OTLP_HEADERS=Authorization=Basic (secret see below)
OTEL_TRACES_EXPORTER=otlp
OTEL_METRICS_EXPORTER=otlp
OTEL_LOGS_EXPORTER=otlp
OTEL_SERVICE_NAME=sample-svc-instrumented
OTEL_NODE_RESOURCE_DETECTORS=all
OTEL_LOG_LEVEL=verbose
```

In particular and most important is the endpoint, and auth header information. 
Follow the instructions [in these docs](https://grafana.com/docs/grafana-cloud/monitor-infrastructure/otlp/send-data-otlp/) to obtain the right endpoint address, and generate a token that
you can use for the auth header. In the end, your auth header should be:

`Authorization=Basic $(echo -n $GRAFANA_INSTANCE_ID:$TOKEN | base64 -w 0)`

(with the right values substituted for your case)

## Run the Instrumented Container with that Environment

`docker run -p 8080:8080 --env-file ./.env sample-svc-instrumented`

**Important note**: docker doesn't strip `"` characters from the env file as bash would.  Be careful not to use double quotes around values when specified in this way.

