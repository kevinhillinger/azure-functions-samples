package io.hillinger.azure;

import java.util.*;

import com.microsoft.azure.serverless.functions.annotation.*;

import io.hillinger.designpatterns.Singleton;

import com.microsoft.azure.serverless.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
    
    private Singleton singleton;
    
    public Function() {
        this.singleton = Singleton.getInstance();
    }
    
    @FunctionName("singleton")
    public HttpResponseMessage<String> handle(
            @HttpTrigger(name = "req", methods = {"get", "post"}, authLevel = AuthorizationLevel.ANONYMOUS) 
            HttpRequestMessage<Optional<String>> request, final ExecutionContext context) {
        int singletonId = singleton.getId();
        context.getLogger().info("Singleton Id = " + singletonId);

        // Parse query parameter
        String query = request.getQueryParameters().get("name");
        String name = request.getBody().orElse(query);

    
        HttpResponseMessage<String> resp = request.createResponse(200, "Hello, " + name + " from Singleton " + singletonId);
        resp.addHeader("Content-Type","text/html");
        
        return resp;

    }
}
