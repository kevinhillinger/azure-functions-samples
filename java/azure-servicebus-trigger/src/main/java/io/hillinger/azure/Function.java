package io.hillinger.azure;

import com.microsoft.azure.serverless.functions.annotation.*;
import com.microsoft.azure.serverless.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {

    @FunctionName("hello")
    public void handle(
            @ServiceBusQueueTrigger(name = "message", queueName = "hello", connection = "servicebusconnection") 
            String message,
            final ExecutionContext context) {
        
        context.getLogger().info(message);
    }
}