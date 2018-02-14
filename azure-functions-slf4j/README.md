# Azure Functions SLF4J Sample

This sample demonstrates the use of the Azure Functions feature of supporting a `lib` directory for your Java dependencies.

## Running locally

In another terminal session, pull down the `dev` branch of the azure functions java worker, and compile it.

```
git clone -b dev https://github.com/Azure/azure-functions-java-worker && cd azure-functions-java-worker
mvn clean package
```

Next, install the Azure Functions core tools, and copy the latest worker

```
CORE_TOOLS_PATH=/usr/local/lib/node_modules/azure-functions-core-tools/bin/workers/java/

sudo npm install -g azure-functions-core-tools@core --unsafe-perm true
sudo mv ${CORE_TOOLS_PATH}/azure-functions-java-worker.java ${CORE_TOOLS_PATH}/azure-functions-java-worker.java.current

sudo cp <directory of azure functions java worker clone>/azure-functions-java-worker/target/azure-functions-java-worker-1.0.0-beta-2.jar ${CORE_TOOLS_PATH}/azure-functions-java-worker.jar
```

Finally, run the sample:

```
mvn clean package azure-functions:run
```