package net.xerosoft.kafka;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.kafka.client.consumer.KafkaConsumer;

public class KafkaConsumerVerticle extends AbstractVerticle {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerVerticle.class);

    private KafkaConsumer<String, Form> consumer;
    private String topic;
    private Consumer<Form> handler;
    private String bootstrapServers;

    public KafkaConsumerVerticle(String bootstrapServers, String topic, Consumer<Form> handler) {
        this.topic = topic;
        this.handler = handler;
        this.bootstrapServers = bootstrapServers;
    }

    @Override
    public void start() throws Exception {
        Map<String, String> config = new HashMap<>();
        config.put("bootstrap.servers", bootstrapServers);
        config.put("key.deserializer", StringDeserializer.class.getName());
        config.put("value.deserializer", FormDeserializer.class.getName());
        config.put("group.id", "my_consumer_group");
        config.put("enable.auto.commit", "false");

        consumer = KafkaConsumer.create(vertx, config);
        
        consumer.handler(record -> {
            handler.accept(record.value());
            consumer.commit();
        });

        consumer.subscribe(topic);
        log.info("subscribed to topic: {}", topic);

        super.start();
    }

    @Override
    public void stop() throws Exception {
        consumer.close(res -> {
            if (res.succeeded()) {
                log.info("consumer is now closed");
            } else {
                log.warn("failed to close consumer");
            }
        });
        super.stop();
    }
}
