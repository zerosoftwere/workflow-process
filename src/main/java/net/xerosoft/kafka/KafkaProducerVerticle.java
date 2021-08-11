package net.xerosoft.kafka;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;

public class KafkaProducerVerticle extends AbstractVerticle {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerVerticle.class);

    private KafkaProducer<String, String> producer;

    @Override
    public void start() throws Exception {
        Map<String, String> config = new HashMap<>();
        config.put("bootstrap.servers", "localhost:9091,localhost:9092");
        config.put("key.serializer", StringSerializer.class.getName());
        config.put("value.serializer", StringSerializer.class.getName());
        config.put("acks", "1");

        producer = KafkaProducer.create(vertx, config);

        vertx.setPeriodic(2000, handler -> {
            KafkaProducerRecord<String, String> record = KafkaProducerRecord.create("test", "Hello sent at: " + Instant.now());
            producer.write(record, done -> {
                if (done.succeeded()) {
                    log.info("message written");
                }
            });
        });

        super.start();
    }

    @Override
    public void stop() throws Exception {
        producer.close(res -> {
            if (res.succeeded()) {
                log.info("producer is now closed");
            } else {
                log.info("close failed");
            }
        });
        super.stop();
    }
}
