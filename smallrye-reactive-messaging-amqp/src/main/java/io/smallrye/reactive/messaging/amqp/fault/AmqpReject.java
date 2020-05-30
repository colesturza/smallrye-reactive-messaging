package io.smallrye.reactive.messaging.amqp.fault;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.slf4j.Logger;

import io.smallrye.reactive.messaging.amqp.AmqpMessage;

public class AmqpReject implements AmqpFailureHandler {

    private final Logger logger;
    private final String channel;

    public AmqpReject(Logger logger, String channel) {
        this.logger = logger;
        this.channel = channel;
    }

    @Override
    public <V> CompletionStage<Void> handle(AmqpMessage<V> msg, Throwable reason) {
        // We mark the message as rejected and fail.
        logger.warn("A message sent to channel `{}` has been nacked, ignoring the failure and mark the message as rejected",
                channel);
        logger.debug("The full ignored failure is", reason);
        msg.getAmqpMessage().rejected();
        return CompletableFuture.completedFuture(null);
    }
}
