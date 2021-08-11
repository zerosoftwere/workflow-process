package net.xerosoft.kafka;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;

//@ApplicationScoped
public class KafkaProducer {
    private Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    @Inject
    Vertx vertx;

    private String deploymentId;

    void onStart(@Observes StartupEvent event) {
        log.info("onStart");
        //startKafkaConnection();
    }

    void onStop(@Observes ShutdownEvent event) {
        log.info("onStop");
        //stopKafkaConnection();
    }

    private void startKafkaConnection() {
        KafkaProducerVerticle producerVerticle = new KafkaProducerVerticle();
        vertx.deployVerticle(producerVerticle, handler -> {
            deploymentId = handler.result();
        });
    }

    private void stopKafkaConnection() {
        vertx.undeploy(deploymentId, handler -> {
            if (handler.succeeded()) {
                vertx.close();
            }
        });
    }

}
