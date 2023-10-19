## K6 Test

This subdirectory implements a simple load test we can run on our services
we are instrumenting.

[K6 Learn](https://github.com/grafana/k6-learn) is a great resource for
understanding k6 as a tool and what's going on in this subdirectory.

1. [Install k6](https://k6.io/docs/get-started/installation/)
2. Run the docker container provided in this repo (instrumented or not)
3. Run the k6 load test like this:

```
k6 run test.js
```
