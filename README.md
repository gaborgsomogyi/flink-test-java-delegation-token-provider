flink-test-java-delegation-token-provider
=========================================

### Introduction

Minimal Flink java delegation token provider.
This mainly serves testing purposes. One can see how and what Flink does with delegation tokens.

### Build the app
To build, you need Java 11, git and maven on the box.
Do a git clone of this repo and then run:
```
cd flink-test-java-delegation-token-provider
mvn clean package
cp target/flink-test-java-delegation-token-provider-1.0.jar ${FLINK_HOME}/lib/
```

### Prerequisites

To run, you need Minikube + FLink Kubernetes Operator.
Please take a look at how to start [here](https://nightlies.apache.org/flink/flink-kubernetes-operator-docs-main/docs/try-flink-kubernetes-operator/quick-start/).

### Load it from Flink
```
kubectl apply -f your-example-application.yaml
```
