flink-test-java-delegation-token-provider
=========================================

### Introduction

Minimal Flink java delegation token provider.
This mainly serves testing purposes. One can see how and what Flink does with delegation tokens.

### Build the app
To build, you need Java 1.8, git and maven on the box.
Do a git clone of this repo and then run:
```
cd flink-test-java-delegation-token-provider
mvn clean package
cp target/flink-test-java-delegation-token-provider-1.0.jar ${FLINK_HOME}/lib/
```

### Prerequisites

To run, you need a running Kubernetes cluster.
If you have problems setting up a Kubernetes cluster, then take a look at
[how to setup a Kubernetes cluster for Flink](https://nightlies.apache.org/flink/flink-docs-release-1.14/docs/deployment/resource-providers/native_kubernetes/).

### Load it from Flink
```
flink run \
    --target kubernetes-session \
    -Dkubernetes.cluster-id=delegation-token-flink-cluster \
    -yD security.kerberos.login.keytab=/path/to/your.keytab \
    -yD security.kerberos.login.principal=user@YOURCOMPANY.COM \
    workload.jar
```

### Example log
```
TODO
```
