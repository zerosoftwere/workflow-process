package net.xerosoft.kafka;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class FormDeserializer extends ObjectMapperDeserializer<Form>{
    public FormDeserializer() {
        super(Form.class);
    }
}
