# Azure Functions With Java / Azure Service Bus Trigger

## Overview
Azure Functions v2 is still in beta. This example uses the latest version of the functions core tools, and _[unreleased versions of the ServiceBus extensions](https://www.myget.org/feed/azure-appservice/package/nuget/Microsoft.Azure.WebJobs.ServiceBus)_.

The sample contains the following:

* "hello" Function App handler for receiving messages
* "hello" message producer (Node.js) to run locally

## Getting Started
To work with Azure Functions v2 locally, you'll need to install the following:

* [Node.js](https://nodejs.org/en/download/)
* [.NET Core 2.x SDK](https://www.microsoft.com/net/download) (Windows, Mac, Linux)
* [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli?view=azure-cli-latest)
* Azure Functions Core Tools (2.0.1-beta.22)

> Follow the links above to setup your environment. Because this sample requires the latest core tools version, I've included the installation instructions inline.
	
### Install/Update Azure Functions Core Tools
Follow the instructions below to install _2.0.1-beta.22_ onto your machine.

```
# if already installed, check version
func --version

# windows
npm i -g azure-functions-core-tools@core

# everyone else
npm i -g azure-functions-core-tools@core --unsafe-perm true
```

## Extension Installation

The extension for ServiceBus is a separate install for v2. Since the Azure Functions Host is dotnet, the extension is going to be a `.dll`.

> TIP: The service bus extension for v2 has been separated into its own package.

### Adding The ServiceBus Extension

```
# navigate to azure-servicebus-trigger 
cd java/azure-servicebus-trigger

# execute a `func extensions install` so the functions-extensions folder and project are created 
func extensions install --package Microsoft.Azure.WebJobs.ServiceBus --version 3.0.0-beta4
```

	> The `functions-extensions` folder has been included in this sample, to aid in understanding. 

Next, navigate to the extensions folder:

```
cd functions-extensions
```


#### Update the "restore sources"

As of this writing (February 26, 2018), the latest nuget packages haven't been release yet to `nuget.org`. We will pull the latest builds from `myget.com`. Since the extensions are .NET Core, they installed using a csproj file. Add the myget to the `extensions.csproj` so that we can install the correct version of the Extensions version, as well as update the. 

```
<PropertyGroup>
    <RestoreSources>https://www.myget.org/F/azure-appservice/api/v3/index.json;https://api.nuget.org/v3/index.json</RestoreSources>
</PropertyGroup>
```

#### Add packages for Azure Functions Extensions generator and ServiceBus extension

```
# clean out existing nuget cache and install from myget
dotnet nuget locals all -c

dotnet add package Microsoft.Azure.WebJobs.Script.ExtensionsMetadataGenerator --version 1.0.0-beta3
dotnet add package Microsoft.Azure.WebJobs.ServiceBus --version 3.0.0-beta411232
```

#### Install the ServiceBus extension

```
# now execute the functions' tool extension installation with the correct version
func extensions install --package Microsoft.Azure.WebJobs.ServiceBus --version 3.0.0-beta411232 
```

Once this is done, a `bin/` folder should be generated in the root of the project. 

> INFO: This folder will get copied during packaging. The `pom.xml` includes all the necessary logic

Verify that an `extensions.json` file has been created in the `bin` folder. Its contents should be similar to the below JSON:

```
{
  "extensions":[
    { "name": "ServiceBusExtensionConfig", "typeName":"Microsoft.Azure.WebJobs.ServiceBus.Config.ServiceBusExtensionConfig, Microsoft.Azure.WebJobs.ServiceBus, Version=3.0.0.0, Culture=neutral, PublicKeyToken=null"}
  ]
}
```

## Running the Sample

### Azure Service Bus

It's assumed you have an Azure subscription and setup the following:

1. a ServiceBus namespace
2. a queue named _hello_ in that ns
3. you're able to retrieve the connection string 


### Deploy the FunctionApp

```
mvn clean package azure-functions:deploy
```

### Start the message producer

A "smoke test" node.js console app has been provided to send messages to the "hello" Azure Service Bus Queue. 

```
cd src/main/js

npm install
node helloMessageProducer.js
```
