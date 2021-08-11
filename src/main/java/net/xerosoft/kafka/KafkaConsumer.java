package net.xerosoft.kafka;

import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import net.xerosoft.applicant.ApplicantService;
import net.xerosoft.applicant.value.ApplicantRequest;

@ApplicationScoped
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
    
    @Inject
    Vertx vertx;

    @Inject
    ApplicantService applicantService;

    @ConfigProperty(name = "forms.kafka.bootstrap.servers")
    Optional<String> bootstrapServes;

    @ConfigProperty(name = "forms.kafka.topic")
    Optional<String> topic;


    @ConfigProperty(name = "forms.kafka.enable", defaultValue = "false")
    boolean enableKafka;

    private String deploymentId;

    void onStartup(@Observes StartupEvent event) {
        log.info("onStartup");
        if (enableKafka) startKafkaConnection();
    }

    void onShutdown(@Observes ShutdownEvent event) {
        log.info("onShutdown");
        if (enableKafka) stopKafkaConnection();
    }

    private void startKafkaConnection() {
        KafkaConsumerVerticle consumerVerticle = new KafkaConsumerVerticle(
            bootstrapServes.get(), 
            topic.get(), 
            this::processForms);
        
        vertx.deployVerticle(consumerVerticle, handler -> {
            deploymentId = handler.result();
        });
    }

    private void stopKafkaConnection() {
        vertx.undeploy(deploymentId, handler -> {
            if (handler.succeeded()){
                vertx.close();
            }
        });
    }

    private void processForms(Form form) {
        vertx.executeBlocking(handler -> {
            ApplicantRequest request = new ApplicantRequest();
            request.age = form.age;
            request.sex = form.sex;
            request.email = form.email;
            request.name = form.name;
            request.score = form.score;
            request.applicationId = UUID.fromString("2d41840e-10ed-49ad-85f6-d01538bebc49");
            
            applicantService.create(request);
            handler.complete();

        }, handler -> {
            if (handler.failed()) {
                log.error("falied", handler.cause());
            }
            if (handler.succeeded()) {
                log.info("received form: {} - {}", form.name, form.sex);
            }
        });
    }
}
